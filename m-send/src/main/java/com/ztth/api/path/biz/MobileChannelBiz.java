package com.ztth.api.path.biz;

import com.ztth.api.path.entity.MobileChannel;
import com.ztth.api.path.mapper.MobileChannelMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.stereotype.Service;

@Service("mobileChannelBiz")
public class MobileChannelBiz extends BaseBiz<MobileChannelMapper,MobileChannel> {

        public MobileChannel getMobileChannelByMobile(String mobile){
            return null;
        }

}
