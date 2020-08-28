package com.zf.study.web.controller;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private DefaultMQProducer mqProducer;

    @RequestMapping("/open/index")
    public Object index() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for(int i = 0; i < 10; i ++) {
            String body = "hello rocketMQ" + i;
            //注意，第一个参数是topic，第二个tag，要和配置文件保持一致，第三个是body，也就是我们要发的消息，字节类型。
            Message message = new Message("topic2020", "test", body.getBytes());
            System.out.println("发送消息:"+body);
            SendResult result = mqProducer.send(message);
            Thread.sleep(1000);
        }
        //关闭资源
        mqProducer.shutdown();
        System.out.println("producer shutdown!");
        return "success";
    }
}
