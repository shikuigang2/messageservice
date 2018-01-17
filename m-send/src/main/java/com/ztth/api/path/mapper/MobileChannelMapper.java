package com.ztth.api.path.mapper;

import com.ztth.api.path.entity.MobileChannel;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface MobileChannelMapper extends Mapper<MobileChannel> {

    MobileChannel getMobileChannelByEnterpriseNum(Map<String,String> map);
}