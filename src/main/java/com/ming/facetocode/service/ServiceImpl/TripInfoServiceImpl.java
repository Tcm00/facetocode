package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.TripInfoMapper;
import com.ming.facetocode.pojo.VO.AddressVO;
import com.ming.facetocode.pojo.VO.TripInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.TripInfoService;
import com.ming.facetocode.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Service
public class TripInfoServiceImpl extends ServiceImpl<TripInfoMapper, TripInfoVO> implements TripInfoService {

    @Override
    public ResultBody getTripInfo(String name,String days) {
        List<TripInfoVO> tripInfoVOS = baseMapper.queryTripInfoByName(name, days);
        if (tripInfoVOS.size()!=0){
            return ResultBody.ok().data("tripInfo",tripInfoVOS).message("查询行程信息成功");
        }else {
            return ResultBody.error().message("未查询到该成员");
        }
    }

    @Override
    public ResultBody getTripInfoByIdCard(String idCard, String days) {
        List<TripInfoVO> tripInfoVOS = baseMapper.queryTripInfoByIdCard(idCard, days);
        if (tripInfoVOS.size()!=0){
            return ResultBody.ok().data("tripInfo",tripInfoVOS).message("查询行程信息成功");
        }else {
            return ResultBody.error().message("未查询到该成员");
        }
    }

    @Override
    public ResultBody getTrip(String id) {

//        List<TripInfoVO> tripInfo = baseMapper.getTripInfo(page, Integer.parseInt(id));

        List<TripInfoVO> lists = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,-i);
            Date time = instance.getTime();
            String date = dateFormat.format(time);
//            baseMapper.getOne("%"+date+"%");
            TripInfoVO trip = baseMapper.getTrip("%" + date + "%", Integer.parseInt(id));
            if (trip==null){
                continue;
            }
            trip.setDate(date);
            List<AddressVO> one = baseMapper.getOne("%" + date + "%",trip.getId());
            trip.setMap(one);
            lists.add(trip);
        }
        return ResultBody.ok().data("trip",lists).message("查询行程成功");
    }

}
