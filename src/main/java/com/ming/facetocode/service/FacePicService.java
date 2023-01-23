package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.HighDO;
import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/22
 * @description
 */
public interface FacePicService extends IService<HighDO> {

    UserInfoVO selectId(String faceName);


    String updateStatus(List<TripInfoDO> sevenTrip);

    int updateHealthCode(String faceName, String status);

    int addPassInfo(UserInfoVO userInfoVO);
}
