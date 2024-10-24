package com.limemartini.service.impl;

import com.google.gson.Gson;
import com.limemartini.domain.dto.MultipartDto;
import com.limemartini.service.UploadService;
import com.limemartini.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    @Override
    public String uploadImg(MultipartDto file) {
        MultipartFile img = file.getImg();;
        String originalFilename = img.getOriginalFilename();
        //check file type
//        assert originalFilename != null;
//        if (!originalFilename.endsWith(".png")){
//            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
//        }
        assert originalFilename != null;
        String filePath = PathUtils.generateFilePath(originalFilename);

        return testOSS(img, filePath);
    }

    public String testOSS(MultipartFile img, String filePath) {
        //build a config object containing the region info
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// set resumable upload version

        UploadManager uploadManager = new UploadManager(cfg);
//        String accessKey = "your accessKey";
//        String secretKey = "your secretKey";
//        String bucket = "your bucket name";

        //if key == null，the file name will be the hash of the content
        String key;
        key = filePath;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);


            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //parse the success result
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return domain+"/"+key;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
