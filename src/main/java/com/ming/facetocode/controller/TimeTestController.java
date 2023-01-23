package com.ming.facetocode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ming.facetocode.pojo.DO.*;
import com.ming.facetocode.service.TableService;
import com.ming.facetocode.service.TimeService;
import com.ming.facetocode.utils.AddressUtil;
import com.ming.facetocode.utils.DateUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小明
 * @date 2022/4/15
 * @description
 */
@Controller
public class TimeTestController {

//    每隔5秒执行一次：*/5 * * * * ?
//    每隔1分钟执行一次：0 */1 * * * ?
//    每天23点执行一次：0 0 23 * * ?
//    每天凌晨1点执行一次：0 0 1 * * ?
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//    每月最后一天23点执行一次：0 0 23 L * ?
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//    在26分、29分、33分执行一次：0 26,29,33 * * * ?
//    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TableService tableService;

    @Autowired
    private TimeService timeService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void timeScheduled() {

        //格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM--dd");
        System.out.println("当前时间:" + dateFormat.format(new Date()));
        String date = dateFormat.format(new Date());
        System.out.println(new Date());
        setRedisTemplate(date, "high");
        setRedisTemplate(date, "mid");

        setTable();
    }
    //存数据库所有表
    private void setTable() {
        List<AdminUserDO> adminUser = tableService.getAdminUser();
        redisTemplate.opsForValue().set("adminUser",JSONObject.toJSON(adminUser).toString());
        List<HealthCodeDO> healthCode = tableService.getHealthCode();
        redisTemplate.opsForValue().set("healthCode",JSONObject.toJSON(healthCode).toString());
        List<PassInfoDO> passInfo = tableService.getPassInfo();
        redisTemplate.opsForValue().set("passInfo",JSONObject.toJSON(passInfo).toString());
        List<RiskInfoDO> riskInfo = tableService.getRiskInfo();
        redisTemplate.opsForValue().set("riskInfo",JSONObject.toJSON(riskInfo).toString());
        List<TripInfoDO> tripInfo = tableService.getTripInfo();
        redisTemplate.opsForValue().set("tripInfo",JSONObject.toJSON(tripInfo).toString());
        List<UserInfoDO> userInfo = tableService.getUserInfo();
        redisTemplate.opsForValue().set("userInfo",JSONObject.toJSON(userInfo).toString());
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void deleteAll(){
        delAllFile("//root//TorchProject//face-detect//Pictures");

    }

    public void setRedisTemplate(String date,String risk){
        String result = result(date);
        int i = result.indexOf("\"riskarea\":");
        String substring = "{"+result.substring(i,result.length()-2);
        JSONObject riskarea = JSON.parseObject(substring).getJSONObject("riskarea");
        Object[] objects = riskarea.getJSONArray(risk).toArray();
        List<Map<String, String>> maps = new ArrayList<>();
        HighDO highDO = new HighDO();

        for (Object o : objects) {
            Map<String, String> stringMap = AddressUtil.addressResolution(o.toString()).get(0);
            highDO.setProvince(stringMap.get("province"));
            highDO.setCity(stringMap.get("city"));
            highDO.setCounty(stringMap.get("county"));
            highDO.setTown(stringMap.get("town"));
            highDO.setVillage(stringMap.get("village"));
            highDO.setRisk(risk);
            timeService.insert(highDO);

            System.out.println(stringMap);
            maps.add(stringMap);
        }
        redisTemplate.opsForValue().set(risk,JSON.toJSONString(maps));
    }
    public String result(String date) {
        ByteArrayOutputStream out = null;
        try {
            URL url = new URL("http://api.tianapi.com/ncov/index?key=95a68c50ead35d496499bc739f1fba62&date="+date);
            InputStream is = url.openStream();
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int line = 0;
            while ((line = is.read(b)) > 0) {
                out.write(b, 0, line);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        byte[] b1 = out.toByteArray();
        return new String(b1, StandardCharsets.UTF_8);
    }

    //删除指定文件夹下的所有文件
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }
}