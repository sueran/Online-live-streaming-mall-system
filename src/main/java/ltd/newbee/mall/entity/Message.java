package ltd.newbee.mall.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Message {

    private Long id;

    private Long userId;

    private Long shopId;

    private int type;


    private String  message;

    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date time;

}
