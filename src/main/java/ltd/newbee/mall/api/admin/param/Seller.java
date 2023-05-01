package ltd.newbee.mall.api.admin.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Seller {

    @ApiModelProperty("昵称")
    @NotEmpty(message = "昵称不能为空")
    private  String nickName;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("登录名")
    @NotEmpty(message = "登录名不能为空")
    private String phone;
}
