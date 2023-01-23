package com.ming.facetocode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskVO implements Serializable {
    private String city;
    private String area;
}
