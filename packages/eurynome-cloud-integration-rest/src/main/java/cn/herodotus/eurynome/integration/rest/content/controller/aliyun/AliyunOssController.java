package cn.herodotus.eurynome.integration.rest.content.controller.aliyun;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.herodotus.eurynome.integration.content.service.aliyun.oss.AliyunOssService;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * <p>Description: 阿里云OSS操作接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/21 9:14
 */
@Slf4j
@RestController
@RequestMapping("/integration/rest/aliyun/oss")
@Api(tags = {"第三方集成接口", "阿里云集成接口", "阿里云OSS操作接口"})
public class AliyunOssController {

    @Autowired
    private AliyunOssService aliyunOssService;
    @Autowired
    private AliyunProperties aliyunProperties;

    private String createName(String fileName, String directory) {
        String name = IdUtil.fastSimpleUUID();
        return directory + "/" + name;
    }

    private String uploadFileOperation(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileType = aliyunOssService.getFileClassify(originalFileName);
            String fileName = this.createName(originalFileName, fileType);
            String filePath = aliyunOssService.uploadFile(fileName, file.getInputStream());
            log.debug("[Eurynome] |- Get upload file path [{}] from MultipartFile", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("[Eurynome] |- Upload file catch the IOException: {}", e.getMessage());
            return null;
        }
    }


    @ApiOperation(value = "文件或图片上传", notes = "支持多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", required = true, value = "待上传的文件", dataType = "MultipartFile", dataTypeClass = MultipartFile.class, paramType = "query")
    })
    @PostMapping(value = "/file")
    public Result<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ObjectUtils.isNotEmpty(file)) {
            String filePath = this.uploadFileOperation(file);
            if (StringUtils.isNotEmpty(filePath)) {
                Map<String, Object> result = new HashMap<>();
                result.put("bucketName", aliyunProperties.getOss().getBucketName());
                result.put("url", filePath);
                return new Result<Map<String, Object>>().ok().data(result);
            }
        }
        return new Result<Map<String, Object>>().failed().message("上传文件出错");
    }

    @ApiOperation(value = "文件或图片上传", notes = "支持多文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", required = true, value = "待上传的文件", dataType = "MultipartFile", dataTypeClass = MultipartFile.class, paramType = "query")
    })
    @PostMapping(value = "/files")
    public Result<Map<String, Object>> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        if (ArrayUtils.isNotEmpty(files)) {
            Map<String, Object> result = new HashMap<>();
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                String filePath = this.uploadFileOperation(file);
                if (StringUtils.isNotEmpty(filePath)) {
                    urls.add(filePath);
                }
            }
            result.put("bucketName", aliyunProperties.getOss().getBucketName());
            result.put("url", urls);
            return new Result<Map<String, Object>>().ok().data(result);
        }

        return new Result<Map<String, Object>>().failed().message("上传文件获取file参数出错");
    }
}
