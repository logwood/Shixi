package org.example.shixi.service.base;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Component
@Slf4j
@ServerEndpoint("/websocket/{sid}")
public class WebSocketService {
    private static int onlineCount = 0;
    //线程安全ArraySet
    private static final CopyOnWriteArraySet<WebSocketService> writeArraySet
            = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid ="";
    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid){
        this.session =session;
        writeArraySet.add(this);
        this.sid = sid;
        addOnlineCount();
        try{
            sendMessage("Conn_success");
            log.info("有新窗口监听:"+ sid +"当前在线窗口数为："+getOnlineCount());
        }catch (Exception e){
            log.error(e.getStackTrace().toString());
        }
    }
    @OnClose
    public void  onClose(){
        writeArraySet.remove(this);
        subOnlineCount();
        log.info("释放sid为"+sid);
        log.info("连接关闭");
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketService item : writeArraySet) {
            try{
                item.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    private static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private void sendMessage(String connSuccess)throws IOException{
        this.session.getBasicRemote().sendText(connSuccess);
    }
    public void sendInfo(String message,@PathParam("sid")String sid){
        log.info("推送消息到窗口"+sid+"，推送内容"+message);
        for(WebSocketService item:writeArraySet){
            try{
                if(sid == null){
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            }catch (IOException e){
            }
        }
    }
    private void addOnlineCount() {
        WebSocketService.onlineCount++;
    }
    public static CopyOnWriteArraySet<WebSocketService> getWriteArraySet(){
        return writeArraySet;

    }
}
