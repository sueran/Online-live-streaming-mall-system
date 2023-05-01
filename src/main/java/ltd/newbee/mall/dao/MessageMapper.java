package ltd.newbee.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.newbee.mall.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageMapper  extends BaseMapper<Message> {

    List<Message> getMessageList(Long id);


    List<Message> getAdminMessageList(Long id);

}
