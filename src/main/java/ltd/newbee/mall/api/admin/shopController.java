package ltd.newbee.mall.api.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.api.admin.param.AddTrendsImgParams;
import ltd.newbee.mall.api.admin.param.editShopInfoParams;
import ltd.newbee.mall.api.admin.param.shareTrendsParams;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.entity.ShopTrends;
import ltd.newbee.mall.entity.shopTrendsPhoto;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.service.shopTrendsService;
import ltd.newbee.mall.service.shopTrendsPhotoService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Api(value = "v1",tags = "店铺相关接口信息")
@RequestMapping(value = "/manage-api/v1")
public class shopController {

   @Resource
   private ShopService shopService;

   @Resource
   private shopTrendsService shopTrendsSerivce;

   @Resource
   private shopTrendsPhotoService shopTrendsPhotoService;

   public  static  final Logger logger= LoggerFactory.getLogger(shopController.class);

    @RequestMapping(value = "/shopDetail",method = RequestMethod.GET)
    @ApiOperation(value = "获取店铺信息",notes = "根据token可获取店铺信息")
    public Result getShopDetail(@TokenToAdminUser AdminUserToken adminUserToken){
     System.out.println("adminUserToken-----"+adminUserToken);
        Long tokenToId = adminUserToken.getAdminUserId();
        Shop info = shopService.getShopInfoById(tokenToId);
        Map<String,Object> res=new HashMap<>();
        res.put("shopId",info.getId().toString());
        res.put("shopAvatar",info.getShopAvatar());
        res.put("shopName",info.getShopName());
        res.put("shopDesc",info.getShopDesc());
        return ResultGenerator.genSuccessResult(res);
    }


    @RequestMapping(value = "/editShopInfo",method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺信息",notes = "修改店铺信息")
    public Result editShopInfo(@RequestBody editShopInfoParams editShopInfoParams ){
        Shop shop= new Shop();
        BeanUtil.copyProperties(editShopInfoParams,shop);
        LambdaQueryWrapper<Shop> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shop::getId,editShopInfoParams.getId());
        boolean update = shopService.update(shop, lambdaQueryWrapper);
        if(update){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("唉，好像哪里出错了！");
    }

    @RequestMapping(value = "/deliveryShopTrends",method = RequestMethod.POST)
    @ApiOperation(value ="发表动态" ,notes = "发表动态接口")
    public  Result shareTrends(@RequestBody shareTrendsParams shareTrends){

        logger.info("shareTrends :{}",shareTrends);
        Long id = IdWorker.getId();

        ShopTrends shopTrends = new ShopTrends();
        shopTrends.setShopId(shareTrends.getShopId());
        shopTrends.setTrendsTime(shareTrends.getTrendsTime());
        shopTrends.setId(id);

        shopTrends.setShopTrends(shareTrends.getShopTrends());

        boolean b = shopTrendsSerivce.insertTrends(shopTrends);


        List<String> imgList = shareTrends.getImgList();
        boolean b1 = shopTrendsPhotoService.insertTrendsPhoto(imgList, id);

        if(b && b1){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genErrorResult(-200,"ERROR");
    }


    @RequestMapping(value = "/getShopTrendsByShopId",method = RequestMethod.GET)
    @ApiOperation(value = "获取动态",notes = "获取动态")
    public  Result getShopTrendsByShopId(@RequestParam  String shopId){
        List<Object> res= new ArrayList<>();
        List<ShopTrends> shopTrendByShopId = shopTrendsSerivce.getShopTrendByShopId(Long.parseLong(shopId));
        if(shopTrendByShopId==null){
            return ResultGenerator.genFailResult("暂无动态");
        }
        for (ShopTrends item:shopTrendByShopId){
            Map<String,Object> resItem= new HashMap<>();
//            System.out.println(iterator.next());
            List<Object> urlList = shopTrendsPhotoService.getPhotoUrlByShopId(item.getId());

            resItem.put("trendId",item.getId().toString());
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

    @RequestMapping(value = "/deleteTrendsById",method =RequestMethod.GET)
    @ApiOperation(value = "根据id删除动态",notes = "根据id删除动态")
    public Result deleteTrendsById(@RequestParam String id){
        boolean b = shopTrendsSerivce.removeById(Long.parseLong(id));
        if(b){
           return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("唉，好像哪里出错了");
    }

    @RequestMapping(value = "/deleteTrendPhotoByPhotoId",method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除图片",notes = "根据id删除图片")
    public  Result deleteTrendPhoto(@RequestParam String photoId){

        boolean b = shopTrendsPhotoService.removeById(photoId);
        if(b){
            return ResultGenerator.genSuccessResult(b);
        }else{
            return ResultGenerator.genFailResult("唉，好像哪里出错了！");
        }
    }

    @RequestMapping(value ="/addTrendsImg",method = RequestMethod.POST)
    @ApiOperation(value = "新增一个图片",notes = "新增一个图片")
    public  Result addTrendsImg(@RequestBody AddTrendsImgParams addTrendsImgParams){
        shopTrendsPhoto shopTrendsPhoto = new shopTrendsPhoto();
        shopTrendsPhoto.setTrendId(Long.parseLong(addTrendsImgParams.getTrendId()));
        shopTrendsPhoto.setTrendPhoto(addTrendsImgParams.getUrl());
        boolean save = shopTrendsPhotoService.save(shopTrendsPhoto);
        if(save){
            return  ResultGenerator.genSuccessResult();
        }
        return  ResultGenerator.genFailResult("唉，好像哪里出错了");
    }
    @RequestMapping(value = "/updateTrends",method = RequestMethod.POST)
    @ApiOperation(value = "修改动态",notes = "修改动态")
    public Result updateTrends(@RequestBody shareTrendsParams shareTrendsParams){
        ShopTrends shopTrends= new ShopTrends();
        shopTrends.setId(shareTrendsParams.getShopId());
        shopTrends.setShopTrends(shareTrendsParams.getShopTrends());
        boolean b = shopTrendsSerivce.updateTrends(shopTrends);
        if(b){
            return  ResultGenerator.genSuccessResult();
        }

        return ResultGenerator.genFailResult("唉，好像哪里出错了");
    }
}
