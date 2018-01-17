package com.ztth.user.biz;

import com.ztth.core.biz.BaseBiz;
import com.ztth.user.entity.AdminUser;
import com.ztth.user.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userAdminBiz")
public class UserAdminBiz extends BaseBiz<AdminUserMapper,AdminUser> {

    @Autowired
    private AdminUserMapper adminUserMapper;

    public AdminUser getAdminUserByMobile(String mobile){

        Map<String,String> map = new HashMap<String,String>();
        map.put("mobile",mobile);
        return adminUserMapper.getUserByMobile(map);
    }

    public int addAdminUser(AdminUser adminUser){
        return  adminUserMapper.insert(adminUser);
    }

    public int updateAdminUser(AdminUser adminUser){
        return  adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }
}
