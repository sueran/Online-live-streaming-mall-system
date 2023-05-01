package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.shopTrendsMapper;
import ltd.newbee.mall.entity.ShopTrends;
import ltd.newbee.mall.service.shopTrendsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class shopTrendsServiceImpl  extends ServiceImpl<shopTrendsMapper, ShopTrends> implements shopTrendsService {


    @Resource
    private  shopTrendsMapper shopTrendsMapper;

    @Override
    public boolean insertTrends(ShopTrends shopTrends) {
        int insert = shopTrendsMapper.insert(shopTrends);
        if(insert==1){
            return  true;
        }
        return false;
    }

    @Override
    public boolean updateTrends(ShopTrends shopTrends) {
        int i = shopTrendsMapper.updateById(shopTrends);
        if(i==1){
            return  true;
        }
        return false;
    }

    @Override
    public List<ShopTrends> getShopTrendByShopId(Long shopId) {
        LambdaQueryWrapper<ShopTrends> shopTrendsLambdaQueryWrapper= new LambdaQueryWrapper<>();
        shopTrendsLambdaQueryWrapper.eq(ShopTrends::getShopId,shopId);
        shopTrendsLambdaQueryWrapper.orderByDesc(ShopTrends::getTrendsTime);
        List<ShopTrends> shopTrends = shopTrendsMapper.selectList(shopTrendsLambdaQueryWrapper);
        if(shopTrends.size()>0){
            return  shopTrends;
        }
        return null;
    }
}

