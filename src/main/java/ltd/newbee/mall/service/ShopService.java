package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.Shop;

public interface ShopService extends IService<Shop> {
      Shop getShopInfoById(Long id);
}
