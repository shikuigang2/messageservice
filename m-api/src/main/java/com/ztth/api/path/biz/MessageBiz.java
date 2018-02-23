package com.ztth.api.path.biz;

import com.ztth.api.path.entity.Message;
import com.ztth.api.path.mapper.MessageMapper;
import com.ztth.core.biz.BaseBiz;
import org.springframework.stereotype.Service;

@Service
public class MessageBiz extends BaseBiz<MessageMapper,Message> {

    public void  addMsgLog(Message msgLog){
        //msgLog.setId(new IdGenerator(8).nextId());//数字8 是进程数
        super.insertSelective(msgLog);
    }

    public void updateMsgLog(Message msgLog){
        super.updateSelectiveById(msgLog);
    }
}
