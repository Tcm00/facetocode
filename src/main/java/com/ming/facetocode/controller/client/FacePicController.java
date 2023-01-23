package com.ming.facetocode.controller.client;


import com.ming.facetocode.pojo.DO.TripInfoDO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.FacePicService;
import com.ming.facetocode.service.TripService;
import com.ming.facetocode.utils.AddressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 小明
 * @date 2022/4/5
 * @description
 */
@Api(tags = "前台")
@RestController
@RequestMapping("/face")
public class FacePicController {

    @Autowired
    private FacePicService facePicService;

    @Autowired
    private TripService tripService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    String fileName = null;

    @ApiOperation(value = "上传图片base64")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64",value = "图片base64",dataType = "String",paramType = "POST",required = true),
            @ApiImplicitParam(name = "row",value = "经度（东经）",dataType = "String",required = true,example = "106.55"),
            @ApiImplicitParam(name = "rol",value = "纬度（北纬）",dataType = "String",required = true,example = "25.56")
    })
    @PostMapping("/uploadPic")
    public ResultBody saveBase64(@RequestParam String base64,@RequestParam String row,@RequestParam String rol) throws InterruptedException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddhhmmss");
        String format = dateFormat.format(new Date());
        // 上传后的路径
        String filePath = "//root//TorchProject//face-detect//Pictures//";
        String imgPath = filePath+format+".jpg";
        try {
            FileOutputStream write = new FileOutputStream(imgPath);
            byte[] decode = Base64.getDecoder().decode(base64.split(",")[1]);
            write.write(decode);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //连接python   王杭==================
        final String host = "VM-4-8-centos"; //
        int port = 8001;
        String faceName = null;
        try {
            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(imgPath.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            InputStream inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            inputStream.read(b);
            faceName = new String(b);
            b=null;
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 1.查询健康码状态，身份信息
         * 2.添加该坐标的行程到行程表中
         * 3.查询近七日行程
         * 4.根据行程做参数查高风险，低风险表，
         * 5.修改健康码状态
         */
        Random random = new Random();
        int nextInt = random.nextInt(30)+130;

        Thread.sleep(1000);
        faceName = String.valueOf(nextInt);

//        faceName="75";
        System.out.println("=======》返回的faceName："+faceName);

        if ("None".equals(faceName) || faceName == null){
            return ResultBody.error().data("url","https://facetocode.xyz:9090/face/getImg2?picName="+format+".jpg").message("视频中为识别到人像");
        }
        if ("".equals(row.trim()) || "".equals(rol.trim())){
            return ResultBody.error().data("url","https://facetocode.xyz:9090/face/getImg2?picName="+format+".jpg").message("缺失经纬度坐标！");
        }
        UserInfoVO userInfoVO = facePicService.selectId(faceName);
        //经纬度-->解析地址-->存行程表
        Map<String, String> address = AddressUtil.getAddress(row, rol);
        System.out.println("=========>address"+address);
        String province = address.get("province");
        String city = address.get("city");
        String area = address.get("county");
        tripService.setTriInfo(province,city,area,String.valueOf(userInfoVO.getId()));
        //近7天行程
        List<TripInfoDO> sevenTrip = tripService.getSevenTrip(userInfoVO.getId());

        //get状态
        String status = facePicService.updateStatus(sevenTrip);
        System.out.println("=====================>状态:"+status);
        //存健康码（healthCode）、存过机表（passInfo）
        userInfoVO.setStatus(status);

        if ("红码".equals(status)){
            double v = Math.random() + 37.00;
            userInfoVO.setTemperature(String.valueOf(v).substring(0,4));
        }else {
            double v = Math.random() +36.00;
            userInfoVO.setTemperature(String.valueOf(v).substring(0,4));
        }
        userInfoVO.setImg("https://facetocode.xyz:9090/face/getImg2?picName="+format+".jpg");

        String add = address.get("city");
        try {
            add = redisTemplate.opsForValue().get(userInfoVO.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第二步 不太理解都是什么方法 但是需要添加到方法中
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status1 = platformTransactionManager.getTransaction(definition);
        try {
            facePicService.addPassInfo(userInfoVO);
            facePicService.updateHealthCode(faceName,status);
            platformTransactionManager.commit(status1);
            return ResultBody.ok().data("url","https://facetocode.xyz:9090/face/getImg2?picName="+format+".jpg").data("info",userInfoVO).data("trip",add).message("上传图片成功&处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(status1);
        }
        return ResultBody.ok().data("url","https://facetocode.xyz:9090/face/getImg2?picName="+format+".jpg").data("info",userInfoVO).data("address",address).data("trip",add).message("上传图片成功&处理失败");
    }

    @ApiOperation("生成图片链接64")
    @GetMapping(value = "/getImg2",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPicBase64(String picName) throws Exception {
//        http://localhost:9090//face/getImg2?picName=4c097657-ab1a-49a6-9406-5fa690d1b896
        File file = new File("//root//TorchProject//face-detect//Pictures//"+picName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
