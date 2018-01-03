package com.ztth.user.rest;

import com.alibaba.fastjson.JSON;
import com.ztth.core.util.Blowfish;
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

        AdminUser user =  userAdminBiz.getAdminUserByMobile(mobile);
        if(user == null){
            return ResponseEntity.status(404).body("{error:\"invalide mobile\"}");
        }else{
            if(user.getPassword().equals(key)){
                Map<String,String> map = new HashMap<String,String>();
                String token = new Blowfish(key).encryptString(mobile);
                redisUserBiz.set(token,JSON.toJSONString(user), UserConstant.EXPIRT_USER);
                return ResponseEntity.status(200).body("{token:"+token+"}");
            }else{
                return ResponseEntity.status(404).body("{error: \"invalide key\"}");
            }
        }



    }

    @RequestMapping(value = "/sendGet")
    @ResponseBody
    public ResponseEntity<?> sendGet(String mobile,String content) throws Exception {


        return  null;

    }
}
