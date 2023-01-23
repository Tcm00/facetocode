package com.ming.facetocode.controller;

import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.pojo.entity.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 小明
 * @date 2022/4/24
 * @description
 */
@Api(tags = "方洁")
@RequestMapping("/test")
@RestController
public class Fangjie {

    @ApiOperation(value = "查信息")
    @ApiImplicitParam(name = "id",value = "1",dataType = "int",paramType = "POST",required = true)
    @RequestMapping("/getInfo")
    public ResultBody preview(@RequestParam int id){
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setAge("14");
        userInfoDO.setId(1);
        userInfoDO.setName("张三");
        userInfoDO.setPhone("1523694856");
        userInfoDO.setSex("男");
        userInfoDO.setCreateTime(new Date());
        userInfoDO.setUpdateTime(new Date());
        return ResultBody.ok().data("info",userInfoDO);
    }
}
