package com.ming.facetocode.controller.back;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.VO.TripInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.TripInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Api(tags = "行程码")
@RestController
@RequestMapping("/tripInfo")
public class TripInfoController {

    @Autowired
    private TripInfoService tripInfoService;

    @ApiOperation(value = "查询近7天行程码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "info",value = "姓名或身份证号",dataType = "String",required = true,example = "周杰伦/230102199003072475"),
            @ApiImplicitParam(name = "days",value = "最近？天(默认7天)",dataType = "String",example = "7")
    })
    @PostMapping("/getTripInfo")
    public ResultBody getTripInfo(@RequestParam String info,@RequestParam(defaultValue = "7") String days){
        if (info.length()<7){
            return tripInfoService.getTripInfo(info,days);
        }else {
            return tripInfoService.getTripInfoByIdCard(info,days);
        }
    }

    @ApiOperation(value = "根据id查询近7天行程码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",required = true,example = "61")
    })
    @PostMapping("/getTrip")
    public ResultBody getTrip(@RequestParam String id){
        return tripInfoService.getTrip(id);
    }

}
