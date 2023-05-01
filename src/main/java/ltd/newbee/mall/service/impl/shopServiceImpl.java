package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.ShopMapper;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.ShopService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class shopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Override
    public Shop getShopInfoById(Long id) {

        LambdaQueryWrapper<Shop> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shop::getShopOwner,id);
        Shop shop = shopMapper.selectOne(lambdaQueryWrapper);
        if(ObjectUtils.isNotEmpty(shop))
            return shop;
        return null;
    }
}
