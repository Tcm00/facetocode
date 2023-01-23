package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.VO.TripInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
public interface TripInfoService extends IService<TripInfoVO> {
    ResultBody getTripInfo(String name, String days);
    ResultBody getTripInfoByIdCard(String idCard, String days);

    ResultBody getTrip(String id);
}
