package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.AdminPhoneMapper;
import ltd.newbee.mall.entity.adminPhone;
import ltd.newbee.mall.service.AdminUserService;
import ltd.newbee.mall.service.adminPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class adminPhoneServiceImpl  extends ServiceImpl<AdminPhoneMapper, adminPhone> implements adminPhoneService {

  @Resource
  private   AdminPhoneMapper adminPhoneMapper;
  @Resource
  private AdminUserService adminUserService;

    @Override
    public Integer validatePhone(String phone) {

        Integer phone1 = adminUserService.validatePhone(phone);
        return phone1;
    }
}
