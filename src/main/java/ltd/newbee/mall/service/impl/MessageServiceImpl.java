package ltd.newbee.mall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.MessageMapper;
import ltd.newbee.mall.entity.Message;
import ltd.newbee.mall.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private  MessageMapper messageMapper;

    @Override
    public List getMessageList(Long userId, Long shopId) {
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper= new LambdaQueryWrapper<>();
        messageLambdaQueryWrapper.eq(Message::getUserId,userId);
        messageLambdaQueryWrapper.eq(Message::getShopId,shopId);
//        messageLambdaQueryWrapper.groupBy(Message::getType);
        messageLambdaQueryWrapper.orderByAsc(Message::getTime);
        List<Message> messageList = messageMapper.selectList(messageLambdaQueryWrapper);
        System.out.println(messageList);
        return messageList;
    }
}
