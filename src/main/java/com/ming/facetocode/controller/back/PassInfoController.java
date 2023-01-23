package com.ming.facetocode.controller.back;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.VO.WuKongVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.PassInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Api(tags = "人流量数据")
@RequestMapping("/PassInfo")
@RestController
public class PassInfoController {
    @Autowired
    private PassInfoService passInfoService;

    @ApiOperation(value = "过机异常查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber",value = "当前页数(默认1)",dataType = "String",required = true,example = "1"),
            @ApiImplicitParam(name = "size",value = "每页容量(默认7)",dataType = "String",required = true,example = "7"),
            @ApiImplicitParam(name = "date",value = "查询哪天(默认当天)",dataType = "String",required = true,example = "2022-04-12")
    })
    @PostMapping("/getAll")
    public ResultBody getAll(@RequestParam(defaultValue = "1") @NotEmpty String pageNumber,
                             @RequestParam(defaultValue = "7") @NotEmpty String size,
                             @RequestParam(defaultValue = "date") @NotEmpty String date) {
        Long pageN = Long.valueOf(pageNumber);
        Long sizeN = Long.valueOf(size);
        Page<PassInfoDO> page = new Page<>(pageN,sizeN);
        Page<PassInfoDO> all = passInfoService.getAll(page,date);

        return ResultBody.ok().data("passInfo", all).message("查询过机成功");
    }

    @ApiOperation(value = "添加一个通过数据")
    @PostMapping("/addPassInfo")
    public ResultBody setPassInfo(@RequestBody @ApiParam PassInfoDO passInfoDO){
        return passInfoService.addPassInfo(passInfoDO);
    }

    @ApiOperation(value = "按日期查询每日流量中个码统计")
    @ApiImplicitParam(name = "date",value = "查询哪天(默认当天)",dataType = "String",example = "2022-04-12")
    @PostMapping("/selectCount")
    public ResultBody selectCount(@RequestParam(defaultValue = "date") @NotEmpty String date){
        return passInfoService.getCount(date);
    }

    @ApiOperation(value = "近七天的疫情统计")
    @ApiImplicitParam(name = "days",value = "天数(默认7天)",dataType = "String",example = "7")
    @PostMapping("/getSeven")
    public ResultBody getSeven(@RequestParam(defaultValue = "7") String days){
        return passInfoService.getSeven(days);
    }


    @ApiOperation(value = "实时通过--孙悟空")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber",value = "当前页数(默认1)",dataType = "String",required = true,example = "1"),
            @ApiImplicitParam(name = "size",value = "每页容量(默认7)",dataType = "String",required = true,example = "7")
    })
    @PostMapping("/getNow")
    public ResultBody getNow(@RequestParam(defaultValue = "1") @NotEmpty String pageNumber,
                             @RequestParam(defaultValue = "7") @NotEmpty String size
                             ){
        Long pageN = Long.valueOf(pageNumber);
        Long sizeN = Long.valueOf(size);
        Page<WuKongVO> page = new Page<>(pageN,sizeN);
        return passInfoService.getNow(page);
    }


}
