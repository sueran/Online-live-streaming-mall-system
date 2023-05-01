package ltd.newbee.mall.api.admin.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MessageParams {

    @NotNull(message = "不允许为空")
    @ApiModelProperty("userToken")
    private  String userToken;

    @NotNull(message = "不允许为空")
    @ApiModelProperty("shopId")
    private  Long shopId;


}
