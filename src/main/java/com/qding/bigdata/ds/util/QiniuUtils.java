package com.qding.bigdata.ds.util;

/**
 * #七牛空间名
 bucketName=qding-storage
 #七牛云的空间对应的下载域名
 domain = img1.qdingnet.com
 #密钥
 accessKey=QlVOYFxHiqoYWqP8fqd2BQ7a8KT5Y7RWC7CuUSOK
 #密钥
 secretKey=jY-rfeAkVeVeT3IWgZN9YxKSzZQ-R6ZXnhsbPdU7
 */

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: QiniuUtils
 * @Description: 七牛操作工具类
 *
 */
public class QiniuUtils {
    //Access key
    private static final String ACCESS_KEY = "QlVOYFxHiqoYWqP8fqd2BQ7a8KT5Y7RWC7CuUSOK";
    //Secret key
    private static final String SECRET_KEY = "jY-rfeAkVeVeT3IWgZN9YxKSzZQ-R6ZXnhsbPdU7";
    //bucket name
    private static final String BUCKET_NAME = "qding-storage";
    //CDN加速域名
    private static final String CDN_DOMAIN_NAME = "http://img1.qdingnet.com/";
    //上传根目录
    private static final String IMG_UPLOAD_PATH = "bigData/";
    //构造一个带指定Zone对象的配置类
    //华东：Zone.zone0()、华北：Zone.zone1()、华南：Zone.zone2()
    private static final Configuration cfg = new Configuration(Zone.zone0());
    private static final UploadManager uploadManager = new UploadManager(cfg);
    /**
     * 上传图片到七牛云
     * 并返回图片URL
     * @param file
     * @return
     */
    public static String uploadImg2QiNiu(MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss.SSS");
        String filename = file.getOriginalFilename();

        //文件名称
        String key = IMG_UPLOAD_PATH +filename+"_"+System.currentTimeMillis();
        try {
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(BUCKET_NAME);
            Response response = uploadManager.put(file.getInputStream(), key, upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return CDN_DOMAIN_NAME + putRet.key;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            //System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException qe) {
                e.printStackTrace();
            }
            return "上传图片异常!";
        }
    }

}
