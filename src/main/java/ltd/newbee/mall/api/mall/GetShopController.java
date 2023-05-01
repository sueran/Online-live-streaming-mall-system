package ltd.newbee.mall.api.mall;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.entity.ShopTrends;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.service.shopTrendsPhotoService;
import ltd.newbee.mall.service.shopTrendsService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "手机端",tags = "手机端获取店铺相关接口信息")
@RequestMapping( "/api/v1")
public class GetShopController {

    @Autowired
    private ShopService shopService;

    @Resource
    private ltd.newbee.mall.service.shopTrendsService shopTrendsService;

    @Resource
    private ltd.newbee.mall.service.shopTrendsPhotoService shopTrendsPhotoService;
    @RequestMapping(value = "/getShopInfoById",method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取店铺信息",notes = "根据id获取店铺信息")
    public Result getShopInfoById(@RequestParam String shopId){
        System.out.println(shopId);
        Shop shop = shopService.getById(shopId);
        if(ObjectUtils.isNotEmpty(shop)){
            Map<String,Object> res=new HashMap<>();
            res.put("shopId",shop.getId().toString());
            res.put("shopAvatar",shop.getShopAvatar());
            res.put("shopName",shop.getShopName());
            res.put("shopDesc",shop.getShopDesc());
            return  ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult("唉，好像哪里出错了");
    }

    @RequestMapping(value = "/getShopTrendsByShopId",method = RequestMethod.GET)
    @ApiOperation(value = "根据店铺id获取店铺动态",notes = "根据店铺id获取店铺动态")
    public Result getShopTrendsByShopId(@RequestParam Long shopId){
        List<Object> res= new ArrayList<>();
        List<ShopTrends> shopTrendByShopId = shopTrendsService.getShopTrendByShopId(shopId);

        for (ShopTrends item:shopTrendByShopId){
            Map<String,Object> resItem= new HashMap<>();
//            System.out.println(iterator.next());
            List<Object> urlList = shopTrendsPhotoService.getPhotoUrlByShopId(item.getId());

            resItem.put("trendId",item.getId());
            resItem.put("trend",item.getShopTrends());
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = simpleDateFormat.format(item.getTrendsTime());
            resItem.put("createTime",s);
            resItem.put("shopId",item.getShopId());
            resItem.put("trendPhotos",urlList);
            res.add(resItem);
        }
        if (res.size()>0){
            return  ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult("Null Data");
    }


}
