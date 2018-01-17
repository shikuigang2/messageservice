package com.ztth.api.path.biz;

import com.ztth.api.path.entity.MobileChannel;
import com.ztth.api.path.mapper.MobileChannelMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileChannelBiz extends BaseBiz<MobileChannelMapper,MobileChannel> {

    @Autowired
    private MobileChannelMapper mobileChannelMapper ;

    @Override
    public List<MobileChannel> selectListAll() {
        return super.selectListAll();
    }

}
