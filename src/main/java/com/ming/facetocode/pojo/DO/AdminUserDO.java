package com.ming.facetocode.pojo.DO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 小明
 * @date 2022/4/5
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AdminUserDO",description = "管理员信息")
@TableName(value = "adminUser")
public class AdminUserDO implements Serializable {
    private int id;
    @ApiModelProperty(name = "adminUser",value = "管理员名称",example = "test",required = true)
    private String adminUser;
    @ApiModelProperty(name = "adminPassWord",value = "管理员密码",example = "test",required = true)
    private String adminPassWord;
    @ApiModelProperty(name = "state",value = "管理员状态",example = "admin/test")
    private String state;
    @ApiModelProperty(name = "email",value = "邮箱地址",example = "xxxxxx@qq.com",required = true)
    private String email;
    @ApiModelProperty(name = "code",value = "验证码，先发",required = true)
    private String code;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;
}
