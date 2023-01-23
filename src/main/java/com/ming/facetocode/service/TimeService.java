package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.HighDO;

/**
 * @author 小明
 * @date 2022/4/21
 * @description
 */
public interface TimeService extends IService<HighDO> {
    int insert(HighDO highDO);
}
