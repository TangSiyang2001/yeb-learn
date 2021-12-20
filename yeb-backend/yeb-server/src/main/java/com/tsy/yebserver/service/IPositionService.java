package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Position;
import com.tsy.yebserver.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IPositionService extends IService<Position> {

    /**
     * 获取所有职位
     * @return
     */
    Result listPositions();
}
