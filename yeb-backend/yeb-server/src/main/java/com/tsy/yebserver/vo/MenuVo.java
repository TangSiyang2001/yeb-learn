package com.tsy.yebserver.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/11/27
 */
@Data
@ApiModel(value="Menu对象")
public class MenuVo {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "图标")
    private String iconCls;

    @ApiModelProperty(value = "是否保持激活")
    private Boolean keepAlive;

    @ApiModelProperty(value = "是否要求权限")
    private Boolean requireAuth;

    @ApiModelProperty(value = "子菜单")
    private List<MenuVo> children;
}
