package com.ming.facetocode.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.SinaDataService;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author 小明
 * @date 2022/4/11
 * @description
 */
@Service
public class SinaDataServiceImpl implements SinaDataService {

    @Override
    public ResultBody getSinaData() {
        String data = getData();
        JSONObject object = JSON.parseObject(data);
        return ResultBody.ok().data("data",object);
    }

    public String getData() {
        ByteArrayOutputStream out = null;
        try {
            URL url = new URL("https://interface.sina.cn/news/wap/fymap2020_data.d.json");
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
}
