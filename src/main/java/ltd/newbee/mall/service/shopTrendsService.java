package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.ShopTrends;

import java.util.List;

public interface shopTrendsService extends IService<ShopTrends> {
    boolean insertTrends(ShopTrends shopTrends);

    boolean updateTrends(ShopTrends shopTrends);

    List<ShopTrends> getShopTrendByShopId(Long shopId);
}
