package com.ming.facetocode.service;

import com.ming.facetocode.pojo.DO.*;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/15
 * @description
 */
public interface TableService {
    List<AdminUserDO> getAdminUser();
    List<HealthCodeDO> getHealthCode();
    List<PassInfoDO> getPassInfo();
    List<RiskInfoDO> getRiskInfo();
    List<TripInfoDO> getTripInfo();
    List<UserInfoDO> getUserInfo();
}
