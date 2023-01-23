package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.VO.TripInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Mapper
@Repository
public interface TripMapper extends BaseMapper<TripInfoDO> {
    TripInfoDO selectIdCard(String idCard);

    List<TripInfoDO> selectTrip(int userId);
}
