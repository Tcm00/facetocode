package com.ming.facetocode.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 小明
 * @date 2022/4/5
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserInfoDO",description = "用户信息")
@TableName(value = "userInfo")
public class UserInfoDO {
    @ApiModelProperty(name = "id",value = "id",example = "1",required = true)
    private int id;
    @ApiModelProperty(name = "name",value = "管理员名称",example = "需要改啥写啥,没写的不改")
    private String name;
    @ApiModelProperty(name = "phone",value = "手机号",example = "15223650215")
    private String phone;
    @ApiModelProperty(name = "idCard",value = "身份证号",example = "500233199907118595")
    private String idCard;
    @ApiModelProperty(name = "age",value = "年龄")
    private String age;
    @ApiModelProperty(name = "sex",value = "性别")
    private String sex;
    @ApiModelProperty(name = "facePic",value = "初始图片")
    private String facePic;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;

}
