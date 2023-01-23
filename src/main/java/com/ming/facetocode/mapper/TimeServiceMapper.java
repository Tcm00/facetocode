package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.facetocode.pojo.DO.HighDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 小明
 * @date 2022/4/21
 * @description
 */
@Mapper
@Repository
public interface TimeServiceMapper extends BaseMapper<HighDO> {
}
