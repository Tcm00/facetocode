package com.ming.facetocode.pojo.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class UserInfoVO {
    private String healthCodeId;
    private int id;
    private String status;
    private String name;
    private String phone;
    private String idCard;
    private String age;
    private String sex;
    private String facePic;
    private String img;
    private String temperature;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;
}
