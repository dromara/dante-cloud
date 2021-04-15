package cn.herodotus.eurynome.integration.content.service.aliyun;

import cn.herodotus.eurynome.integration.content.properties.AliyunOssProperties;
import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 阿里云对象存储服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 12:37
 */
@Slf4j
@Service
public class AliyunOssService {

    /**
     * OSS文件目录分包
     */
    private static final String OSS_DIR_FILE = "File";
    private static final String OSS_DIR_VIDEO = "Video";
    private static final String OSS_DIR_VOICE = "Voice";
    private static final String OSS_DIR_IMAGE = "Image";
    private static final String OSS_DIR_APP = "App";
    private static final String OSS_DIR_DOC = "Doc";

    private static final Map<String, String> FILE_TYPE_INDEX = new HashMap<>();

    public static final int METHOD_LONG = 20;

    @Autowired
    private AliyunProperties aliyunProperties;
    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    private String bucketName;

    @PostConstruct
    public void init() {
        this.setFileTypeIndex(aliyunOssProperties.getPictureTypes(), OSS_DIR_IMAGE);
        this.setFileTypeIndex(aliyunOssProperties.getVideoTypes(), OSS_DIR_VIDEO);
        this.setFileTypeIndex(aliyunOssProperties.getVoiceTypes(), OSS_DIR_VOICE);
        this.setFileTypeIndex(aliyunOssProperties.getAppTypes(), OSS_DIR_APP);
        this.setFileTypeIndex(aliyunOssProperties.getDocTypes(), OSS_DIR_DOC);
        this.bucketName = aliyunOssProperties.getBucketName();
    }

    private void setFileTypeIndex(String[] types, String value) {
        if (ArrayUtils.isNotEmpty(types)) {
            Arrays.stream(types).forEach(type -> FILE_TYPE_INDEX.put(type, value));
        }
    }

    private OSS getOssClient() {
        return new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
    }

    public String getFileClassify(String fileName) {
        String suffix = FileUtil.getSuffix(fileName);
        if (!StringUtils.startsWith(suffix, ".")) {
            suffix = "." + suffix;
        }

        String classify = FILE_TYPE_INDEX.get(suffix);
        if (StringUtils.isNotEmpty(classify)) {
            return classify;
        } else {
            return OSS_DIR_FILE;
        }
    }

