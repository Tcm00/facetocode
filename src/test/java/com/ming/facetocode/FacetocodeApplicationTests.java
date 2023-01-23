package com.ming.facetocode;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ming.facetocode.pojo.DO.*;
import com.ming.facetocode.pojo.VO.RiskVO;
import com.ming.facetocode.service.FacePicService;
import com.ming.facetocode.service.TableService;
import com.ming.facetocode.utils.AddressUtil;
import com.ming.facetocode.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

@SpringBootTest
class FacetocodeApplicationTests {

    @Test
    void contextLoads() {

    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    void redis(){
        redisTemplate.opsForValue().set("code","中文");
        String code = redisTemplate.opsForValue().get("code");
        System.out.println(code);
        String meiyou = redisTemplate.opsForValue().get("meiyou");
        System.out.println("=======》"+meiyou);
        System.out.println(meiyou==null);
        System.out.println(Objects.equals(meiyou, ""));
    }


    @Test
    void Json() {

        String substring = DateUtil.getCurrentTime().substring(0, 11);
        setRedisTemplate(substring,"high");
        setRedisTemplate(substring,"mid");
    }
    public void setRedisTemplate(String date,String risk){
        String result = result(date);
        int i = result.indexOf("\"riskarea\":");
        String substring = "{"+result.substring(i,result.length()-2);
        JSONObject riskarea = JSON.parseObject(substring).getJSONObject("riskarea");
        Object[] objects = riskarea.getJSONArray(risk).toArray();
        List<Map<String, String>> maps = new ArrayList<>();

        for (Object o : objects) {

            Map<String, String> stringMap = addressResolution(o.toString()).get(0);
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

    /**
     * 解析地址
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        /*
         * java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher Pattern
         *    一个Pattern是一个正则表达式经编译后的表现模式。 Matcher
         *    一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         *    首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行		   *	字符串的匹配工作。
         */
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)?(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        //(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?表示一个模块 最后的问号表示可以为空
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }


    @Autowired
    private TableService tableService;

    @Test
    void setTableService(){
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

    @Test
    void address(){
        Map<String, String> address = AddressUtil.getAddress("105.58", "30.22");
        System.out.println(address);
    }


    @Test
    void getAddress(){
        String add = "河北省朝阳市望京区街道巴山猴私家菜望京西园四区";
        String pre = add.substring(0, 3);
        System.out.println(pre);
        if (pre.equals("北京市") || pre=="天津市" || pre=="重庆市" || pre=="上海市"){
            add = "直辖市"+add;
        }
        List<Map<String, String>> str = AddressUtil.addressResolution(add);
        System.out.println(str);
        System.out.println(str.get(0));
    }

    @Autowired
    private FacePicService facePicService;

    @Test
    void getStatus(){
        TripInfoDO tripInfoDO = new TripInfoDO();
        tripInfoDO.setProvince("吉林省");
        tripInfoDO.setCity("长春市");
        tripInfoDO.setArea("宽城区");
        ArrayList<TripInfoDO> tripInfoDOS = new ArrayList<>();
        tripInfoDOS.add(tripInfoDO);
        String status = facePicService.updateStatus(tripInfoDOS);
        System.out.println("==========>状态："+status);
    }

    @Test
    void getNull(){
        String aa = null;
        aa = redisTemplate.opsForValue().get("79");
        if (aa==null) System.out.println(1);
        if ("".equals(aa)) System.out.println(2);
        if ("null".equals(aa)) System.out.println(3);
    }

    @Test
    void getRedis(){
        String s = redisTemplate.opsForValue().get(133);
        System.out.println(s);
    }
}
