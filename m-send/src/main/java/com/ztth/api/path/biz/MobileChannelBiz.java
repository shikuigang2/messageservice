package com.ztth.api.path.biz;

import com.ztth.api.path.entity.MobileChannel;
import com.ztth.api.path.mapper.MobileChannelMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mobileChannelBiz")
public class MobileChannelBiz extends BaseBiz<MobileChannelMapper,MobileChannel> {


    @Autowired
    private MobileChannelMapper mobileChannelMapper;

    public MobileChannel getMobileChannelByMobile(String mobile){
            return null;
        }

    /**
     * 通过企业号获取 所在渠道信息
     * @param enterpirseNum
     * @return
     */
    public MobileChannel getMaxConcurrentNumber(String enterpirseNum){

        Map<String,String> map = new HashMap<String,String>();
        map.put("enterpriseCode",enterpirseNum);

        return  mobileChannelMapper.getMobileChannelByEnterpriseNum(map);
    }



}
