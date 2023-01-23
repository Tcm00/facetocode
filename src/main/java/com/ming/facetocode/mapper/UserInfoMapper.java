package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 小明
 * @date 2022/4/8
 * @description
 */
@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {
}