    /**
     * 根据属性配置，创建存储空间
     */
    public void createBucket() {
        this.createBucket(this.bucketName);
    }

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间名称
     */
    public void createBucket(String bucketName) {
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
        //创建OSSClient实例
        OSS ossClient = getOssClient();
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 上传对象
     *
     * @param objectName  待上传名称
     * @param inputStream 上传对象流
     * @return 对象实际存储地址url
     */
    public String uploadObject(String objectName, InputStream inputStream) {
        return this.uploadObject(this.bucketName, objectName, inputStream);
    }

    /**
     * 上传对象
     *
     * @param bucketName  存储空间名称
     * @param objectName  待上传对象名称
     * @param inputStream 上传对象流
     * @return 对象实际存储地址url
     */
    public String uploadObject(String bucketName, String objectName, InputStream inputStream) {
        //创建OSSClient实例
        OSS ossClient = this.getOssClient();
        ossClient.putObject(bucketName, objectName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Upload object successful！");
        return aliyunOssProperties.getBaseUrl() + objectName;
    }

    /**
     * 上传文件
     *
     * @param fileName    待上传文件名称
     * @param inputStream 上传文件流
     * @return 对象实际存储地址url
     */
    public String uploadFile(String fileName, InputStream inputStream) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return this.uploadFile(fileName, inputStream, calendar);
    }

    /**
     * 上传文件
     *
     * @param fileName    待上传文件名称
     * @param inputStream 上传文件流
     * @param calendar    有效期时间
     * @return 对象实际存储地址url
     */
    public String uploadFile(String fileName, InputStream inputStream, Calendar calendar) {
        return this.uploadFile(this.bucketName, fileName, inputStream, calendar);
    }


    /**
     * 上传文件
     *
     * @param bucketName  存储空间名称
     * @param fileName    待上传文件名称
     * @param inputStream 上传文件流
     * @param calendar    有效期时间
     * @return 对象实际存储地址url
     */
    public String uploadFile(String bucketName, String fileName, InputStream inputStream, Calendar calendar) {
        //创建OSSClient实例
        OSS ossClient = this.getOssClient();
        //上传
        ossClient.putObject(bucketName, fileName, inputStream);
        //获取上传之后生成的url  带上有效期
        String url = ossClient.generatePresignedUrl(bucketName, fileName, calendar.getTime()).toString();
        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Upload file successful！");
        return url;
    }

    /**
     * 下载文件
     *
     * @param objectName      对象名称
     * @param destinationfile 最终写入的文件
     * @return 文件路径
     */
    public String downloadFile(String objectName, File destinationfile) {
        return this.downloadFile(this.bucketName, objectName, destinationfile);
    }

    /**
     * 下载文件
     *
     * @param bucketName      存储空间名称
     * @param objectName      对象名称
     * @param destinationfile 最终写入的文件
     * @return 文件路径
     */
    public String downloadFile(String bucketName, String objectName, File destinationfile) {
        //创建OSSClient实例
        OSS ossClient = this.getOssClient();
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (ObjectUtils.isNotEmpty(content)) {
            try {
                IOUtils.copy(content, new FileOutputStream(destinationfile));
                content.close();
            } catch (IOException e) {
                log.error("[Eurynome] |- Download file catch IO error！");
            }
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Download file successfully！");
        return destinationfile.getPath();
    }

    private String renameFile(String fileName, String directory) {

        if (fileName.length() > METHOD_LONG) {
            fileName = fileName.substring(fileName.length() - METHOD_LONG);
        }
        String newName = UUID.randomUUID().toString().replace("-", "");
        newName = directory + "/" + newName + "_" + fileName;
        return newName;
    }

//    private String getFilePath(MultipartFile multipartFile) {
//        if (ObjectUtils.isNotEmpty(multipartFile) && StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
//            try {
//                String fileName = this.renameFile(multipartFile.getOriginalFilename(), "");
//                return this.uploadFile(fileName, multipartFile.getInputStream());
//            } catch (IOException e) {
//                log.error("[Eurynome] |- UploadFiles get multipartFile error！");
//                return null;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param files MultipartFile
//     * @return 文件Map
//     */
//    public Map<String, Object> uploadFiles(MultipartFile[] files) {
//        Map<String, Object> result = new HashMap<>();
//
//        if (ArrayUtils.isNotEmpty(files)) {
//            List<String> filePaths = new ArrayList<>();
//            Arrays.stream(files).forEach((file) -> {
//                String filePath = this.getFilePath(file);
//                if (StringUtils.isNotEmpty(filePath)) {
//                    filePaths.add(filePath);
//                }
//            });
//
//            result.put("bucketName", "braineex");
//            result.put("files", filePaths);
//        }
//
//        return result;
//    }

    private String getFileListItem(OSSObjectSummary objectSummary) {
        return objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")";
    }

    /**
     * 获取文件列表
     *
     * @return List<String>
     */
    public List<String> getFileList() {
        return this.getFileList(this.bucketName);
    }

    /**
     * 获取文件列表
     *
     * @param bucketName 存储空间名称
     * @return List<String>
     */
    public List<String> getFileList(String bucketName) {
        //创建OSSClient实例
        OSS ossClient = getOssClient();
        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        List<String> result = objectListing.getObjectSummaries().stream().map(this::getFileListItem).collect(Collectors.toList());
        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Get object list successful！");
        return result;
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件名列表
     * @return List<String>
     */
    public List<String> batchDelete(List<String> objectNames) {
        return this.batchDelete(this.bucketName, objectNames);
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件名列表
     * @param bucketName  存储空间名称
     * @return List<String>
     */
    public List<String> batchDelete(String bucketName, List<String> objectNames) {
        List<String> deletedObjects = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(objectNames)) {
            OSS ossClient = this.getOssClient();

            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
            //true表示简单模式，false表示详细模式。默认为详细
            deleteObjectsRequest.setQuiet(true);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest.withKeys(objectNames));
            //详细模式下为删除成功的文件列表，简单模式下为删除失败的文件列表
            deletedObjects = deleteObjectsResult.getDeletedObjects();
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        log.debug("[Eurynome] |- Batch delete oss object successful！");

        return deletedObjects;
    }

    /**
     * 删除对象
     *
     * @param objectName 需要删除的对象名称
     */
    public void delete(String objectName) {
        this.delete(this.bucketName, objectName);
    }

    /**
     * 删除对象
     *
     * @param bucketName 存储空间名称
     * @param objectName 需要删除的对象名称
     */
    public void delete(String bucketName, String objectName) {
        //创建OSSClient实例
        OSS ossClient = this.getOssClient();
        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Delete oss object successful！");
    }

    public void deleteByPrefix(String bucketName, String prefix) {
        OSS ossClient = this.getOssClient();

        // 列举所有包含指定前缀的文件并删除。
        String nextMarker = null;
        ObjectListing objectListing = null;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName)
                    .withPrefix(prefix)
                    .withMarker(nextMarker);

            objectListing = ossClient.listObjects(listObjectsRequest);
            if (objectListing.getObjectSummaries().size() > 0) {
                List<String> keys = new ArrayList<>();
                for (OSSObjectSummary s : objectListing.getObjectSummaries()) {
                    log.debug("[Eurynome] |- Delete by prefix for key name: {}", s.getKey());
                    keys.add(s.getKey());
                }
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
                ossClient.deleteObjects(deleteObjectsRequest);
            }

            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());

        // 关闭OSSClient。
        ossClient.shutdown();

        log.debug("[Eurynome] |- Delete by prefix successfully!");
    }
}
