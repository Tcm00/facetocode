package com.ming.facetocode.controller.client;

import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Api(tags = "前台行程信息")
@RestController
public class TripController {
    @Autowired
    private TripService tripService;

    @ApiOperation(value = "存入行程信息,后面我可能在图片匹配的时候自己存了")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province",value = "省",dataType = "String",required = true,example = "重庆市重庆市"),
            @ApiImplicitParam(name = "city",value = "**省**市",dataType = "String",required = true,example = "重庆市重庆市"),
            @ApiImplicitParam(name = "area",value = "**区/**县",dataType = "String",required = true,example = "合川区"),
            @ApiImplicitParam(name = "idCard",value = "身份证号",dataType = "String",required = true,example = "500103199805114160")
    })
    @PostMapping("/setTriInfo")
    public ResultBody setTripInfo(@RequestParam String province ,@RequestParam String city, @RequestParam String area, @RequestParam String userId){
        return tripService.setTriInfo(province,city,area,userId);
    }
}
