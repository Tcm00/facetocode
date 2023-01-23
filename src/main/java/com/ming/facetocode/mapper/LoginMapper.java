package com.ming.facetocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.DO.EmailCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author 小明
 * @date 2022/4/6
 * @description
 */
@Mapper
@Repository
public interface LoginMapper extends BaseMapper<AdminUserDO>{

    AdminUserDO seleteUserInfo(String adminUser, String adminPassWord);

    int addEmail(String email, String code);

    EmailCode selectCode(String email);

    void deleteOther(String email, String code);
}
