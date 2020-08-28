package com.zf.study.soa.rocketmq.message.impl;

import com.zf.study.soa.rocketmq.message.MessageProcessor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessorImpl implements MessageProcessor {
    @Override
    public boolean handle(MessageExt messageExt) {
        String result = new String(messageExt.getBody());
        System.out.println("收到了消息："+ result);
        return true;
    }
}
