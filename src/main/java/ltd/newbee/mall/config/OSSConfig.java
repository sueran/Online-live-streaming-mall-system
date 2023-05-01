package ltd.newbee.mall.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class OSSConfig {
    @Bean
    @Scope("prototype")
    public  OSS ossClient(){

//        aliyun.oss.file.endpoint=oss-cn-beijing.aliyuncs.com
//aliyun.oss.file.keyid=LTAI5tCEBugCAzffPa64Fv8X
//aliyun.oss.file.keysecret=z6x63nZJc8c6fUpWKCL9hklDE5QLXG
//#bucket??????????????java????
//aliyun.oss.file.bucketname=sususu123
        // Endpoint以成都为例。
        //创建对象存储的域名
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        //验证码获取的accessKeyId
        String accessKeyId = "LTAI5tCEBugCAzffPa64Fv8X";
        //验证码获取的accessKeySecret
        String accessKeySecret = "z6x63nZJc8c6fUpWKCL9hklDE5QLXG";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }
}
