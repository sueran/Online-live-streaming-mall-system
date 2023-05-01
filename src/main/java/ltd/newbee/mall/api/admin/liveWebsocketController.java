package ltd.newbee.mall.api.admin;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import ltd.newbee.mall.entity.Live;
import ltd.newbee.mall.entity.Message;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.LiveService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/live/{data}")
public class liveWebsocketController {

    private Session session;

    private  static LiveService liveService;

    private static NewBeeMallGoodsService goodsService;

    private  static ShopService shopService;

    private  Long liveId;

    @Autowired
    public void setLiveService(LiveService liveService){
        liveWebsocketController.liveService=liveService;
    }
    @Autowired
    public  void setGoodsService(NewBeeMallGoodsService goodsService){
        liveWebsocketController.goodsService=goodsService;
    }

    @Autowired
    public  void  setShopService(ShopService shopService){
        liveWebsocketController.shopService=shopService;
    }

    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<liveWebsocketController> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String,Session>();

    private  static Map<String,Session> firstSession = new HashMap<>();

    @OnOpen
    public void open(Session session , @PathParam(value = "data") String params){
        try {
            System.out.println(session);
            this.session = session;
            if(webSockets.size()==0){
                firstSession.put("first",session);
            }
            webSockets.add(this);
            this.liveId= Long.valueOf(params);
            sessionPool.put(params, this.session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
            log.info(firstSession.toString());
            Live living = liveService.getById(Long.parseLong(params));
            if(living.getLiveState()==0){
                Boolean aBoolean = liveService.startLive(Long.parseLong(params));
                System.out.println("开启直播:"+aBoolean);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            System.out.println(session);
            System.out.println("关闭sesion------------------------"+this.session);

            if(firstSession.get("first").equals(this.session)){
                System.out.println("--------------------"+"商家关闭了session");
                Live live =new Live();
                live.setId(this.liveId);
                live.setLiveState(0);
                Date date= new Date();
                live.setLiveEndTime(date);
                liveService.updateById(live);
                //
                Live live1 = liveService.getById(this.liveId);
                Long shopId = live1.getLiveShopId();
                Long shopOwner = shopService.getById(shopId).getShopOwner();
                Integer tag = goodsService.initUpdateTag(shopOwner);
                System.out.println("本次更新的tag:"+tag);
            }
//            System.out.println(this.liveId);
//            Live live =new Live();
//            live.setId(this.liveId);
//            live.setLiveState(0);
//            Date date= new Date();
//            live.setLiveEndTime(date);
//            liveService.updateById(live);
            //

//            System.out.println(this);
            webSockets.remove(this);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @OnMessage
    public void onMessage(String messageParams) throws IOException {
        log.info("【websocket消息】收到客户端消息:"+messageParams);
        for (liveWebsocketController client :webSockets){
            client.session.getBasicRemote().sendText(messageParams);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }



}
