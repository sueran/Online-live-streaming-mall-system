package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.LiveGoods;
import ltd.newbee.mall.entity.NewBeeMallGoods;

import java.util.List;

public interface LiveGoodsService  extends IService<LiveGoods> {
    List<NewBeeMallGoods> getOnlineGoodsByLiveId(Long liveId);

}
