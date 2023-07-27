package org.example.shixi.controller.base

import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.service.base.WebSocketService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@Tag(name = "websocket接口")
@Slf4j
@RestController
@RequestMapping("/websocket")
class WebSocketController {
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    fun pushToWeb(@PathVariable cid:String,message:String):Map<String, Any>{
        val result:MutableMap<String, Any> = HashMap()
        try {
            WebSocketService::sendInfo.call(message,cid)
            result["code"] = cid
            result["msg"] =message
        }catch (e:IOException){
            e.printStackTrace()
        }
        return result
    }

}