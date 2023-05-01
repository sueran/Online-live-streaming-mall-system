package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.dao.LiveMapper;
import ltd.newbee.mall.entity.Live;
import ltd.newbee.mall.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LiveServiceImpl extends ServiceImpl<LiveMapper, Live> implements LiveService {

    @Autowired
    private LiveMapper liveMapper;

    @Override
    public Boolean startLive(Long id) {
        Live live =new Live();
        live.setId(id);
        live.setLiveState(1);
        Date date= new Date();
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = tempDate.format(new java.util.Date());
        live.setLiveStartTime(date);
        live.setLiveWs("ws://localhost:8888/live/"+id);
        int i = liveMapper.updateById(live);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean closeLive(Long id) {
        Live live =new Live();
        live.setId(id);
        live.setLiveState(0);
        Date date= new Date();
        live.setLiveEndTime(date);
        int i = liveMapper.updateById(live);
        if(i==1){
            return true;
        }
        return false;
    }
}
