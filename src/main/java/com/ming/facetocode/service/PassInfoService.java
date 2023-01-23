package com.ming.facetocode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.VO.WuKongVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
public interface PassInfoService extends IService<PassInfoDO> {
    ResultBody addPassInfo(PassInfoDO passInfoDO);

    Page<PassInfoDO> getAll(@RequestBody Page<PassInfoDO> page, String date);

    ResultBody getCount(String date);

    ResultBody getSeven(String days);

    ResultBody getNow(Page<WuKongVO> page);
}
