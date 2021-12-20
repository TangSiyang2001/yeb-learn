package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.JobLevel;
import com.tsy.yebserver.dao.mapper.JoblevelMapper;
import com.tsy.yebserver.service.IJobLevelService;
import com.tsy.yebserver.vo.JobLevelVo;
import com.tsy.yebserver.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class JobLevelServiceImpl extends ServiceImpl<JoblevelMapper, JobLevel> implements IJobLevelService {

    @Override
    public Result listJobLevels(){
        return Result.success(copyList(list()));
    }

    private List<JobLevelVo> copyList(List<JobLevel> list) {
        List<JobLevelVo> jobLevelVos=new ArrayList<>();
        list.forEach(jobLevel -> jobLevelVos.add(copy(jobLevel)));
        return jobLevelVos;
    }

    private JobLevelVo copy(JobLevel jobLevel) {
        JobLevelVo jobLevelVo=new JobLevelVo();
        BeanUtils.copyProperties(jobLevel,jobLevelVo);
        return jobLevelVo;
    }
}
