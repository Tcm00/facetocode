package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.TripMapper;
import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.TripService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Service
public class TripServiceImpl extends ServiceImpl<TripMapper, TripInfoDO> implements TripService {

    @Override
    public ResultBody setTriInfo(String province,String city, String area, String userId) {

        TripInfoDO tripInfo = new TripInfoDO();
        tripInfo.setProvince(province);
        tripInfo.setCity(city);
        tripInfo.setArea(area);
        tripInfo.setUserId(userId);
        baseMapper.insert(tripInfo);
        return ResultBody.ok().message("行程录入成功");

    }

    @Override
    public List<TripInfoDO> getSevenTrip(int id) {

        return baseMapper.selectTrip(id);
    }
}
