package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.entity.ResultBody;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
public interface TripService extends IService<TripInfoDO> {
    ResultBody setTriInfo(String province,String city, String area, String userId);

    List<TripInfoDO> getSevenTrip(int id);
}
