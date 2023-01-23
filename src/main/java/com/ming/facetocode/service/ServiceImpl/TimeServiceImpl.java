package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.TimeServiceMapper;
import com.ming.facetocode.pojo.DO.HighDO;
import com.ming.facetocode.service.TimeService;
import org.springframework.stereotype.Service;

/**
 * @author 小明
 * @date 2022/4/21
 * @description
 */
@Service
public class TimeServiceImpl extends ServiceImpl<TimeServiceMapper, HighDO> implements TimeService {

    @Override
    public int insert(HighDO highDO) {

        return baseMapper.insert(highDO);
    }
}
