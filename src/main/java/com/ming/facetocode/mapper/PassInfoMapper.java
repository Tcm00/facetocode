package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.DO.HealthCodeDO;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.VO.GreenVO;
import com.ming.facetocode.pojo.VO.WuKongVO;
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
public interface PassInfoMapper extends BaseMapper<PassInfoDO> {
    String selectUser(String idCard);

    HealthCodeDO selectCode(String s);

    Page<PassInfoDO> selectRed(Page<PassInfoDO> page,String date);

    List<GreenVO> getCount(String date);

    Page<WuKongVO> getNow(Page<WuKongVO> page);
}
