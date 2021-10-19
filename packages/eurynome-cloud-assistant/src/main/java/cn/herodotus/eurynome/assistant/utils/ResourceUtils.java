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
 * Module Name: eurynome-cloud-assistant
 * File Name: ResourceUtils.java
 * Author: gengwei.zheng
 * Date: 2021/10/17 22:53:17
 */

package cn.herodotus.eurynome.assistant.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * <p>Description: 资源查找单例工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 12:18
 */
public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    private static volatile ResourceUtils INSTANCE;

    private final PathMatchingResourcePatternResolver resolver;

    private ResourceUtils() {
        this.resolver = new PathMatchingResourcePatternResolver();
    }

    private static ResourceUtils getInstance() {
        if (ObjectUtils.isEmpty(INSTANCE)) {
            synchronized (ResourceUtils.class) {
                if (ObjectUtils.isEmpty(INSTANCE)) {
                    INSTANCE = new ResourceUtils();
                }
            }
        }

        return INSTANCE;
    }

    private PathMatchingResourcePatternResolver getResolver() {
        return this.resolver;
    }

    public static Resource getResource(String location) {
        return ResourceUtils.getInstance().getResolver().getResource(location);
    }

    public static Resource[] getResources(String locationPattern) throws IOException {
        return ResourceUtils.getInstance().getResolver().getResources(locationPattern);
    }

    public static File getFile(String location) throws IOException {
        return ResourceUtils.getResource(location).getFile();
    }

    public static InputStream getInputStream(String location) throws IOException {
        return ResourceUtils.getResource(location).getInputStream();
    }

    public static String getFilename(String location) {
        return ResourceUtils.getResource(location).getFilename();
    }

    public static URI getURI(String location) throws IOException {
        return ResourceUtils.getResource(location).getURI();
    }

    public static URL getURL(String location) throws IOException {
        return ResourceUtils.getResource(location).getURL();
    }

    public static long contentLength(String location) throws IOException {
        return ResourceUtils.getResource(location).contentLength();
    }

    public static long lastModified(String location) throws IOException {
        return ResourceUtils.getResource(location).lastModified();
    }

    public static boolean exists(String location) {
        return ResourceUtils.getResource(location).exists();
    }

    public static boolean isFile(String location) {
        return ResourceUtils.getResource(location).isFile();
    }

    public static boolean isReadable(String location) {
        return ResourceUtils.getResource(location).isReadable();
    }

    public static boolean isOpen(String location) {
        return ResourceUtils.getResource(location).isOpen();
    }
}
