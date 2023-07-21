package org.example.shixi.Component.rabbitmq;

import org.example.shixi.util.CryptionUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "q_hello")
public class ReceiverComponent {
    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver  : " + CryptionUtil.decrypt(hello));
    }
}
