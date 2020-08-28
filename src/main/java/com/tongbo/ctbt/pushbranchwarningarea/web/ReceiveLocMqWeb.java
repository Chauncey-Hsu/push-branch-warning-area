package com.tongbo.ctbt.pushbranchwarningarea.web;

import com.rabbitmq.client.*;
import com.tongbo.ctbt.pushbranchwarningarea.dic.Constant;
import com.tongbo.ctbt.pushbranchwarningarea.service.LocMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lenovo
 */
@Component
public class ReceiveLocMqWeb {
    Logger logger = LoggerFactory.getLogger(ReceiveLocMqWeb.class);

    @Autowired
    LocMqService locMqService;

    public void receive() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.RABBITMQ_IP_BEIDOU_PUSH);
        factory.setPort(Constant.RABBITMQ_PORT_BEIDOU_PUSH);
        factory.setUsername(Constant.RABBITMQ_USER_BEIDOU_PUSH);
        factory.setPassword(Constant.RABBITMQ_PASS_BEIDOU_PUSH);
        factory.setVirtualHost(Constant.RABBITMQ_VIRTUALHOST_BEIDOU_PUSH);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_NAME_BEIDOU_PUSH, BuiltinExchangeType.FANOUT, true);
        String queueName = channel.queueDeclare(Constant.QUEUE_NAME_BEIDOU_PUSH, true, false,
                false, null).getQueue();
        System.out.println("queueName: " + queueName);

        channel.queueBind(queueName, Constant.EXCHANGE_NAME_BEIDOU_PUSH, Constant.ROUTING_BEIDOU_PUSH);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
//            logger.info(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            locMqService.handler(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
