package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Live {
    @TableId(type= IdType.INPUT)
    private Long id;

    private Long liveShopId;

    private String liveUrl;

    private String livePassword;

    private String liveWs;


    private String liveDesc;

    private int liveState;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveEndTime;


}
