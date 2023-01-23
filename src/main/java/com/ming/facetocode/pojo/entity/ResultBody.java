package com.ming.facetocode.pojo.entity;



import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小明
 * @date 2021/10/19
 * @description
 */
@Data
public class ResultBody {
    //是否成功
    private Boolean success;

    //返回码
    private Integer code;

    //返回信息
    private String message;

    //返回数据
    private Map<String,Object> data = new HashMap<>();

    //附带说明
    private Map<String,String> expound = new HashMap();

    private ResultBody(){}

    /**
     * 请求成功
     * @return
     */
    public static ResultBody ok(){
        ResultBody r =new ResultBody();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    /**
     * 请求失败
     * @return
     */
    public static ResultBody error(){
        ResultBody resultBody = new ResultBody();
        resultBody.setSuccess(false);
        resultBody.setCode(100);
        resultBody.setMessage("失败");
        return resultBody;
    }

    /**
     * 设置返回信息
     * @param message
     * @return
     */
    public ResultBody message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 设置返回code
     * @param code
     * @return
     */
    public ResultBody code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 设置返回数据
     * @param key
     * @param value
     * @return
     */
    public ResultBody data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    /**
     * 设置返回类型
     * <br/> 基本引用类型、列表类型都不能使用该方法 <br/> 对map集合、对象兼容 <br/> <strong>使用时考虑清楚</strong>
     * @param data
     * @return
     */
//    @Deprecated
    public ResultBody data(Object data){
        BeanUtils.copyProperties(data,this.data);
        return this;
    }

}
