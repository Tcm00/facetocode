package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.VO.AddressVO;
import com.ming.facetocode.pojo.VO.TripInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Mapper
@Repository
public interface TripInfoMapper extends BaseMapper<TripInfoVO> {
    List<TripInfoVO> queryTripInfoByName(String name,String days);
    List<TripInfoVO> queryTripInfoByIdCard(String idCard,String days);

    TripInfoVO getTrip(String date,int id);

    List<AddressVO> getOne(String date,String userId);

    List<TripInfoVO> getTripInfo(Page<TripInfoVO> page, int parseInt);
}
