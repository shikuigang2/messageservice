package com.ztth.api.path.biz;

import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.mapper.MessageLogMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.stereotype.Service;

@Service("msgLogBiz")
public class MsgLogBiz extends BaseBiz<MessageLogMapper,MessageLog> {

    public void  addMsgLog(MessageLog msgLog){
        super.insertSelective(msgLog);
    }

    public void updateMsgLog(MessageLog msgLog){
        super.updateSelectiveById(msgLog);
    }

}
