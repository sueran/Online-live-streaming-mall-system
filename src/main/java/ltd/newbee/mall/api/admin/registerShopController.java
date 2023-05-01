package ltd.newbee.mall.api.admin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.newbee.mall.api.admin.param.AdminLoginParam;
import ltd.newbee.mall.api.admin.param.Seller;
import ltd.newbee.mall.api.admin.param.ShopParams;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.AdminUserService;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.service.impl.adminPhoneServiceImpl;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@Api(tags = "商家注册以及店铺注册接口")
@RequestMapping("/manage-api/v1")
public class registerShopController {

    @Resource
    private adminPhoneServiceImpl adminPhoneService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ShopService shopService;

    @RequestMapping(value = "/validatePhone",method = RequestMethod.GET)
    @ApiOperation(value = "验证手机号码" ,notes = "验证手机号码")
    public Result validatePhone(@RequestParam(required = true) @ApiParam(value = "手机号码") String phone ){

        Integer validatePhone = adminPhoneService.validatePhone(phone);
        if(validatePhone>=1 ){
           return   ResultGenerator.genSuccessResult("账号已注册");
        }
        return  ResultGenerator.genSuccessResult("账号未注册");
    }
    @RequestMapping(value = "/createShopSeller",method = RequestMethod.POST)
    @ApiOperation(value="注册店铺卖家",notes = "注册店铺卖家")
    public Result createShopSeller(@RequestBody(required = true) @ApiParam(value = "卖家信息") Seller seller ){
        Long id = adminUserService.register(seller.getNickName(), seller.getPassword(), seller.getPhone());

        if (id!=null){
            HashMap<String,String> res=new HashMap<>();
            res.put("userId",id.toString());
            return ResultGenerator.genSuccessResult(res);

        }
        return ResultGenerator.genFailResult("唉，好像哪里出错了");
    }

    @RequestMapping(value = "/createShop",method = RequestMethod.POST)
    @ApiOperation(value = "创建店铺",notes = "创建店铺")
    public Result createShop(@RequestBody(required = true)ShopParams shopParams){

        Shop shop= new Shop();
        BeanUtil.copyProperties(shopParams,shop);
        boolean save = shopService.save(shop);
        if(save){
            return  ResultGenerator.genSuccessResult("创建成功");
        }
        return  ResultGenerator.genFailResult("唉，好像哪里出错了");
    }
}
