package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.JobLevel;
import com.tsy.yebserver.service.IJobLevelService;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Api(tags = "JobLevelController")
@RestController
@RequestMapping("/system/basic/job_level")
public class JobLevelController {

    @Resource
    private IJobLevelService jobLevelService;

    @ApiOperation("获取所有职称")
    @GetMapping
    public Result listJobLevels() {
        return jobLevelService.listJobLevels();
    }

    @ApiOperation("添加职称")
    @PostMapping
    public Result addJobLevel(@RequestBody JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        jobLevel.setEnabled(true);
        return jobLevelService.save(jobLevel) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("修改职称")
    @PutMapping
    public Result updateJobLevel(@RequestBody JobLevel jobLevel) {
        return jobLevelService.updateById(jobLevel) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    /**
     * 此处不需要逻辑删除，因为职级相对固定，也没有统计的需求
     */
    @ApiOperation("删除职称")
    @DeleteMapping("/{id}")
    public Result deleteJobLevel(@PathVariable Integer id) {
        return jobLevelService.removeById(id) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("批量删除职称")
    @DeleteMapping
    public Result deleteJobLevels(Integer[] ids) {
        return jobLevelService.removeByIds(Arrays.asList(ids)) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
