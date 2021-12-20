package com.tsy.yebserver.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Steven.T
 * @date 2021/12/10
 */
@Data
@ApiModel("职位对象")
public class PositionVo {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "职位")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
}
