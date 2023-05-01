package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
   List getMessageList(Long userId , Long shopId);

}
