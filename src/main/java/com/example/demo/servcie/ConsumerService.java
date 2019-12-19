package com.example.demo.servcie;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 消费者
 */
@Service
public class ConsumerService {

    public static void main(String[] args) throws MQClientException {
        // 创建一个带组名的消费者实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // 指定MQ服务IP地址/域名
        consumer.setNamesrvAddr("localhost:9876");

        // 为消费者订阅一个或多个topics
        consumer.subscribe("TopicTest", "*");

        // 从broker拿到的消息之后执行的方法，do sth.
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt msg: msgs ) {
                        System.out.println("收到消息:" + new String(msg.getBody()));
                    }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者实例
        consumer.start();

        System.out.printf("消费者已准备就绪.%n");
    }


}
