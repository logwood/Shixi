package org.example.shixi.Component.rabbitmq;


import org.example.shixi.util.CryptionUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SenderComponent {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void Send()
    {
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date());
            this.rabbitTemplate.convertAndSend("q_hello",CryptionUtil.encrypt(date));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
