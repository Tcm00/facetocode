package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.entity.ResultBody;


/**
 * @author 小明
 * @date 2022/4/6
 * @description
 */
public interface LoginService extends IService<AdminUserDO>{

    ResultBody login(String adminUser, String adminPassWord);
    ResultBody register(AdminUserDO adminInfo);

    ResultBody addEmail(String email);
}
