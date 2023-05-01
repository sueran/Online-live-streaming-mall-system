package ltd.newbee.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Shop {

    private Long id;


    private String shopAvatar;

    private Long shopOwner;

    private String shopName;

    private String shopDesc;

    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;
}
