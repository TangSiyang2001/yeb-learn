package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.JobLevel;
import com.tsy.yebserver.vo.JobLevelVo;
import com.tsy.yebserver.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IJobLevelService extends IService<JobLevel> {

    /**
     * 获取所有职级
     * @return 结果
     */
    Result listJobLevels();
}
