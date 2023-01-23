package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.pojo.VO.CodeInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Mapper
@Repository
public interface QueryUserInfoMapper extends BaseMapper<UserInfoDO> {
    Page<UserInfoVO> queryByNameInfo(Page<UserInfoVO> page,String name);

    Page<UserInfoVO>  queryByIdCard(Page<UserInfoVO> page,String idCard);

    Page<UserInfoVO> selectByAll(Page<UserInfoVO> page);

    UserInfoVO queryById(int id);

    Integer getRedNum();

    Integer getGreen();

    Integer getYellow();

    Integer getAll();

    int insertCode(int id,Date create,Date update);

    Page<UserInfoVO> selectRedUser(Page<UserInfoVO> page);

    Page<UserInfoVO> queryByNameRed(Page<UserInfoVO> page, String name);

    Page<UserInfoVO> queryByIdCardRed(Page<UserInfoVO> page, String idCard);

    boolean updateUser(String id, String facePic);
}
