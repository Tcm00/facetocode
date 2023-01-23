package com.ming.facetocode.service.ServiceImpl;

import com.ming.facetocode.mapper.TableMapper;
import com.ming.facetocode.pojo.DO.*;
import com.ming.facetocode.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/15
 * @description
 */
@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<AdminUserDO> getAdminUser() {
        return tableMapper.getAdminUser();
    }

    @Override
    public List<HealthCodeDO> getHealthCode() {
        return tableMapper.getHealthCode();
    }

    @Override
    public List<PassInfoDO> getPassInfo() {
        return tableMapper.getPassInfo();
    }

    @Override
    public List<RiskInfoDO> getRiskInfo() {
        return tableMapper.getRiskInfo();
    }

    @Override
    public List<TripInfoDO> getTripInfo() {
        return tableMapper.getTripInfo();
    }

    @Override
    public List<UserInfoDO> getUserInfo() {
        return tableMapper.getUserInfo();
    }
}
