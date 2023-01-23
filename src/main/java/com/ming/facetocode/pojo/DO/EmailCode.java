package com.ming.facetocode.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小明
 * @date 2022/4/7
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCode {
    private int id;
    private String email;
    private String code;
}
