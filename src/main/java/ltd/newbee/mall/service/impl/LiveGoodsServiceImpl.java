package ltd.newbee.mall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.LiveGoodsMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.LiveGoods;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.LiveGoodsService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LiveGoodsServiceImpl extends ServiceImpl<LiveGoodsMapper, LiveGoods> implements LiveGoodsService {
   @Autowired
   private LiveGoodsMapper liveGoodsMapper;

   @Autowired
   private NewBeeMallGoodsService goodsService;

   @Autowired
   private NewBeeMallGoodsMapper goodsMapper;
    @Override
    public List<NewBeeMallGoods> getOnlineGoodsByLiveId(Long liveId) {
        LambdaQueryWrapper<LiveGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LiveGoods::getLiveId,liveId);
        List<LiveGoods> liveGoods = liveGoodsMapper.selectList(lambdaQueryWrapper);
        List<NewBeeMallGoods> res = new ArrayList<>();
        for (LiveGoods item: liveGoods){
            Long id = item.getGoodsId();
            NewBeeMallGoods one = goodsMapper.selectByPrimaryKey(id);
            res.add(one);
        }
        System.out.println(res);
        return res;
    }
}
