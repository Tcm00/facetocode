package com.ming.facetocode.utils;

import com.ming.facetocode.pojo.DO.AdminUserDO;
import org.springframework.stereotype.Component;

/**
 * @author 小明
 * @date 2021/10/27
 * @description
 */
@Component
public class LocalUser {
    public static ThreadLocal<AdminUserDO> USER = new ThreadLocal<>();
}
