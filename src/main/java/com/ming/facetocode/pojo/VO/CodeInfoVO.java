package com.ming.facetocode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeInfoVO {
    private int id;
    private String img;
    private String status;
    private String name;
    private String phone;
    private String idCard;
    private String age;
    private String sex;
    private String facePic;

}
