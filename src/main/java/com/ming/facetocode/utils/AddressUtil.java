package com.ming.facetocode.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小明
 * @date 2022/4/21
 * @description
 */
public class AddressUtil {
    public static Map<String,String> getAddress(String row,String rol){
        String host = "https://regeo.market.alicloudapi.com";
        String path = "/v3/geocode/regeo";
        String method = "GET";
        String appcode = "eba1c90e343a4fa8989ca6681ff4ec64";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("batch", "false");
//        querys.put("callback", "");
//        querys.put("extensions", "base");
//        querys.put("homeorcorp", "2");
//        querys.put("location", "116.47089234140496,39.9976009239991");
        querys.put("location", row+","+rol);
//        querys.put("output", "JSON");
//        querys.put("radius", "1000");
//        querys.put("roadlevel", "0");

        Map<String,String> map = new HashMap<>();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtil.doGet(host, path, method, headers, querys);
            //获取response的body
            String json = new String(EntityUtils.toString(response.getEntity()).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            System.out.println(json);
            String str = JSON.parseObject(json).getJSONObject("regeocode").getString("formatted_address");
            System.out.println(str);
//            String pre = str.substring(0, 3);
//            System.out.println(pre);
//            if ("北京市".equals(pre) || "天津市".equals(pre) || "重庆市".equals(pre) || "上海市".equals(pre)){
//                str = "直辖市"+str;
//            }
            map = addressResolution(str).get(0);
            Map<String,String> map2 = addressResolution(str).get(1);
            System.out.println(map);
            System.out.println(map2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 解析地址
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        String pre = address.substring(0, 3);
        System.out.println(pre);
        if ("北京市".equals(pre) || "天津市".equals(pre) || "重庆市".equals(pre) || "上海市".equals(pre)){
            address = "直辖市"+address;
        }
        /*
         * java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher Pattern
         *    一个Pattern是一个正则表达式经编译后的表现模式。 Matcher
         *    一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         *    首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行		   *	字符串的匹配工作。
         */
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?(?<city>[^?市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)?(?<county>[^县]+县|.*?区|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        //(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?表示一个模块 最后的问号表示可以为空  .*省|.*自治区|上海|北京|天津|重庆
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

}
