package org.example.shixi.controller.base;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.shixi.Component.rabbitmq.SenderComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/queue/tryOne")
@Slf4j
@Controller
@Tag(name = "消息队列接口")
public class MessageQueueController {
    @Autowired
    private SenderComponent senderHelloWorld;
    @GetMapping
    @Operation(summary = "发送方")
    public void helloSender(){
        senderHelloWorld.Send();
    }
}
