package ltd.newbee.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.newbee.mall.entity.shopTrendsPhoto;

import java.util.List;
import java.util.Objects;

public interface shopTrendsPhotoService extends IService<shopTrendsPhoto> {
    boolean insertTrendsPhoto(List<String> imgUrlLists,Long trendId);

    List<Object> getPhotoUrlByShopId(Long trendId);
}
