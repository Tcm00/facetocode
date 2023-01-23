package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.FacePicMapper;
import com.ming.facetocode.mapper.PassInfoMapper;
import com.ming.facetocode.pojo.DO.HighDO;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.DO.RiskInfoDO;
import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.service.FacePicService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author 小明
 * @date 2022/4/22
 * @description
 */
@Service
public class FacePicServiceImpl extends ServiceImpl<FacePicMapper, HighDO> implements FacePicService {

    @Autowired
    private PassInfoMapper passInfoMapper;

    @Override
    public UserInfoVO selectId(String id) {
        return baseMapper.selectId(id);
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String updateStatus(List<TripInfoDO> sevenTrip) {

        String status = "绿码";
        String normalCity = "";
        String userId  = "";
        for (TripInfoDO tripInfoDO : sevenTrip) {

            userId = tripInfoDO.getUserId();
            List<HighDO> highDOS = baseMapper.selectRisk(tripInfoDO.getProvince(), tripInfoDO.getCity(), tripInfoDO.getArea());
            System.out.println(highDOS);
            if (highDOS.size()==0){
                continue;
            }

            status = "黄码";
            for (HighDO highDO : highDOS) {
                if (Objects.equals(highDO.getRisk(), "highCity")){
                    redisTemplate.opsForValue().set(userId,highDO.getCity());
                    status = "红码";
                    return status;
                }
                redisTemplate.opsForValue().set(userId,highDO.getCity());
            }
        }
        if (status=="绿码"){
            redisTemplate.opsForValue().set(userId,sevenTrip.get(0).getCity());
        }
        return status;
    }

    @Override
    public int updateHealthCode(String userId, String status) {
        return baseMapper.updateHealthCode(userId,status);
    }


    @Override
    public int addPassInfo(UserInfoVO userInfoVO) {
        String  sort = "3";
        switch (userInfoVO.getStatus()){
            case "红码":sort = "1";break;
            case "黄码":sort = "2";break;
            case "绿码":sort = "3";break;
            default:
                sort = "为定义";
                break;
        }
        PassInfoDO passInfoDO = new PassInfoDO();
        passInfoDO.setImg(userInfoVO.getFacePic());
        passInfoDO.setUserId(String.valueOf(userInfoVO.getId()));
        passInfoDO.setIdCard(userInfoVO.getIdCard());
        passInfoDO.setHealthCodeId(Integer.parseInt(userInfoVO.getHealthCodeId()));
        passInfoDO.setHealthStatus(userInfoVO.getStatus());
        passInfoDO.setSort(sort);
        passInfoDO.setImg(userInfoVO.getImg());
        passInfoDO.setPcId("1");
        return passInfoMapper.insert(passInfoDO);
    }

    /**
     * 1.查询健康码状态，身份信息
     * 2.添加该坐标的行程到行程表中
     * 3.查询近七日行程
     * 4.根据行程做参数查高风险，低风险表，
     * 5.修改健康码状态
     */

}
