package com.ztth.user.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ztth.core.msg.BaseResponse;
import com.ztth.core.util.Blowfish;
import com.ztth.core.util.PhoneFormatCheckUtils;
import com.ztth.user.constant.UserConstant;
import com.ztth.user.biz.RedisUserBiz;
import com.ztth.user.biz.UserAdminBiz;
import com.ztth.user.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserAdminController {

    @Autowired
    private UserAdminBiz userAdminBiz;

    @Autowired
    private RedisUserBiz redisUserBiz;

    @RequestMapping(value = "/getToken")
    @ResponseBody
    public ResponseEntity<?> sendPost(String mobile,String key) throws Exception {
        //System.out.println(mobile);
        Map<String,Object> resMap = new HashMap<String,Object>();

        BaseResponse response = new BaseResponse();
        AdminUser user =  userAdminBiz.getAdminUserByMobile(mobile);
        if(user == null){
           // resMap.put("code",400);
            response.setStatus(400);
            response.setMessage("invalid mobile");
            resMap.put("msg","invalid mobile");
            return ResponseEntity.status(400).body(resMap);
        }else{
            if(user.getKey().equals(key)){
                Map<String,String> map = new HashMap<String,String>();
                String token = new Blowfish(key).encryptString(mobile);
                redisUserBiz.set(token,JSON.toJSONString(user), UserConstant.EXPIRT_USER);
                resMap.put("code",200);
                resMap.put("token",token);
                resMap.put("expire",UserConstant.EXPIRT_USER);
                return ResponseEntity.status(200).body(resMap);
            }else{
                resMap.put("code",400);
                resMap.put("msg","invalid key");
                return ResponseEntity.status(400).body(resMap);
            }
        }
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<?> sendGet(AdminUser adminUser) throws Exception {

        String  pass =  adminUser.getKey();
        String  mobile =  adminUser.getMobile();
        String  username = adminUser.getUsername();

        BaseResponse response  = new BaseResponse();

        if(pass == null){
            response.setStatus(500);
            response.setMessage("key is null");
            return  ResponseEntity.status(500).body(response);
        }else if(mobile == null || !PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)){
            response.setStatus(500);
            response.setMessage("mobile error");
            return  ResponseEntity.status(500).body(response);
        }else if(username == null){
            response.setStatus(500);
            response.setMessage("username is null");
            return  ResponseEntity.status(500).body(response);
        }

        AdminUser ad = userAdminBiz.getAdminUserByMobile(mobile);

        if(ad != null){
            response.setStatus(500);
            response.setMessage("mobile exist");
            return  ResponseEntity.status(500).body(response);
        }

        int i = userAdminBiz.addAdminUser(adminUser);

        if(i==0){
           return  ResponseEntity.status(500).body("{code:500,}");
        }else{
            return ResponseEntity.status(200).body("{code:200,msg:'success'}");
        }

    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseEntity<?> updateUser(AdminUser adminUser) throws Exception {

        int i = userAdminBiz.updateAdminUser(adminUser);

        if(i==1){
            return ResponseEntity.status(200).body("{code:200,msg:'success'}");
        }else{
            return ResponseEntity.status(500).body("{code:500,msg:'error'}");
        }
       //return null;
    }

    @RequestMapping(value = "/getUserByToken")
    @ResponseBody
    public ResponseEntity<?> getAdminUser(String token) throws Exception {

        String userStr = redisUserBiz.get(token.trim());

        if(userStr == null){
            return ResponseEntity.status(500).body("{code:500,msg:'token invalid or overdue'}");
        }else{

            AdminUser user  = JSON.parseObject(userStr, new TypeReference<AdminUser>() {});
            //user.setPassword(null);
            user.setKey(null);
            //user.setStatus(null);
            return ResponseEntity.status(200).body(user);
        }

    }

    @RequestMapping(value = "/getAdminList")
    @ResponseBody
    public ResponseEntity<?> getAdminList(String token) throws Exception {

        String userStr = redisUserBiz.get(token.trim());

        if(userStr == null){
            return ResponseEntity.status(500).body("{code:500,msg:'token invalid or overdue'}");
        }else{

            return ResponseEntity.status(200).body(userAdminBiz.getAdminUserList());
        }

    }
}
