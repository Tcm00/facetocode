package com.ming.facetocode.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "passInfo",description = "人流量信息")
@TableName(value = "passInfo")
public class PassInfoDO {
    private int id;
    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;
    @ApiModelProperty(name = "idCard",value = "人流身份号",example = "500103199805114160",required = true)
    private String idCard;
    private int healthCodeId;
    private String healthStatus;
    private String img;
    private String sort;
    @ApiModelProperty(name = "pcId",value = "摄像设备id",example = "1",required = true)
    private String pcId;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;
}
