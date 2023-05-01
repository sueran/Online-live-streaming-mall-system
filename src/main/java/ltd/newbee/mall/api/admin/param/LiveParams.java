package ltd.newbee.mall.api.admin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LiveParams {

    private Long id;

    private Long  liveShopId;

    private String  liveUrl;

    private String livePassword;

    private  String liveWs;

    private int liveState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date liveEndTime;
}
