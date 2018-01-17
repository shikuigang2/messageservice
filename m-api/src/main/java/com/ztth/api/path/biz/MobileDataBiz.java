package com.ztth.api.path.biz;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.entity.MobileData;
import com.ztth.api.path.mapper.MobileDataMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mobileDataBiz")
public class MobileDataBiz extends BaseBiz<MobileDataMapper,MobileData> {

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    public void  addMobileData(MobileData mobileData){
        super.insertSelective(mobileData);
    }

    public MobileData  selectMobileData ( int mobileMiddle){

        String mobileDataStr = redisQueueBiz.get(String.valueOf(mobileMiddle));
        MobileData mobileData = null;
        if(mobileDataStr == null){
            //查询数据 并添加到redis
            mobileData = mapper.selectMobileDataByMiddle(mobileMiddle);
            if(mobileData != null ){
                redisQueueBiz.set(String.valueOf(mobileMiddle), JSON.toJSONString(mobileData));
            }
        }else{
            mobileData = JSON.parseObject(mobileDataStr,MobileData.class)  ;
        }

        return mobileData;
    }



}
