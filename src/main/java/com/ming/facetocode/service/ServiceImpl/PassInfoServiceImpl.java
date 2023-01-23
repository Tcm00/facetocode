package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.PassInfoMapper;
import com.ming.facetocode.pojo.DO.HealthCodeDO;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.VO.GreenVO;
import com.ming.facetocode.pojo.VO.SevenVO;
import com.ming.facetocode.pojo.VO.WuKongVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.PassInfoService;
import com.ming.facetocode.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 小明
 * @date 2022/4/12
 * @description
 */
@Service
public class PassInfoServiceImpl extends ServiceImpl<PassInfoMapper, PassInfoDO> implements PassInfoService {

    @Override
    public ResultBody addPassInfo(PassInfoDO passInfoDO) {
        String s = baseMapper.selectUser(passInfoDO.getIdCard());
        HealthCodeDO healthCodeDO = baseMapper.selectCode(s);
        passInfoDO.setUserId(s);
        passInfoDO.setHealthCodeId(healthCodeDO.getId());
        passInfoDO.setHealthStatus(healthCodeDO.getStatus());
        int insert = baseMapper.insert(passInfoDO);
        if (insert>0){
            return ResultBody.ok().message("行程数据存入成功");
        }
        return ResultBody.error().message("行程数据数据存入失败");
    }

    @Override
    public Page<PassInfoDO> getAll(Page<PassInfoDO> page,String date) {

        if (Objects.equals(date, "date")) {
            date = DateUtil.getCurrentTime().substring(0, 10);
        }
        return baseMapper.selectRed(page,date+"%");
    }

    @Override
    public ResultBody getCount(String date) {
        if (Objects.equals(date, "date")) {
            date = DateUtil.getCurrentTime().substring(0, 10);
        }
        List<GreenVO> count = baseMapper.getCount(date + "%");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("红码","0");
        hashMap.put("黄码","0");
        hashMap.put("绿码","0");
        for (GreenVO greenVO : count) {
            if (Objects.equals(greenVO.getState(), "红码")) hashMap.put("红码",greenVO.getNumber());
            if (Objects.equals(greenVO.getState(), "黄码")) hashMap.put("黄码",greenVO.getNumber());
            if (Objects.equals(greenVO.getState(), "绿码")) hashMap.put("绿码",greenVO.getNumber());
        }
        hashMap.put("date",date);
        return ResultBody.ok().data("count",hashMap).message("查询"+date+"绿码统计成功(红、黄、绿)");
    }

    @Override
    public ResultBody getSeven(String days) {

        ArrayList<SevenVO> sevenVOS = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(days); i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,-i);
            Date time = instance.getTime();
            String format = dateFormat.format(time);
            List<GreenVO> count = baseMapper.getCount(format + "%");

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("red","0");
            hashMap.put("yellow","0");
            hashMap.put("green","0");
            for (GreenVO greenVO : count) {
                if (Objects.equals(greenVO.getState(), "红码")) hashMap.put("red",greenVO.getNumber());
                if (Objects.equals(greenVO.getState(), "黄码")) hashMap.put("yellow",greenVO.getNumber());
                if (Objects.equals(greenVO.getState(), "绿码")) hashMap.put("green",greenVO.getNumber());
            }
            SevenVO sevenVO = new SevenVO();
            sevenVO.setDate(format);
            sevenVO.setRed(hashMap.get("red"));
            sevenVO.setYellow(hashMap.get("yellow"));
            sevenVO.setGreen(hashMap.get("green"));
            sevenVOS.add(sevenVO);
        }
        return ResultBody.ok().data("Seven",sevenVOS).message("多日绿码统计");
    }

    @Override
    public ResultBody getNow(Page<WuKongVO> page) {
        Page<WuKongVO> now = baseMapper.getNow(page);

        return ResultBody.ok().data("NowInfo",now).message("查询悟空成功");
    }

}
