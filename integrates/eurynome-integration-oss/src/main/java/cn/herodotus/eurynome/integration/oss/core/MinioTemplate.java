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
 * File Name: MinioTemplate.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 21:00:08
 */

package cn.herodotus.eurynome.integration.oss.core;

import cn.herodotus.eurynome.integration.oss.domain.MinioItem;
import cn.herodotus.eurynome.integration.oss.exception.*;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>Description: Minio 模版 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 11:14
 */
public class MinioTemplate {

    private static final Logger log = LoggerFactory.getLogger(MinioTemplate.class);

    private final MinioClientPool minioClientPool;

    public MinioTemplate(MinioClientPool minioClientPool) {
        this.minioClientPool = minioClientPool;
    }

    private void close(MinioClient minioClient) {
        if (ObjectUtils.isNotEmpty(minioClient)) {
            this.minioClientPool.getMinioClientPool().returnObject(minioClient);
        }
    }

    public MinioClient getMinioClient() throws MinioClientPoolErrorExeption {
        try {
            MinioClient minioClient = minioClientPool.getMinioClientPool().borrowObject();
            log.debug("[Herodotus] |- Fetch minio client from connetion pool.");
            return minioClient;
        } catch (Exception e) {
            log.error("[Herodotus] |- Can not fetch minio client from pool.");
            throw new MinioClientPoolErrorExeption("Can not fetch minio client from pool.");
        }
    }

    public void createBucket(String bucketName) {
        MinioClient minioClient = getMinioClient();

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.debug("[Herodotus] |- Minio create Bucket [{}].", bucketName);
            }
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in createBucket.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in createBucket.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in createBucket.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in createBucket.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in createBucket.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in createBucket.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in createBucket.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in createBucket.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in createBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public List<Bucket> getAllBuckets() {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.listBuckets();
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getAllBuckets.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getAllBuckets.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getAllBuckets.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getAllBuckets.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getAllBuckets.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getAllBuckets.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getAllBuckets.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getAllBuckets.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getAllBuckets.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public Optional<Bucket> getBucket(String bucketName) {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getBucket.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getBucket.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getBucket.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getBucket.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getBucket.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getBucket.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getBucket.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getBucket.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void removeBucket(String bucketName) {
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in removeBucket.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in removeBucket.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in removeBucket.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in removeBucket.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in removeBucket.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in removeBucket.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in removeBucket.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in removeBucket.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in removeBucket.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public List<MinioItem> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        MinioClient minioClient = getMinioClient();

        try {
            List<MinioItem> objectList = new ArrayList<>();
            Iterable<Result<Item>> objectsIterator = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
            objectsIterator.forEach(i -> {
                try {
                    objectList.add(new MinioItem(i.get()));
                } catch (ErrorResponseException e) {
                    log.error("[Herodotus] |- Minio cache ErrorResponseException in getAllObjectsByPrefix.", e);
                    throw new MinioErrorResponseException("Minio response error.");
                } catch (InsufficientDataException e) {
                    log.error("[Herodotus] |- Minio cache InsufficientDataException in getAllObjectsByPrefix.", e);
                    throw new MinioInsufficientDataException("Minio insufficient data error.");
                } catch (InternalException e) {
                    log.error("[Herodotus] |- Minio cache InternalException in getAllObjectsByPrefix.", e);
                    throw new MinioInternalException("Minio internal error.");
                } catch (InvalidKeyException e) {
                    log.error("[Herodotus] |- Minio cache InvalidKeyException in getAllObjectsByPrefix.", e);
                    throw new MinioInvalidKeyException("Minio key invalid.");
                } catch (InvalidResponseException e) {
                    log.error("[Herodotus] |- Minio cache InvalidResponseException in getAllObjectsByPrefix.", e);
                    throw new MinioInvalidResponseException("Minio response invalid.");
                } catch (IOException e) {
                    log.error("[Herodotus] |- Minio cache IOException in removeBucket.", e);
                    throw new MinioIOException("Minio io error.");
                } catch (NoSuchAlgorithmException e) {
                    log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getAllObjectsByPrefix.", e);
                    throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
                } catch (ServerException e) {
                    log.error("[Herodotus] |- Minio cache ServerException in getAllObjectsByPrefix.", e);
                    throw new MinioServerException("Minio server error.");
                } catch (XmlParserException e) {
                    log.error("[Herodotus] |- Minio cache XmlParserException in getAllObjectsByPrefix.", e);
                    throw new MinioXmlParserException("Minio xml parser error.");
                }
            });
            return objectList;
        } finally {
            close(minioClient);
        }
    }

    private int calculate(Duration expires) {
        long data = expires.toMillis() / 1000;
        return new Long(data).intValue();
    }

    public String getObjectURL(String bucketName, String objectName, Duration expires) {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(calculate(expires)).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getObjectURL.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getObjectURL.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getObjectURL.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getObjectURL.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getObjectURL.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getObjectURL.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getObjectURL.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getObjectURL.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getObjectURL.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public String getObjectURL(String bucketName, String objectName) {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getObjectURL.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getObjectURL.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getObjectURL.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getObjectURL.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getObjectURL.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getObjectURL.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getObjectURL.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getObjectURL.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getObjectURL.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void putFile(String bucketName, String fileName, InputStream stream) {
        putFile(bucketName, fileName, stream, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    public void putFile(String bucketName, String fileName, InputStream stream, String contentType) {
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(stream, stream.available(), -1).contentType(contentType).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in putFile.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in putFile.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in putFile.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in putFile.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in putFile.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in putFile.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in putFile.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in putFile.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in putFile.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contentType){
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contentType).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in putObject.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in putObject.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in putObject.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in putObject.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in putObject.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in putObject.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in putObject.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in putObject.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in putObject.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public InputStream getObject(String bucketName, String objectName) {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getObject.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getObject.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getObject.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getObject.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getObject.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getObject.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getObject.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getObject.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getObject.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public StatObjectResponse getObjectInfo(String bucketName, String objectName) {
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in getObjectInfo.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in getObjectInfo.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in getObjectInfo.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in getObjectInfo.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in getObjectInfo.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in getObjectInfo.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in getObjectInfo.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in getObjectInfo.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in getObjectInfo.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void removeObject(String bucketName, String objectName) {
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in removeObject.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in removeObject.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in removeObject.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in removeObject.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in removeObject.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in removeObject.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in removeObject.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in removeObject.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in removeObject.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    public void downloadObject(String bucketName, String objectName, String fileName) {
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(fileName).build());
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio cache ErrorResponseException in downloadObject.", e);
            throw new MinioErrorResponseException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio cache InsufficientDataException in downloadObject.", e);
            throw new MinioInsufficientDataException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio cache InternalException in downloadObject.", e);
            throw new MinioInternalException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio cache InvalidKeyException in downloadObject.", e);
            throw new MinioInvalidKeyException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio cache InvalidResponseException in downloadObject.", e);
            throw new MinioInvalidResponseException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio cache IOException in downloadObject.", e);
            throw new MinioIOException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio cache NoSuchAlgorithmException in downloadObject.", e);
            throw new MinioNoSuchAlgorithmException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio cache ServerException in downloadObject.", e);
            throw new MinioServerException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio cache XmlParserException in downloadObject.", e);
            throw new MinioXmlParserException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }
}
