package ltd.newbee.mall.api.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import ltd.newbee.mall.api.admin.param.LiveParams;
import ltd.newbee.mall.api.admin.param.onlineGoodsParams;
import ltd.newbee.mall.entity.Live;
import ltd.newbee.mall.entity.LiveGoods;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.LiveGoodsService;
import ltd.newbee.mall.service.LiveService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.util.List;

import static jdk.nashorn.internal.runtime.Debug.id;

@RestController
@RequestMapping(value = "/manage-api/v1")
@Api(value = "商家直播相关接口",tags = "商家直播相关接口")
public class LiveController {
    @Resource
    private LiveService liveService;

    @Resource
    private ShopService shopService;


    @Resource
    private NewBeeMallGoodsService goodsService;

    @Resource
    private LiveGoodsService liveGoodsService;

    @RequestMapping(value = "/addLiveUrl",method = RequestMethod.POST)
    @ApiOperation(value = "将url与推流码保存到数据库",notes = " 将url与推流码保存到数据库")
    public Result addLiveUrl(@RequestBody LiveParams liveParams){
        Live live= new Live();
        BeanUtil.copyProperties(liveParams,live);
        Long id= IdWorker.getId();
        live.setId(id);
        boolean save = liveService.save(live);
        if(save){
            return ResultGenerator.genSuccessResult(id.toString());
        }
        return ResultGenerator.genFailResult("唉，哪里好像出错了");
    }

    @RequestMapping(value = "/getLiving",method = RequestMethod.GET)
    @ApiOperation(value = "获取直播url",notes = "获取直播url")
    public Result getLiveingById(@RequestParam String id){
        Live live = liveService.getById(id);
        if(ObjectUtils.isNotEmpty(live)){
            return ResultGenerator.genSuccessResult(live);
        }else{
            return ResultGenerator.genFailResult("唉，哪里好像出错了");
        }
    }

    @RequestMapping(value = "/getAllGoods",method = RequestMethod.GET)
    @ApiOperation(value = "获取该店铺的所有的商品",notes = "获取该店铺的所有的商品")
    public  Result getAllGoods(@RequestParam Long shopId){
        Shop shop = shopService.getById(shopId);
        if(ObjectUtils.isNotEmpty(shop)){
            Long owner = shop.getShopOwner();
            List<NewBeeMallGoods> allGoodsByOwnerId = goodsService.getAllGoodsByOwnerId(owner);
            return ResultGenerator.genSuccessResult(allGoodsByOwnerId);
        }

        return null;
    }


    @RequestMapping(value = "/onlineGoods",method = RequestMethod.POST)
    @ApiOperation(value = "上架商品",notes = "上架商品")
    public Result onlineGoods(@RequestBody onlineGoodsParams onlineGoodsParams){
        LiveGoods liveGoods = new LiveGoods();
        liveGoods.setLiveId(onlineGoodsParams.getLiveId());
        liveGoods.setGoodsId(onlineGoodsParams.getGoodsId());
        boolean save = liveGoodsService.save(liveGoods);

        NewBeeMallGoods goods=new NewBeeMallGoods();
        goods.setGoodsId(onlineGoodsParams.getGoodsId());
        String str= String.valueOf('1');
        goods.setTag(str);

        Integer integer = goodsService.updateTagById(goods);


        if(save && integer==1){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("唉，哪里好像出错了");
    }

    @RequestMapping(value = "/offOnlineGoods",method = RequestMethod.POST)
    @ApiOperation(value = "下架商品且不可以再上架了",notes = "下架商品且不可以再上架了")
    public  Result offOnlineGoods(@RequestBody onlineGoodsParams params){
        LiveGoods liveGoods = new LiveGoods();
        LambdaQueryWrapper<LiveGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LiveGoods::getLiveId,params.getLiveId());
        lambdaQueryWrapper.eq(LiveGoods::getGoodsId,params.getGoodsId());
        boolean remove = liveGoodsService.remove(lambdaQueryWrapper);
        if(remove){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("唉，哪里好像出错了");
    }

    @RequestMapping(value = "/getOnlineGoods",method = RequestMethod.GET)
    @ApiOperation(value = "获取上线的商品",notes = "获取上线的商品")
    public  Result getOnlineGoods (@RequestParam Long liveId){
        List<NewBeeMallGoods> onlineGoodsByLiveId = liveGoodsService.getOnlineGoodsByLiveId(liveId);
        return ResultGenerator.genSuccessResult(onlineGoodsByLiveId);
    }
    @RequestMapping(value = "/offEndOnlineGoods",method = RequestMethod.POST)
    @ApiOperation(value = "下架后可以重新上架",notes = "下架后可以重新上架")
    public Result offEndOfflineGoods (@RequestBody  onlineGoodsParams params){
        LiveGoods liveGoods = new LiveGoods();
        LambdaQueryWrapper<LiveGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LiveGoods::getLiveId,params.getLiveId());
        lambdaQueryWrapper.eq(LiveGoods::getGoodsId,params.getGoodsId());
        boolean remove = liveGoodsService.remove(lambdaQueryWrapper);
        NewBeeMallGoods goods = new NewBeeMallGoods();
        goods.setTag("0");
        goods.setGoodsId(params.getGoodsId());
        Integer r = goodsService.updateTagById(goods);

        if(r==1 && remove){
            return  ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("唉，哪里好像出错了");
        }
    }



}
