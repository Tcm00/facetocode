package com.ming.facetocode.utils;

import sun.misc.BASE64Decoder;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 小明
 * @date 2022/4/23
 * @description
 */
public class Base64ToPhoto {
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Base64转换为图片服务
     * targetPath  输出视频文件路径,不需要文件名
     * */
    public static File base64ToImg(String base64, String targetPath){
        if (base64 == null || "".equals(base64)){
            return null;
        }
        File file = null;
        FileOutputStream fops = null;
        base64 = base64.replace("data:image/jpeg;base64,","");
        byte[] buff = DatatypeConverter.parseBase64Binary(base64);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddhhmmssMs");
        String format = dateFormat.format(new Date());
        try {

            file = File.createTempFile(UUID.randomUUID().toString(),".jpeg",new File(targetPath));
            fops = new FileOutputStream(file);
            fops.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------"+"图片转换完成"+"--------------------------------");
        return file;
    }
}
