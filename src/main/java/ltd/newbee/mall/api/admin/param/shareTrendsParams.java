package ltd.newbee.mall.api.admin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class shareTrendsParams {
        private Long shopId;

        private  String shopTrends;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date trendsTime;

        private List<String> imgList;

}
