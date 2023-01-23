package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.facetocode.pojo.DO.HighDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小明
 * @date 2022/4/22
 * @description
 */
@Mapper
@Repository
public interface FacePicMapper extends BaseMapper<HighDO> {

    UserInfoVO selectId(String id);

    List<HighDO> selectRisk(String province, String city, String county);

    int updateHealthCode(String userId, String status);

    int addPassInfo(@Param("img") String facePic,@Param("userId") int id,@Param("idCard") String idCard,@Param("healthCodeId") String healthCodeId,@Param("healthStatus") String healthStatus, String sort, String pcId);
}
