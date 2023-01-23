package com.ming.facetocode.controller.client;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.LoginService;
import com.ming.facetocode.utils.JWTUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @author 小明
 * @date 2022/4/5
 * @description
 */
@Api(tags = "管理员登录注册接口")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminUser", value = "管理员姓名",paramType = "POST", required = true, dataType = "String", example = "root"),
            @ApiImplicitParam(name = "adminPassWord", value = "管理员密码",paramType = "POST", required = true, dataType = "String", example = "root")
    })
    @PostMapping("/login")
    public ResultBody login(@RequestParam String adminUser,@RequestParam String adminPassWord){
        return loginService.login(adminUser, adminPassWord);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResultBody register(@RequestBody @Validated @ApiParam(name = "adminInfo",value = "json") AdminUserDO adminInfo){
        return loginService.register(adminInfo);
    }


    @ApiOperation(value = "发送验证码")
    @ApiImplicitParam(name = "email",value = "邮箱",dataType = "String",required = true,example = "xxxx@qq.com")
    @PostMapping("/sendEmail")
    public ResultBody sendEmail(@RequestParam String email){
       return loginService.addEmail(email);
    }

    @ApiOperation(value = "解析token")
    @PostMapping("/explain")
    public ResultBody verify(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        //token=xx.xx.xx的头信息
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        //通过k-v中的键取出（k不可以重复）
        String adminUser = verify.getClaim("adminUser").asString();
        String state = verify.getClaim("state").asString();
        String email = verify.getClaim("email").asString();
        System.out.println(adminUser);
        System.out.println(state);
        System.out.println(email);

        map.put("userId",adminUser);
        map.put("userName",state);
        map.put("email",email);

        return ResultBody.ok().data("info",map);
    }
}
