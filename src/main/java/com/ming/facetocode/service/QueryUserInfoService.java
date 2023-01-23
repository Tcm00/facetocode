package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
public interface QueryUserInfoService extends IService<UserInfoDO> {
    ResultBody queryByNameInfo(Page<UserInfoVO> page,String name);

    ResultBody queryByIdCard(Page<UserInfoVO> page,String idCard);

    ResultBody selectById(int id);

    Page<UserInfoVO> selectByAll(Page<UserInfoVO> page);

    ResultBody addUser(UserInfoDO userInfoDO);

    ResultBody getCount();

    Page<UserInfoVO> selectRedUser(Page<UserInfoVO> page);

    ResultBody queryByNameRed(Page<UserInfoVO> page,String info);

    ResultBody queryByIdCardRed(Page<UserInfoVO> page, String info);
}
