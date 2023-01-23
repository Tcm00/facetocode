package com.ming.facetocode.mapper;

import com.ming.facetocode.pojo.DO.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/15
 * @description
 */
@Mapper
@Repository
public interface TableMapper {

    List<AdminUserDO> getAdminUser();

    List<HealthCodeDO> getHealthCode();

    List<PassInfoDO> getPassInfo();

    List<RiskInfoDO> getRiskInfo();

    List<TripInfoDO> getTripInfo();

    List<UserInfoDO> getUserInfo();
}
