package cn.bukkit.sip.storage.http;

import cn.bukkit.sip.config.StorageConfig;
import cn.bukkit.sip.exception.UploadException;
import cn.bukkit.sip.storage.HttpStorage;
import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class QiniuStorage extends HttpStorage {
    String name = "七牛云";
    String apiId;
    String apiKey;
    String domain;
    String bucket;
    Configuration configuration;

    @Override
    public Object saveFile(String path, byte[] data) {
        UploadManager uploadManager = new UploadManager(this.configuration);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = Auth.create(this.getApiId(), this.getApiKey());
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet;
        log.debug("this.getApiId() = " + this.getApiId());
        log.debug("this.getApiKey() = " + this.getApiKey());
        try {
            Response response = uploadManager.put(data, path, upToken);
            String resJson = response.bodyString();
            putRet = JSONUtil.toBean(resJson, DefaultPutRet.class);
            log.debug("putRet.key = " + putRet.key);
            log.debug("putRet.hash = " + putRet.hash);
        } catch (Exception ex) {
            log.debug(ex.toString());
            throw new UploadException(ex.getMessage());
        }
        return putRet;
    }

    @Override
    public boolean isExist(String path) {
        Auth auth = Auth.create(this.getApiId(), this.getApiKey());
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            FileInfo fileInfo = bucketManager.stat(bucket, path);
            log.debug("file info debug: {}", JSONUtil.toJsonStr(fileInfo));
        } catch (QiniuException ex) {
            log.debug("file info debug: {}", ex.response.toString());
            log.debug("file status code: {}", ex.response.statusCode);
            if (ex.response.statusCode == 612) {
                return false;
            }
            throw new UploadException(ex.getMessage());
        }
        return true;
    }

    @Override
    public Object getFile(String path) {
        return this.getDomain() + "/" + path;
    }

    @Override
    public boolean deleteFile(String path) {
        Auth auth = Auth.create(this.getApiId(), this.getApiKey());
        BucketManager bucketManager = new BucketManager(auth, this.configuration);
        try {
            bucketManager.delete(bucket, path);
        } catch (QiniuException ex) {
            log.debug("ex.code() = " + ex.code());
            log.debug("ex.response.toString() = " + ex.response.toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean isOrigin() {
        return true;
    }

    @Override
    public boolean init(String name, StorageConfig config) {
        this.apiId = config.getAccessKey();
        this.apiKey = config.getSecretKey();
        this.domain = config.getDomain();
        this.bucket = config.getBucket();
        this.configuration = new Configuration(Region.autoRegion());
        return true;
    }

    @Override
    public String getGateway() {
        return this.getDomain();
    }
}
