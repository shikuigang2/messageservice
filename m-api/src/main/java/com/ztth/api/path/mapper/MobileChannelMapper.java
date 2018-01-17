package com.ztth.api.path.mapper;

import com.ztth.api.path.entity.MobileChannel;
import com.ztth.api.path.entity.MobileData;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MobileChannelMapper extends Mapper<MobileChannel> {

    List<MobileChannel> selectActiveChannel();

}