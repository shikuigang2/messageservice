package com.ztth.api.path.mapper;

import com.ztth.api.path.entity.MobileData;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MobileDataMapper extends Mapper<MobileData> {

     MobileData selectMobileDataByMiddle(@Param("mobilemiddle")int mobilemiddle);

}