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
 * File Name: MinioController.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 21:00:08
 */

package cn.herodotus.eurynome.integration.oss.controller;

import cn.herodotus.eurynome.rest.annotation.AccessLimited;
import cn.herodotus.eurynome.rest.annotation.Idempotent;
import cn.herodotus.eurynome.assistant.domain.Result;
import cn.herodotus.eurynome.integration.oss.core.MinioTemplate;
import cn.herodotus.eurynome.integration.oss.domain.MinioItem;
import cn.herodotus.eurynome.integration.oss.domain.MinioObject;
import cn.herodotus.eurynome.integration.oss.domain.ObjectDetail;
import io.minio.messages.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 18:26
 */
@RestController
@RequestMapping("/oss/minio")
@Tags({@Tag(name = "Minio 对象存储接口"), @Tag(name = "对象存储接口"), @Tag(name = "外部应用集成接口")})
public class MinioController {

    @Autowired
    private MinioTemplate minioTemplate;

    @Operation(summary = "创建Bucket", description = "创建Bucket",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "Bucket详情", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "userId"),
    })
    @PostMapping("/bucket/{bucketName}")
    public Result<Bucket> createBucker(@PathVariable String bucketName){
        minioTemplate.createBucket(bucketName);
        Bucket bucket = minioTemplate.getBucket(bucketName).get();
        if (ObjectUtils.isNotEmpty(bucket)) {
            return Result.success("创建成功!", bucket);
        } else {
            return Result.failure("创建失败！");
        }
    }

    @Operation(summary = "获取全部Bucket", description = "获取全部Bucket",
            responses = {@ApiResponse(description = "Bucket列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))})
    @GetMapping("/bucket")
    public Result<List<Bucket>> getBuckets() {
        List<Bucket> buckets = minioTemplate.getAllBuckets();
        if (ObjectUtils.isNotEmpty(buckets)) {
            if (CollectionUtils.isNotEmpty(buckets)) {
                return Result.success("查询数据成功！", buckets);
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    @Operation(summary = "删除Bucket", description = "根据名称获取bucket",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
    })
    @GetMapping("/bucket/{bucketName}")
    public Result<Bucket> getBucket(@PathVariable String bucketName) {
        Bucket bucket = minioTemplate.getBucket(bucketName).get();
        if (ObjectUtils.isNotEmpty(bucket)) {
            return Result.success("创建成功!", bucket);
        } else {
            return Result.failure("创建失败！");
        }
    }

    @Idempotent
    @Operation(summary = "删除Bucket", description = "删除bucket",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
    })
    @DeleteMapping("/bucket/{bucketName}")
    public Result<String> deleteBucket(@PathVariable String bucketName) {
        minioTemplate.removeBucket(bucketName);
        return Result.success("删除成功");
    }

    @Operation(summary = "文件上传", description = "存入对象到bucket并设置对象名称",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "multipartFile", required = true, description = "multipartFile对象", schema = @Schema(implementation = MultipartFile.class)),
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称")
    })
    @PostMapping("/object/{bucketName}")
    public Result<MinioObject> putObject(@RequestBody MultipartFile multipartFile, @PathVariable String bucketName) {
        String name = multipartFile.getOriginalFilename();
        return this.putObject(multipartFile, bucketName, name);

    }

    @Operation(summary = "文件上传", description = "存入对象到bucket并设置对象名称",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))})
    @Parameters({
            @Parameter(name = "multipartFile", required = true, description = "multipartFile对象", schema = @Schema(implementation = MultipartFile.class)),
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
            @Parameter(name = "objectName", required = true, in = ParameterIn.PATH, description = "对象名称"),
    })
    @PostMapping("/object/{bucketName}/{objectName}")
    public Result<MinioObject> putObject(@RequestBody MultipartFile multipartFile, @PathVariable String bucketName, @PathVariable String objectName) {
        try {
            minioTemplate.putObject(bucketName, objectName, multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getContentType());
            MinioObject minioObject = new MinioObject(minioTemplate.getObjectInfo(bucketName, objectName));
            return Result.success("上传成功", minioObject);
        } catch (IOException e) {
            return Result.failure("上传失败，MultipartFile IO 错误！");
        }
    }


    @AccessLimited
    @Operation(summary = "获取对象", description = "根据bucket名称和对象名称过滤所有对象",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
            @Parameter(name = "objectName", required = true, in = ParameterIn.PATH, description = "对象名称"),
    })
    @GetMapping("/object/{bucketName}/{objectName}")
    public  Result<List<MinioItem>>  filterObject(@PathVariable String bucketName, @PathVariable String objectName)  {
        List<MinioItem> minioItems = minioTemplate.getAllObjectsByPrefix(bucketName, objectName, true);
        if (ObjectUtils.isNotEmpty(minioItems)) {
            if (CollectionUtils.isNotEmpty(minioItems)) {
                return Result.success("查询数据成功！", minioItems);
            } else {
                return Result.empty("未查询到数据！");
            }
        } else {
            return Result.failure("查询数据失败！");
        }
    }

    @AccessLimited
    @Operation(summary = "获取对象", description = "根据名称获取bucket下的对象并设置外链的过期时间",
            responses = {@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObjectDetail.class)))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
            @Parameter(name = "objectName", required = true, in = ParameterIn.PATH, description = "对象名称"),
            @Parameter(name = "expires", required = true, in = ParameterIn.PATH, description = "过期时间，Duration表达式"),
    })
    @GetMapping("/object/{bucketName}/{objectName}/{expires}")
    public Result<ObjectDetail> getObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable String expires) throws Exception {

        Duration expireDuration = Duration.parse(expires);
        if (expireDuration != Duration.ZERO) {
            String url = minioTemplate.getObjectURL(bucketName, objectName,expireDuration);
            if (StringUtils.isNotBlank(url)) {
                ObjectDetail objectDetail = new ObjectDetail();
                objectDetail.setObjectName(objectName);
                objectDetail.setBucketName(bucketName);
                objectDetail.setUrl(url);
                objectDetail.setExpires(expireDuration.toString());

                return Result.success("获取成功！", objectDetail);
            } else {
                return Result.failure("获取失败！");
            }
        } else {
            return Result.failure("Expires 参数格式错误!");
        }
    }

    @Idempotent
    @Operation(summary = "删除对象", description = "根据Bucket名字和对象名称删除对象",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "bucketName", required = true, in = ParameterIn.PATH, description = "Bucket名称"),
            @Parameter(name = "objectName", required = true, in = ParameterIn.PATH, description = "对象名称")
    })
    @DeleteMapping("/object/{bucketName}/{objectName}/")
    public Result<String> deleteObject(@PathVariable String bucketName, @PathVariable String objectName) {
        minioTemplate.removeObject(bucketName, objectName);
        return Result.success("删除成功");
    }
}
