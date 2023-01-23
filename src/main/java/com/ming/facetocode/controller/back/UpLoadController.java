package com.ming.facetocode.controller.back;

import com.ming.facetocode.mapper.QueryUserInfoMapper;
import com.ming.facetocode.pojo.entity.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @author 小明
 * @date 2022/4/23
 * @description
 */
@Api(tags = "上传用户头像")
@RestController
public class UpLoadController {
    @Autowired
    private QueryUserInfoMapper queryUserInfoMapper;

    @ApiOperation(value = "上传用户初始头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "文件",dataType = "File",paramType = "POST",required = true),
            @ApiImplicitParam(name = "id",value = "唯一标识",dataType = "String",paramType = "POST",required = true)
    })

    @PostMapping("/upload")
    public ResultBody facePic(@RequestParam MultipartFile file,@RequestParam String id, HttpServletRequest request){

        if (file.isEmpty()) {
            return ResultBody.error().message("文件为空");
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffixName);
//        路径：C://temp-seat//
        // 上传后的路径
        String filePath = "//root//titlePic//";
        // 新文件名
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "https://facetocode.xyz:9090/title?picName="+fileName;
        boolean b = queryUserInfoMapper.updateUser(id, url);

        return ResultBody.ok().data("fileURL","https://facetocode.xyz:9090/title?picName="+fileName).message(fileName+"=>图片上传成功"+b);
    }

    @ApiOperation("生成图片链接")
    @GetMapping(value = "/title",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPic(String picName) throws Exception {

        File file = new File("//root//titlePic//"+picName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
