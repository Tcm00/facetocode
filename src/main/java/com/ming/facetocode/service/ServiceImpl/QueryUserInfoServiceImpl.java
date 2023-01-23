package com.ming.facetocode.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.facetocode.mapper.QueryUserInfoMapper;
import com.ming.facetocode.pojo.DO.PassInfoDO;
import com.ming.facetocode.pojo.DO.UserInfoDO;
import com.ming.facetocode.pojo.VO.CodeInfoVO;
import com.ming.facetocode.pojo.VO.UserInfoVO;
import com.ming.facetocode.pojo.entity.ResultBody;
import com.ming.facetocode.service.QueryUserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 小明
 * @date 2022/4/10
 * @description
 */
@Service
public class QueryUserInfoServiceImpl extends ServiceImpl<QueryUserInfoMapper, UserInfoDO> implements QueryUserInfoService {

    @Autowired
    private QueryUserInfoMapper queryUserInfoMapper;

    @Override
    public ResultBody queryByNameInfo(Page<UserInfoVO> page,String name) {
        Page<UserInfoVO> voPage = queryUserInfoMapper.queryByNameInfo(page, "%" + name + "%");
        if (voPage!=null){
            return ResultBody.ok().data("userInfo",voPage).message("查询用户成功");
        }
        return ResultBody.error().message("未查询到用户");
    }

    @Override
    public ResultBody queryByIdCard(Page<UserInfoVO> page,String idCard) {
        Page<UserInfoVO> voPage = queryUserInfoMapper.queryByIdCard(page, "%" + idCard + "%");
        if (voPage!=null){
            return ResultBody.ok().data("userInfo",voPage).message("查询用户成功");
        }
        return ResultBody.error().message("未查询到用户");
    }

    @Override
    public ResultBody selectById(int id) {
        UserInfoVO userInfoVO = baseMapper.queryById(id);
        return ResultBody.ok().data("info",userInfoVO);
    }

    @Override
    public Page<UserInfoVO> selectByAll(Page<UserInfoVO> page) {
        return baseMapper.selectByAll(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBody addUser(UserInfoDO userInfoDO) {

        if ("".equals(userInfoDO.getName().trim()) || userInfoDO.getIdCard()==""){
            return ResultBody.error().message("注册失败：name和idCard必填");
        }
        if (userInfoDO.getIdCard().length() != 18){
            return ResultBody.error().message(userInfoDO.getIdCard()+"==>检查身份证长度");
        }
        QueryWrapper<UserInfoDO> wrapper = new QueryWrapper<>();
        wrapper.eq("idCard",userInfoDO.getIdCard());
        wrapper.isNull("deleteTime");
        UserInfoDO infoDO = baseMapper.selectOne(wrapper);

        if (infoDO!=null){
            return ResultBody.error().message(infoDO.getIdCard()+"==>不可以重复");
        }
        int age = getAge(userInfoDO.getIdCard());

        if (age < 0){
            return ResultBody.error().message(userInfoDO.getIdCard()+"==>请检查出生年月是否正确");
        }
        String sex = getsex(userInfoDO.getIdCard());
        userInfoDO.setAge(String.valueOf(age));
        userInfoDO.setSex(sex);
        userInfoDO.setId(0);
        int insert = 0;
//        springboot操作事务
        try {
            insert = baseMapper.insert(userInfoDO);
            UserInfoDO one = baseMapper.selectOne(wrapper);
            baseMapper.insertCode(one.getId(),new Date(),new Date());
        } catch (Exception e) {
//            手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            insert  = -1;
            System.out.println("添加用户未执行成功");
            e.printStackTrace();
        }
        if (insert>0){
            return ResultBody.ok().message(userInfoDO.getName()+":注册成功");
        }
        return ResultBody.error().message("注册失败");
    }

    private String getsex(String idCard) {
        String a = idCard.substring(16,17);
        String sex = "";
        if (Integer.parseInt(a)%2==0){
            sex = "女";
        }else {
            sex = "男";
        }
        return sex;
    }

    @Override
    public ResultBody getCount() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("all",baseMapper.getAll());
        map.put("red",baseMapper.getRedNum());
        map.put("yellow",baseMapper.getYellow());
        map.put("green",baseMapper.getGreen());
        return ResultBody.ok().data("count",map).message("查询统计信息成功");
    }

    @Override
    public Page<UserInfoVO> selectRedUser(Page<UserInfoVO> page) {
        return baseMapper.selectRedUser(page);
    }

    @Override
    public ResultBody queryByNameRed(Page<UserInfoVO> page, String name) {
        Page<UserInfoVO> voPage =  queryUserInfoMapper.queryByNameRed(page, "%" + name + "%");
        if (voPage!=null){
            return ResultBody.ok().data("userInfo",voPage).message("查询用户成功");
        }
        return ResultBody.error().message("未查询到用户");
    }

    @Override
    public ResultBody queryByIdCardRed(Page<UserInfoVO> page,@Param("idCard") String idCard) {
        Page<UserInfoVO> voPage = queryUserInfoMapper.queryByIdCardRed(page, "%" + idCard + "%");
        if (voPage!=null){
            return ResultBody.ok().data("userInfo",voPage).message("查询用户成功");
        }
        return ResultBody.error().message("未查询到用户");
    }

    private static int getAge(String idCard) {
        //截取身份证中出行人出生日期中的年、月、日
        Integer personYear = Integer.parseInt(idCard.substring(6, 10));
        Integer personMonth = Integer.parseInt(idCard.substring(10, 12));
        Integer personDay = Integer.parseInt(idCard.substring(12, 14));

        Calendar cal = Calendar.getInstance();
        // 得到当前时间的年、月、日
        Integer yearNow = cal.get(Calendar.YEAR);
        Integer monthNow = cal.get(Calendar.MONTH) + 1;
        Integer dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        Integer yearMinus = yearNow - personYear;
        Integer monthMinus = monthNow - personMonth;
        Integer dayMinus = dayNow - personDay;

        Integer age = yearMinus; //先大致赋值

        if (yearMinus == 0) { //出生年份为当前年份
            age = 0;
        }else{ //出生年份大于当前年份
            if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                age = age - 1;
            }
            if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                    age = age - 1;
                }
            }
        }
        return age;
    }

}
