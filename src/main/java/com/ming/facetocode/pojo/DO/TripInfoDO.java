package com.ming.facetocode.pojo.DO;

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
@TableName(value = "tripInfo")
public class TripInfoDO {
    private int id;
    private String province;
    private String city;
    private String area;
    private String userId;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;
}
