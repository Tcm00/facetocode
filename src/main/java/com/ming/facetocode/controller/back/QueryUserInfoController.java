package com.ming.facetocode.controller.back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.DO.AdminUserDO;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.QueryUserInfoService;
import com.ming.facetocode.utils.LocalUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author 小明
 * @date 2022/4/8
 * @description
 */
@Api(tags = "用户管理信息")
@RestController
@RequestMapping("/userInfo")
public class QueryUserInfoController {

    @Autowired
    private QueryUserInfoService queryUserInfoService;

    @ApiOperation(value = "查询所有用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber",value = "当前页数(默认1)",dataType = "String",example = "1"),
            @ApiImplicitParam(name = "size",value = "每页容量(默认10)",dataType = "String",example = "10"),
            @ApiImplicitParam(name = "info",value = "姓名或者身份证号(改了，可以不传)",dataType = "String",example = "",required = true)
    })
    @PostMapping("/getUserAll")
    public ResultBody selectAll(@RequestParam(defaultValue = "1") @NotEmpty String pageNumber,
                                @RequestParam(defaultValue = "10") @NotEmpty String size,
                                @RequestParam(defaultValue = " ") String info){

        Long pageN = Long.valueOf(pageNumber);
        Long sizeN = Long.valueOf(size);
        Page<UserInfoVO> page = new Page<>(pageN,sizeN);
        if (!Objects.equals(info.trim(), "")){
            if (info.length()<7){
                return queryUserInfoService.queryByNameInfo(page,info);
            }else {
                return queryUserInfoService.queryByIdCard(page,info);
            }
        }
        Page<UserInfoVO> voPage = queryUserInfoService.selectByAll(page);
        return ResultBody.ok().data("userInfo",voPage).message("查询用户信息成功");
    }

    @ApiOperation(value = "通过id查用户")
    @ApiImplicitParam(name = "id",value = "id",dataType = "int",required = true,example = "1")
    @PostMapping("/getUserById")
    public ResultBody selectById(@RequestParam int id){
        return queryUserInfoService.selectById(id);
    }


    @ApiOperation(value = "根据id更新用户信息，没改的千万千万千万不要传")
    @PostMapping("/updateById")
    public ResultBody updateById(@RequestBody @Validated @ApiParam UserInfoDO userInfo){
        boolean b = queryUserInfoService.updateById(userInfo);
        if (b) {
            return ResultBody.ok().message(userInfo.getId()+"：更新成功");
        }else {
            return ResultBody.error().message("更新失败，请重试");
        }
    }

    @ApiOperation(value = "通过id删除(伪)")
    @PostMapping("/deleteById")
    public ResultBody deleteById(@RequestParam int id){
        UpdateWrapper<UserInfoDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("deleteTime",new Date());
        boolean delete = queryUserInfoService.update(wrapper);
        if (delete) {
            return ResultBody.ok().message(id+"：删除成功");
        }else {
            return ResultBody.error().message("更新失败，请重试");
        }
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/addUser")
    public ResultBody addInfo(@RequestBody @Validated @ApiParam UserInfoDO userInfo){
        AdminUserDO adminUserDO = LocalUser.USER.get();
        System.out.println("操作管理员：=====>"+adminUserDO.getAdminUser()+"<===");
        System.out.println("管理员状态：======>"+adminUserDO.getState()+"<====");

        return queryUserInfoService.addUser(userInfo);
    }

    @ApiOperation(value = "统计系统人数")
    @PostMapping("/getCount")
    public ResultBody getCount(){
        return queryUserInfoService.getCount();
    }


    @ApiOperation(value = "查询所有异常用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber",value = "当前页数(默认1)",dataType = "String",example = "1"),
            @ApiImplicitParam(name = "size",value = "每页容量(默认10)",dataType = "String",example = "10"),
            @ApiImplicitParam(name = "info",value = "姓名或者身份证号(改了，可以不传)",dataType = "String",example = "",required = true)
    })
    @PostMapping("/getRedUser")
    public ResultBody selectRedUser(@RequestParam(defaultValue = "1") @NotEmpty String pageNumber,
                                    @RequestParam(defaultValue = "10") @NotEmpty String size,
                                    @RequestParam(defaultValue = " ") String info){

        Long pageN = Long.valueOf(pageNumber);
        Long sizeN = Long.valueOf(size);
        Page<UserInfoVO> page = new Page<>(pageN,sizeN);
        if (!Objects.equals(info.trim(), "")){
            if (info.length()<7){
                return queryUserInfoService.queryByNameRed(page,info);
            }else {
                return queryUserInfoService.queryByIdCardRed(page,info);
            }
        }
        Page<UserInfoVO> voPage = queryUserInfoService.selectRedUser(page);
        return ResultBody.ok().data("userInfo",voPage).message("查询用户信息成功");
    }
}
