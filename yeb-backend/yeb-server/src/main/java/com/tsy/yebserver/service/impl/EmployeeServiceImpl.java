package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.*;
import com.tsy.yebserver.dao.mapper.EmployeeMapper;
import com.tsy.yebserver.service.*;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private IPoliticsStatusService politicsStatusService;

    @Resource
    private IJobLevelService jobLevelService;

    @Resource
    private INationService nationService;

    @Resource
    private IPositionService positionService;

    @Resource
    private IDepartmentService departmentService;

    @Resource
    private IMqService mqService;

    @Override
    public Result listEmployeeByPage(PageParam pageParam, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        final List<Employee> records = employeeMapper.listEmployeeByPage(page, employee, beginDateScope).getRecords();
        return Result.success(records);
    }

    @Override
    public Result getAvailableWorkerId() {
        //max(workID)为数据库中用max查找的字段名
        final List<Map<String, Object>> res = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        final int rawId = Integer.parseInt(String.valueOf(res.get(0).get("max(workID)")) + 1);
        return Result.success(String.format("%08d", rawId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addEmployee(Employee employee) {
        //首先要设置合同期限 根据起止日期计算
        setContractTerm(employee);
        //插入信息
        final int insertRecord = employeeMapper.insert(employee);
        if (insertRecord == 1) {
            //insert后会回写id
            Employee targetEmployee = employeeMapper.getEmployeeById(employee.getId()).get(0);
            //通知消息队列发送邮件
            mqService.sendEmployeeMessage(targetEmployee);
            return Result.success(null);
        } else {
            return Result.fail(Result.CodeMsg.OPERATION_FAILED);
        }
    }

    @Override
    public Result updateEmployee(Employee employee) {
        setContractTerm(employee);
        return super.updateById(employee) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @Override
    public List<Employee> getEmployeeById(Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveEmployeeInfoList(List<Employee> employees) {
        final List<Nation> nations = nationService.list();
        final List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
        final List<Position> positions = positionService.list();
        final List<Department> departments = departmentService.list();
        final List<JobLevel> jobLevels = jobLevelService.list();
        //如下四个实体类中重写hashcode方法，用name作为hashcode后，利用indexOf（根据equals寻址）来获取查询得到的相应id
        for (Employee employee : employees) {
            Nation nation = new Nation(employee.getNation().getName());
            employee.setNationId(nations.get(nations.indexOf(nation)).getId());
            Position position = new Position(employee.getPosition().getName());
            employee.setPosId(positions.get(positions.indexOf(position)).getId());
            Department department = new Department(employee.getDepartment().getName());
            employee.setDepartmentId(departments.get(departments.indexOf(department)).getId());
            PoliticsStatus politicsStatus = new PoliticsStatus(employee.getPoliticsStatus().getName());
            employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(politicsStatus)).getId());
            JobLevel jobLevel = new JobLevel(employee.getJobLevel().getName());
            employee.setJobLevelId(jobLevels.get(jobLevels.indexOf(jobLevel)).getId());
        }
        final boolean isSuccess = super.saveOrUpdateBatch(employees);
        return isSuccess ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    private void setContractTerm(Employee employee) {
        final LocalDate beginContract = employee.getBeginContract();
        final LocalDate endContract = employee.getEndContract();
        final long duration = beginContract.until(endContract, ChronoUnit.DAYS);
        final DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(duration / 365.00)));
    }
}
