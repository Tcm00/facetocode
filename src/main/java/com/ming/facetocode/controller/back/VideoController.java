package com.ming.facetocode.controller.back;

import com.ming.facetocode.pojo.entity.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 小明
 * @date 2022/4/26
 * @description
 */
@Api(tags = "视频播放")
@RestController
public class VideoController {


    @ApiImplicitParam(name = "fileName",value = "文件名",dataType = "String",example = "login.mp4")
    @GetMapping("/getVideo")
    public ResultBody getVideo(@RequestParam String fileName, HttpServletResponse response){
//        String file  = "C:\\Users\\Tcm\\Videos\\Captures\\";
        String file  = "//root//titlePic//video//";
        try {
            FileInputStream inputStream = new FileInputStream(file+fileName);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();

            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultBody.ok().data("videoUrl","https://facetocode.xyz:9090/getVideo?fileName="+fileName);
    }
}
