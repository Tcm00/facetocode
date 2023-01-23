package com.ming.facetocode.pojo.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author 小明
 * @date 2022/4/17
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WuKongVO {
    private int id;
    private String name;
    private String phone;
    private String status;
    private String address;
    private String img;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "deleteTime",fill = FieldFill.UPDATE)
    private Date deleteTime;
}
