package com.ztth.api.path.biz;

import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.entity.MobileData;
import com.ztth.api.path.mapper.MessageLogMapper;
import com.ztth.api.path.mapper.MobileDataMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mobileDataBiz")
public class MobileDataBiz extends BaseBiz<MobileDataMapper,MobileData> {

    public void  addMobileData(MobileData mobileData){
        super.insertSelective(mobileData);
    }

    public MobileData  selectMobileData ( int mobieMidle){
        return mapper.selectMobileDataByMiddle(mobieMidle);
    }

}
