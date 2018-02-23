package com.ztth.api.path.biz;

import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.mapper.MessageLogMapper;
import com.ztth.api.path.mapper.MessageMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.stereotype.Service;

@Service
public class MsgLogBiz extends BaseBiz<MessageLogMapper,MessageLog> {

    public void  addMsgLog(MessageLog msgLog){
        //msgLog.setId(new IdGenerator(8).nextId());//数字8 是进程数
        super.insertSelective(msgLog);
    }

    public void updateMsgLog(MessageLog msgLog){
        super.updateSelectiveById(msgLog);
    }
}
