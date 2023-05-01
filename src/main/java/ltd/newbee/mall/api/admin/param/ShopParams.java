package ltd.newbee.mall.api.admin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class ShopParams {

    @ApiModelProperty("店铺归属ID")
    @NotEmpty(message = "店铺归属ID不能为空")
    private Long shopOwner;

    @ApiModelProperty("店铺头像OSS地址")
    @NotEmpty(message = "avatar不能为空")
    private  String shopAvatar;

    @ApiModelProperty("店铺名")
    @NotEmpty(message = "店铺名称不能为空")
    private String shopName;
    @ApiModelProperty("店铺描述")
    @NotEmpty(message = "店铺描述不能为空")
    private String shopDesc;
    @ApiModelProperty("创建时间")
    @NotEmpty(message = "创建时间不能为空")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
