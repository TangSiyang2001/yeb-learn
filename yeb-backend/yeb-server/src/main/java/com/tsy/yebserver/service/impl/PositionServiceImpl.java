package com.tsy.yebserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Position;
import com.tsy.yebserver.dao.mapper.PositionMapper;
import com.tsy.yebserver.service.IPositionService;
import com.tsy.yebserver.vo.PositionVo;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public Result listPositions() {
        return Result.success(copyList(list()));
    }

    private Object copyList(List<Position> list) {
        List<PositionVo> positionVos=new ArrayList<>();
        list.forEach(position -> positionVos.add(copy(position)));
        return positionVos;
    }

    private PositionVo copy(Position position) {
        PositionVo positionVo=new PositionVo();
        BeanUtils.copyProperties(position,positionVo);
        return positionVo;
    }
}
