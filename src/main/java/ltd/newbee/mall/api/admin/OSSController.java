package ltd.newbee.mall.api.admin;

import com.aliyun.oss.OSS;
import io.swagger.annotations.Api;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


@RestController
@Api(value = "",tags = "阿里云文件上传模块")
@RequestMapping("/manage-api/v1")
public class OSSController {

    @Resource
    private OSS ossClient;

    @PostMapping("/uploadImg")
    public Result uploadImg(MultipartFile file) throws IOException {
        System.out.println(file);
        //获取文件后缀名
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String png = originalFilename.substring(i);

        //存储空间的名字
        String bucketName = "mall-device1234";
        //生成新的文件名 brand/：在bucketName里面创建的分组
        String fileName = "itsource/" + UUID.randomUUID().toString().substring(0, 4) + png;
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（fileName）。
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        //文件访问地址。
        String url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com" + "/" + fileName;
        System.out.println("上传成功返回的：" + url);
        // 关闭OSSClient。
//        ossClient.shutdown();

        HashMap<String ,String> res= new HashMap<>();
        res.put("url",url);
        return ResultGenerator.genSuccessResult(res);
    }

}
