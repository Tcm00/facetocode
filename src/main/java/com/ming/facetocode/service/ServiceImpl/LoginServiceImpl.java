package com.ming.facetocode.service.ServiceImpl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.LoginMapper;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.DO.EmailCode;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.LoginService;
import com.ming.facetocode.utils.JWTUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;


/**
 * @author 小明
 * @date 2022/4/6
 * @description
 */
@Service(value = "LoginService")
public class LoginServiceImpl extends ServiceImpl<LoginMapper, AdminUserDO> implements LoginService {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ResultBody login(String adminUser, String adminPassWord) {
//        loginMapper.seleteUserInfo(adminUser,adminPassWord);
        QueryWrapper<AdminUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("adminUser",adminUser);
        queryWrapper.eq("adminPassWord",adminPassWord);
        AdminUserDO adminUserDO = baseMapper.selectOne(queryWrapper);
        try {
            if (!adminUserDO.toString().isEmpty()){
                HashMap<String, Object> map = new HashMap<>();
                try {
                    //登录Service
                    HashMap<String, String> payload = new HashMap<>();
                    payload.put("adminUser",adminUserDO.getAdminUser());
                    payload.put("state",adminUserDO.getState());
                    payload.put("email",adminUserDO.getEmail());
                    //生成JWT令牌
                    String token = JWTUtil.getToken(payload);
                    map.put("state",true);
                    map.put("msg","认证成功");
                    //响应token
                    map.put("token",token);
                } catch (Exception e) {
                    map.put("state",false);
                    map.put("msg",e.getMessage());
                }
                return ResultBody.ok().data("info",adminUserDO).data("token",map).message("登录成功:");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBody.error().message("账号或密码错误！！");
    }

    @Override
    public ResultBody register(AdminUserDO adminInfo) {
        EmailCode CODE = baseMapper.selectCode(adminInfo.getEmail());
        String code = redisTemplate.opsForValue().get(adminInfo.getEmail());
        if (code==null){
            return ResultBody.error().message("请先获取验证码");
        }
        QueryWrapper<AdminUserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("email",adminInfo.getEmail());
        AdminUserDO adminUserDO = baseMapper.selectOne(wrapper);
        System.out.println(adminUserDO);
        if (adminUserDO!=null){
            baseMapper.deleteById(adminUserDO.getId());
        }
        if (Objects.equals(adminInfo.getCode(), code)){
            baseMapper.insert(adminInfo);
            return ResultBody.ok().message("注册成功："+adminInfo.getAdminUser());
        }
        return ResultBody.error().message("注册失败，验证码错误！！");
    }

    @Override
    public ResultBody addEmail(String email) {
        String code = String.valueOf(Math.random()).substring(2,8);
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject("验证码");
        message.setText(code);
        message.setTo(email);
        message.setFrom("1743394547@qq.com");
        javaMailSender.send(message);
        redisTemplate.opsForValue().set(email,code);
        return ResultBody.ok().message("发送邮件成功:"+code);
    }
}
