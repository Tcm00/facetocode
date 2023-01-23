package com.ming.facetocode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小明
 * @date 2022/4/7
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVO {
    private String email;
    private String code;
    private String status;
    private String time;
}
