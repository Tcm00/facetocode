package com.ming.facetocode.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.utils.JWTUtil;
import com.ming.facetocode.utils.LocalUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author Tcm
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        //获取请求头中的令牌
        String token = request.getHeader("token");
        try {
            //验证令牌
            JWTUtil.verify(token);
            AdminUserDO user = JWTUtil.getUser(token);
            LocalUser.USER.set(user);
            //放行请求
            return true;
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            map.put("msg","无效签名");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","token无效");
        }
        //设置状态
        map.put("state",false);
        //将map 转为json jackson 返回信息
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

    /**
     * 关闭当前线程
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalUser.USER.remove();
    }
}
