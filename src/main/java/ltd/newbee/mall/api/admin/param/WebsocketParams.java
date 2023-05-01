package ltd.newbee.mall.api.admin.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class WebsocketParams {


    @NotEmpty(message = "信息内容不允许为空")
    @ApiModelProperty("信息")
    private  String message;

    @NotEmpty(message = "发送类型不允许为空")
    @ApiModelProperty("发送类型")
    private int type;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotEmpty(message = "发送时间不允许为空")
    @ApiModelProperty("发送时间")
    private Date time;
}
