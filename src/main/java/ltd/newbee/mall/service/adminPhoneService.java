package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.adminPhone;

public interface adminPhoneService extends IService<adminPhone> {

    Integer validatePhone(String phone);
}
