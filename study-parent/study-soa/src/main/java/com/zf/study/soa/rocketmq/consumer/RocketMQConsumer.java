package com.zf.study.soa.rocketmq.consumer;

import com.zf.study.soa.rocketmq.message.MessageProcessor;
import com.zf.study.soa.rocketmq.message.listen.MessageListen;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RocketMQConsumer {
    @Autowired
    private MessageProcessor messageProcessor;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.topic}")
    private String topic;
    @Value("${rocketmq.consumer.tag}")
    private String tag;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer()
    {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);
        //我们自己实现的监听类
        MessageListen messageListen = new MessageListen();
        messageListen.setMessageProcessor(messageProcessor);
        consumer.registerMessageListener(messageListen);
        try {
            consumer.subscribe(topic,tag);
            consumer.start();
            log.info("consume is start ,groupName:{},topic:{}",groupName,topic);
        } catch (MQClientException e) {
            log.error("consume start error");
            e.printStackTrace();
        }
        return consumer;
    }
}
