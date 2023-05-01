package ltd.newbee.mall.api.admin;

import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.api.admin.param.WebsocketParams;
import ltd.newbee.mall.dao.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.entity.MallUserToken;
import ltd.newbee.mall.entity.Message;
import ltd.newbee.mall.service.MessageService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.BeanUtil;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/chat/{userId}")
public class websocketController {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户ID
     */
    private String mobileId;

    private String shopId;

    private static MessageService messageService;

    private  static  NewBeeMallUserService newBeeMallUserService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        websocketController.messageService = messageService;
    }

  @Autowired
  public void setUserIdService(NewBeeMallUserService newBeeMallUserService){
        websocketController.newBeeMallUserService=newBeeMallUserService;
  }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<websocketController> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
//            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
//            System.out.println(userId);
            String[] split = userId.split("-");
            this.shopId=split[0];
            this.mobileId=split[1];
//            System.out.println("111111111:"+newBeeMallUserService.getUserIdByToken(token));
//            this.mobileId=newBeeMallUserService.getUserIdByToken(token);
//            System.out.println(this.mobileId);
//            MallUserToken mallUserToken = newBeeMallUserTokenMapper.selectByToken(token);
//            userId=mallUserToken.getUserId().toString();
//            System.out.println(userId);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            System.out.println(this);
            webSockets.remove(this);
//            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param messageParams
     */
    @OnMessage
    public void onMessage(String messageParams) throws ParseException, IOException {

        log.info("【websocket消息】收到客户端消息:"+messageParams);
//        System.out.println(messageParams);
        for (websocketController client :webSockets){
            client.session.getBasicRemote().sendText(messageParams);
        }
        int type = Integer.parseInt(messageParams.split("-")[0]);
        String message= messageParams.split("-")[1];

        Message message1=new Message();
        message1.setMessage(message);
        message1.setType(type);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat();
        String dateStr = simpleDateFormat.format(new Date());
        Date time =simpleDateFormat.parse(dateStr);
        message1.setTime(time);
        message1.setShopId(Long.parseLong(this.shopId));
        message1.setUserId(Long.parseLong(this.mobileId));
        System.out.println(message1);
        boolean save = messageService.save(message1);
        if(save)
            System.out.println("本次发送操作成功！！！");
    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(websocketController webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
