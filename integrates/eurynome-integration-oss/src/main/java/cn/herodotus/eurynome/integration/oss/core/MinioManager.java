/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-integration-oss
 * File Name: MinioManager.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 21:00:08
 */

package cn.herodotus.eurynome.integration.oss.core;

import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.integration.oss.domain.MinioObject;
import cn.herodotus.eurynome.integration.oss.properties.MinioProperties;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 15:45
 */
public class MinioManager {

    private static final Logger log = LoggerFactory.getLogger(MinioManager.class);

    private static Map<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put("audio/aac", ".aac");
        contentTypeMap.put("application/x-abiword", ".abw");
        contentTypeMap.put("application/x-freearc", ".arc");
        contentTypeMap.put("video/x-msvideo", ".avi");
        contentTypeMap.put("application/vnd.amazon.ebook", ".azw");
        contentTypeMap.put("application/octet-stream", ".bin");
        contentTypeMap.put("image/bmp", ".bmp");
        contentTypeMap.put("application/x-bzip", ".bz");
        contentTypeMap.put("application/x-bzip2", ".bz2");
        contentTypeMap.put("application/x-csh", ".csh");
        contentTypeMap.put("text/css", ".css");
        contentTypeMap.put("text/csv", ".csv");
        contentTypeMap.put("application/msword", ".doc");
        contentTypeMap.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
        contentTypeMap.put("application/vnd.ms-fontobject", ".eot");
        contentTypeMap.put("application/epub+zip", ".epub");
        contentTypeMap.put("image/gif", ".gif");
        contentTypeMap.put("text/html", ".htm");
        contentTypeMap.put("image/vnd.microsoft.icon", ".ico");
        contentTypeMap.put("text/calendar", ".ics");
        contentTypeMap.put("application/java-archive", ".jar");
        contentTypeMap.put("image/jpeg", ".jpeg");
        contentTypeMap.put("text/javascript", ".js");
        contentTypeMap.put("application/json", ".json");
        contentTypeMap.put("application/ld+json", ".jsonld");
        contentTypeMap.put("audio/midi", ".mid");
        contentTypeMap.put("audio/x-midi", ".mid");
        contentTypeMap.put("audio/mpeg", ".mp3");
        contentTypeMap.put("video/mpeg", ".mpeg");
        contentTypeMap.put("application/vnd.apple.installer+xml", ".mpkg");
        contentTypeMap.put("application/vnd.oasis.opendocument.presentation", ".odp");
        contentTypeMap.put("application/vnd.oasis.opendocument.spreadsheet", ".ods");
        contentTypeMap.put("application/vnd.oasis.opendocument.text", ".odt");
        contentTypeMap.put("audio/ogg", ".oga");
        contentTypeMap.put("video/ogg", ".ogv");
        contentTypeMap.put("application/ogg", ".ogx");
        contentTypeMap.put("font/otf", ".otf");
        contentTypeMap.put("image/png", ".png");
        contentTypeMap.put("application/pdf", ".pdf");
        contentTypeMap.put("application/vnd.ms-powerpoint", ".ppt");
        contentTypeMap.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
        contentTypeMap.put("application/x-rar-compressed", ".rar");
        contentTypeMap.put("application/rtf", ".rtf");
        contentTypeMap.put("application/x-sh", ".sh");
        contentTypeMap.put("image/svg+xml", ".svg");
        contentTypeMap.put("application/x-shockwave-flash", ".swf");
        contentTypeMap.put("application/x-tar", ".tar");
        contentTypeMap.put("image/tiff", "tiff");
        contentTypeMap.put("font/ttf", ".ttf");
        contentTypeMap.put("text/plain", ".txt");
        contentTypeMap.put("application/vnd.visio", ".vsd");
        contentTypeMap.put("audio/wav", ".wav");
        contentTypeMap.put("audio/webm", ".weba");
        contentTypeMap.put("video/webm", ".webm");
        contentTypeMap.put("image/webp", ".webp");
        contentTypeMap.put("font/woff", ".woff");
        contentTypeMap.put("font/woff2", ".woff2");
        contentTypeMap.put("application/xhtml+xml", ".xhtml");
        contentTypeMap.put("application/vnd.ms-excel", ".xls");
        contentTypeMap.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
        contentTypeMap.put("application/xml", ".xml");
        contentTypeMap.put("text/xml", ".xml");
        contentTypeMap.put("application/vnd.mozilla.xul+xml", ".xul");
        contentTypeMap.put("application/zip", ".zip");
        contentTypeMap.put("video/3gpp", ".3gp");
        contentTypeMap.put("audio/3gpp", ".3gp");
        contentTypeMap.put("video/3gpp2", ".3g2");
        contentTypeMap.put("audio/3gpp2", ".3g2");
        contentTypeMap.put("application/x-7z-compressed", ".7z");
    }

    private MinioTemplate minioTemplate;
    private MinioProperties minioProperties;

    public MinioManager(MinioTemplate minioTemplate, MinioProperties minioProperties) {
        this.minioTemplate = minioTemplate;
        this.minioProperties = minioProperties;
    }

    private String getFileName(String fileName, String fileSuffix) {
        return fileSuffix.concat(File.separator).concat(LocalDate.now().format(DateTimeFormatter.ofPattern(minioProperties.getTimestampFormat()))).concat(File.separator).concat(fileName);
    }

    private String getBucketName() {
        String bucketName = minioProperties.getBucketNamePrefix();
        minioTemplate.createBucket(bucketName);
        return bucketName;
    }

    private String getSuffix(String fileName) {
        return StringUtils.substringAfterLast(fileName, SymbolConstants.PERIOD);
    }

    /**
     * 文件上传
     *
     * @param multipartFile {@link MultipartFile}
     * @param fileName      文件名
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    private MinioObject putMultipartFile(MultipartFile multipartFile, String fileName) throws IOException {
        if (StringUtils.isNotBlank(fileName)) {
            String fileSuffix = this.getSuffix(fileName);
            String bucketName = this.getBucketName();
            fileName = this.getFileName(fileName, fileSuffix);
            minioTemplate.putFile(bucketName, fileName, multipartFile.getInputStream(), multipartFile.getContentType());

            return new MinioObject(bucketName, fileName, new Date(), multipartFile.getInputStream().available(), null, multipartFile.getContentType());
        } else {
            log.error("[Herodotus] |- Argument fileName is blank in uploadMultipartFile.");
            throw new IllegalArgumentException("Argument is illegal.");
        }
    }

    /**
     * 文件上传自定义文件名
     *
     * @param multipartFile {@link MultipartFile}
     * @param fileName      文件名
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    public MinioObject uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        return this.putMultipartFile(multipartFile, fileName);
    }

    /**
     * 文件上传
     *
     * @param multipartFile {@link MultipartFile}
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    public MinioObject uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        return this.putMultipartFile(multipartFile, fileName);
    }

    private MinioObject putFile(InputStream stream, String fileName) throws IOException {
        if (StringUtils.isNotBlank(fileName)) {
            String fileSuffix = getSuffix(fileName);
            String bucketName = this.getBucketName();
            fileName = this.getFileName(fileName, fileSuffix);
            minioTemplate.putFile(bucketName, fileName, stream);
            return new MinioObject(bucketName, fileName, new Date(), stream.available(), null, null);
        } else {
            log.error("[Herodotus] |- Argument fileName is blank in putFile.");
            throw new IllegalArgumentException("Argument is illegal.");
        }
    }

    /**
     * 文件上传
     *
     * @param file     {@link File}
     * @param fileName 文件名
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    private MinioObject putFile(File file, String fileName) throws IOException {
        InputStream stream = new FileInputStream(file);
        return putFile(stream, fileName);
    }

    /**
     * 文件上传自定义文件名
     *
     * @param file     {@link File}
     * @param fileName 文件名
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    public MinioObject uploadFile(File file, String fileName) throws IOException {
        return this.putFile(file, fileName);
    }

    /**
     * 文件上传
     *
     * @param file {@link File}
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    public MinioObject uploadFile(File file) throws IOException {
        String fileName = file.getName();
        return this.putFile(file, fileName);
    }

    /**
     * 文件上传
     *
     * @param stream   {@link InputStream}
     * @param fileName 文件名
     * @return {@link MinioObject}
     * @throws IOException 输入输出错误
     */
    public MinioObject uploadFile(InputStream stream, String fileName) throws IOException {
        return this.putFile(stream, fileName);
    }


    /**
     * 获取文件外链并设置有效时长
     *
     * @param bucketName 对象存储空间名称
     * @param fileName   文件名
     * @param expires    过期时间 {@link Duration}
     * @return 文件访问URL
     */
    public String getFileUrl(String bucketName, String fileName, Duration expires) {
        if (StringUtils.isNotBlank(bucketName) && StringUtils.isNotBlank(fileName) && expires != Duration.ZERO) {
            return minioTemplate.getObjectURL(bucketName, fileName, expires);
        } else {
            log.error("[Herodotus] |- Argument bucketName or fileName is blank in getFileUrl.");
            throw new IllegalArgumentException("Argument is illegal.");
        }
    }


    /**
     * 获取文件外链并设置有效时长，默认为3天
     *
     * @param bucketName 对象存储空间名称
     * @param fileName   文件名
     * @return 文件访问URL
     */
    public String getFileUrl(String bucketName, String fileName) {
        return getFileUrl(bucketName, fileName, Duration.ofDays(3L));
    }

    /**
     * 获取文件流
     *
     * @param bucketName 存储空间名称
     * @param fileName   文件名
     * @return {@link InputStream}
     */
    public InputStream getFileInputStream(String bucketName, String fileName) {
        if (StringUtils.isNotBlank(bucketName) && StringUtils.isNotBlank(fileName)) {
            return minioTemplate.getObject(bucketName, fileName);
        } else {
            log.error("[Herodotus] |- Argument bucketName or fileName is blank in getFileInputStream.");
            throw new IllegalArgumentException("Argument is illegal.");
        }
    }

    /**
     * 移除文件
     *
     * @param bucketName 存储空间名称
     * @param fileName   文件名
     */
    public void removeFile(String bucketName, String fileName) {
        if (StringUtils.isNotBlank(bucketName) && StringUtils.isNotBlank(fileName)) {
            minioTemplate.removeObject(bucketName, fileName);
        } else {
            log.error("[Herodotus] |- Argument bucketName or fileName is blank in removeFile.");
            throw new IllegalArgumentException("Argument is illegal.");
        }
    }

    /**
     * 上传通过连接共享的文件
     */
    public MinioObject uploadShareLink(String shareLink) throws Exception {
        InputStream urlInputStream = null;
        InputStream resultStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(shareLink).openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
            urlInputStream = conn.getInputStream();
            String fileSuffix = contentTypeMap.get(conn.getContentType());
            byte[] getData = readInputStream(urlInputStream);
            resultStream = new ByteArrayInputStream(getData);
            return this.uploadFile(resultStream, IdUtil.fastUUID() + fileSuffix);
        } finally {
            if (null != resultStream) {
                resultStream.close();
            }
            if (null != urlInputStream) {
                urlInputStream.close();
            }
        }
    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
