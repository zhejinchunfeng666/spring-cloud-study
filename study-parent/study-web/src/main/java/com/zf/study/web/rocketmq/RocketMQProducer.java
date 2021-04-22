//package com.zf.study.web.rocketmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
////@Component
//@Slf4j
//public class RocketMQProducer {
//    @Value("${rocketmq.producer.groupName}")
//    private String groupName;
//    @Value("${rocketmq.producer.namesrvAddr}")
//    private String nameserAddr;
//    @Value("${rocketmq.producer.instanceName}")
//    private String instanceName;
//    @Value("${rocketmq.producer.maxMessageSize}")
//    private int maxMessageSize;
////    @Value("${rocketmq.producer.sendMsgTimeout}")
////    private int sendMsgTimeout;
//
//    private DefaultMQProducer producer;
//
//    @Bean
//    public DefaultMQProducer getRocketMQProducer() {
//
//        producer = new DefaultMQProducer(groupName);
//        producer.setNamesrvAddr(nameserAddr);
//        producer.setInstanceName(instanceName);
//        producer.setMaxMessageSize(maxMessageSize);
//        producer.setSendMsgTimeout(sendMsgTimeout);
//        producer.setVipChannelEnabled(false);
//
//        try {
//            producer.start();
//            log.info("rocketMQ is start !!groupName : {},nameserAddr:{}",groupName,nameserAddr);
//        } catch (MQClientException e) {
//            log.error(String.format("rocketMQ start error,{}",e.getMessage()));
//            e.printStackTrace();
//        }
//        return producer;
//    }
//}
