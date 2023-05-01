package ltd.newbee.mall.api.admin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.newbee.mall.api.admin.param.AdminMessageParams;
import ltd.newbee.mall.api.admin.param.MessageParams;
import ltd.newbee.mall.config.annotation.TokenToAdminUser;
import ltd.newbee.mall.dao.MessageMapper;
import ltd.newbee.mall.entity.*;
import ltd.newbee.mall.service.MessageService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.service.ShopService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "PC端获取信息接口",tags = "pc端信息接口")
@RequestMapping("/manage-api/v1")
public class AdminMessageController {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private NewBeeMallUserService newBeeMallUserService;

    @Autowired
    private ShopService shopService;


    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getConcatMessage",method = RequestMethod.GET)
    @ApiOperation(value = "获取信息联系人",notes = "获取信息联系人")
    public Result getConcatMessage(@TokenToAdminUser AdminUserToken adminUserToken){
//        System.out.println("38"+adminUser.getAdminUserId());
        Shop shop = shopService.getShopInfoById(adminUserToken.getAdminUserId());
        System.out.println(shop);
        List<Message> adminMessageList = messageMapper.getAdminMessageList(shop.getId());
        List<Object> resList= new ArrayList<>();
        for (Message item:adminMessageList){
            Map<String,Object> res = new HashMap<>();
            MallUser info = newBeeMallUserService.getUserInfoByUserId(item.getUserId());
            res.put("userId",item.getUserId().toString());
            res.put("userName",info.getNickName());
            res.put("shopId",item.getShopId().toString());
            res.put("message",item.getMessage());
            res.put("time",item.getTime());
            resList.add(res);
        }
        return  ResultGenerator.genSuccessResult(resList);

    }


    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ApiOperation(value = "获取信息",notes = "获取信息")
    public Result getMessage(@RequestBody @Valid AdminMessageParams messageParams ){
        List<Object> messageList = messageService.getMessageList(messageParams.getUserId(),messageParams.getShopId());
        return ResultGenerator.genSuccessResult(messageList);
    }

}
