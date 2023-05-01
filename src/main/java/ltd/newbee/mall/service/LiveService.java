package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.Live;

public interface LiveService extends IService<Live> {

    Boolean startLive(Long id);

    Boolean closeLive(Long id);

}
