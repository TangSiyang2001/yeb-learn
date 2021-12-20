package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Position;
import com.tsy.yebserver.service.IPositionService;
import com.tsy.yebserver.vo.Result;
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
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Resource
    private IPositionService positionService;

    @ApiOperation("获取所有职位")
    @GetMapping
    public Result listPositions() {
        return positionService.listPositions();
    }

    @ApiOperation("添加职位")
    @PostMapping
    public Result addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        position.setEnabled(true);
        return positionService.save(position) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("修改职位")
    @PutMapping
    public Result updatePosition(@RequestBody Position position) {
        return positionService.updateById(position) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("删除职位")
    @DeleteMapping("/{id}")
    public Result deletePosition(@PathVariable Integer id) {
        return positionService.removeById(id) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("批量删除职位")
    @DeleteMapping
    public Result deletePositions(Integer[] ids) {
        return positionService.removeByIds(Arrays.asList(ids)) ? Result.success(null) :
                Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
