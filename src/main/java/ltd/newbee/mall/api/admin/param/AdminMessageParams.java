package ltd.newbee.mall.api.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class AdminMessageParams {


    @NotNull(message = "不允许为空")
    @ApiModelProperty("userId")
    private Long userId;
    @NotNull(message = "不允许为空")
    @ApiModelProperty("shopId")
    private Long shopId;
}
