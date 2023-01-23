package com.ming.facetocode.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小明
 * @date 2022/4/17
 * @description
 */
public class Main {
    public static void main(String[] args) {
        String host = "https://regeo.market.alicloudapi.com";
        String path = "/v3/geocode/regeo";
        String method = "GET";
        String appcode = "eba1c90e343a4fa8989ca6681ff4ec64";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("batch", "false");
//        querys.put("callback", "");
        querys.put("extensions", "base");
        querys.put("homeorcorp", "2");
        querys.put("location", "121.82,30.45");
        querys.put("output", "JSON");
        querys.put("radius", "1000");
        querys.put("roadlevel", "0");


        Map<String, String> map;
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
            System.out.println("========>"+response.toString());
            //获取response的body
//            System.out.println("======>" + new String(EntityUtils.toString(response.getEntity()).getBytes("utf-8"), "gbk"));
            String json = new String(EntityUtils.toString(response.getEntity()).getBytes(StandardCharsets.UTF_8), "gbk");
            System.out.println(json);
            String string = JSON.parseObject(json).getJSONObject("regeocode").getString("formatted_address");
            System.out.println(string);

            map = AddressUtil.addressResolution(string).get(0);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
