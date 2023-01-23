package com.ming.facetocode.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小明
 * @date 2022/4/18
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO {

    private String province;
    private String city;
    private String area;
    private String createTime;
}
