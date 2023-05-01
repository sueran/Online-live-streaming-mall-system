package ltd.newbee.mall.api.mall;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.api.admin.param.MessageParams;
import ltd.newbee.mall.config.annotation.TokenToMallUser;
import ltd.newbee.mall.dao.MessageMapper;
import ltd.newbee.mall.dao.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.MallUserToken;
import ltd.newbee.mall.entity.Message;
import ltd.newbee.mall.entity.Shop;
import ltd.newbee.mall.service.MessageService;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
@Api(value = "",tags = "消息相关接口")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ShopService shopService;
    @Autowired
    private NewBeeMallUserTokenMapper newBeeMallUserTokenMapper;

    @Autowired
    private MessageMapper messageMapper;

    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ApiOperation(value = "获取信息",notes = "获取信息")
    public Result getMessage(@RequestBody @Valid MessageParams messageParams ){
        MallUserToken mallUserToken = newBeeMallUserTokenMapper.selectByToken(messageParams.getUserToken());
        if(ObjectUtils.isEmpty(mallUserToken)){
            return ResultGenerator.genFailResult("唉，好像哪里出错了");
        }
        List<Object> messageList = messageService.getMessageList(mallUserToken.getUserId(),messageParams.getShopId());
        return ResultGenerator.genSuccessResult(messageList);
    }


    @RequestMapping(value = "/getMessageList",method = RequestMethod.GET)
    @ApiOperation(value ="获取联系人列表",notes = "获取联系人列表")
    public  Result getMessageList(@TokenToMallUser MallUser mallUser){
        List<Message> messageList = messageMapper.getMessageList(mallUser.getUserId());
        List<Object> resList= new ArrayList<>();
        for (Message item:messageList){
            Map<String,Object> res = new HashMap<>();
            Shop shop = shopService.getById(item.getShopId());
            res.put("shopId",item.getShopId().toString());
            res.put("shopName",shop.getShopName());
            res.put("shopAvatar",shop.getShopAvatar());
            res.put("message",item.getMessage());
            res.put("time",item.getTime());
            resList.add(res);
        }
        return  ResultGenerator.genSuccessResult(resList);
    }
}
