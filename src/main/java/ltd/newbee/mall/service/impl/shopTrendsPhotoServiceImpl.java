package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.shopTrendsPhotoMapper;
import ltd.newbee.mall.entity.shopTrendsPhoto;
import ltd.newbee.mall.service.shopTrendsPhotoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class shopTrendsPhotoServiceImpl extends ServiceImpl<shopTrendsPhotoMapper, shopTrendsPhoto> implements shopTrendsPhotoService {

    @Resource
    private  shopTrendsPhotoMapper shopTrendsPhotoMapper;
    @Override
    public boolean insertTrendsPhoto(List<String> imgUrlLists,Long trendId){
        Boolean flag = true ;
        for (String url:imgUrlLists){
            shopTrendsPhoto shopTrendsPhoto = new shopTrendsPhoto();
            shopTrendsPhoto.setTrendPhoto(url);
            shopTrendsPhoto.setTrendId(trendId);
            int insert = shopTrendsPhotoMapper.insert(shopTrendsPhoto);
            if(insert!=1)
                flag=false;
        }
        return flag;
    }

    @Override
    public List<Object> getPhotoUrlByShopId(Long trendId) {
         List<Object> urlList=new ArrayList<>();
        LambdaQueryWrapper<shopTrendsPhoto> shopTrendsPhotoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shopTrendsPhotoLambdaQueryWrapper.eq(shopTrendsPhoto::getTrendId,trendId);
        List<shopTrendsPhoto> shopTrendsPhotos = shopTrendsPhotoMapper.selectList(shopTrendsPhotoLambdaQueryWrapper);
        if (shopTrendsPhotos.size()<=0){
            return  null;
        }else{
            for (shopTrendsPhoto item :shopTrendsPhotos){
                String url = item.getTrendPhoto();
                Map<String,String> urlItem= new HashMap<>();
                urlItem.put("urlId",item.getId().toString());
                urlItem.put("url",url);
                urlList.add(urlItem);
            }
            return  urlList;
        }


    }
}
