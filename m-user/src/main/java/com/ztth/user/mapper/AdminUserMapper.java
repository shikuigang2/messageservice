package com.ztth.user.mapper;

import com.ztth.user.entity.AdminUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface AdminUserMapper extends Mapper<AdminUser> {
    AdminUser getUserByMobile(Map<String,String> map);

    int updateUserByMobile(AdminUser adminUser);
}