package ltd.newbee.mall.api.mall;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.entity.Live;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.LiveGoodsService;
import ltd.newbee.mall.service.LiveService;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "手机端",tags = "手机端直播相关接口")
@RequestMapping( "/api/v1")
public class mobileLiveController {


    @Autowired
    private LiveService liveService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private LiveGoodsService liveGoodsService;

    @RequestMapping(value = "/getAllLiving",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有正在直播的信息",notes = "获取所有正在直播的信息")
    public Result getAllLiving(){
        LambdaQueryWrapper<Live> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Live::getLiveState,1);
        List<Live> liveList = liveService.list(lambdaQueryWrapper);
        List<Object> res=new ArrayList<>();
        for (Live item : liveList){
            Map<String,Object> one= new HashMap<>();
            one.put("id",item.getId().toString());
            Long shopId = item.getLiveShopId();
            Shop shop = shopService.getById(shopId);
            one.put("shopInfo",shop);
            one.put("liveUrl",item.getLiveUrl());
            one.put("livePassword",item.getLivePassword());
            one.put("liveDesc",item.getLiveDesc());
            one.put("liveWs",item.getLiveWs());
            res.add(one);
        }
        return ResultGenerator.genSuccessResult(res);
    }

    @RequestMapping(value = "/getLivingById",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有正在直播的信息",notes = "获取所有正在直播的信息")
    public Result getLivingById(@RequestParam Long id){
        LambdaQueryWrapper<Live> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Live::getId,id);
        List<Live> liveList = liveService.list(lambdaQueryWrapper);
        List<Object> res=new ArrayList<>();
        for (Live item : liveList){
            Map<String,Object> one= new HashMap<>();
            one.put("id",item.getId().toString());
            Long shopId = item.getLiveShopId();
            Shop shop = shopService.getById(shopId);
            one.put("shopInfo",shop);
            one.put("liveUrl",item.getLiveUrl());
            one.put("livePassword",item.getLivePassword());
            one.put("liveDesc",item.getLiveDesc());
            one.put("liveWs",item.getLiveWs());
            one.put("liveStatus",item.getLiveState());
            res.add(one);
        }
        return ResultGenerator.genSuccessResult(res);
    }

    @RequestMapping(value = "getOnlineGoods",method = RequestMethod.GET)
    @ApiOperation(value = "手机端获取所有上架的商品",notes = "手机端获取所有上架的商品")
    public Result  getMobileOnlineGoods(@RequestParam Long liveId){
        List<NewBeeMallGoods> onlineGoodsByLiveId = liveGoodsService.getOnlineGoodsByLiveId(liveId);
        return ResultGenerator.genSuccessResult(onlineGoodsByLiveId);
    }

}
