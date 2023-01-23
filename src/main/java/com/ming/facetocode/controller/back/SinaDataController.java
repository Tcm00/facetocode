package com.ming.facetocode.controller.back;

import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.SinaDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小明
 * @date 2022/4/11
 * @description
 */
@Api(tags = "Sina疫情接口")
@RestController
public class SinaDataController {

    @Autowired
    private SinaDataService sinaDataService;

    @ApiOperation(value = "实时疫情数据json")
    @GetMapping("/getSinaData")
    public ResultBody getSinaData(){
        return sinaDataService.getSinaData();
    }
}
