package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.yebserver.dao.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据名字的关键词搜素管理员
     * @param adminId 管理员编号
     * @param keywords 关键词
     * @return 结果
     */
    List<Admin> getAdminByKeywords(@Param("adminId") Integer adminId, @Param("keywords") String keywords);
}
