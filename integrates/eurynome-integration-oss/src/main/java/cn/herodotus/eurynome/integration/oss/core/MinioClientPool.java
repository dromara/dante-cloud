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
 * File Name: MinioClientPool.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 21:00:08
 */

package cn.herodotus.eurynome.integration.oss.core;


import cn.herodotus.eurynome.integration.oss.properties.MinioProperties;
import io.minio.MinioClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p>Description: Minio 客户端连接池 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 10:54
 */
public class MinioClientPool {

    private final GenericObjectPool<MinioClient> minioClientPool;

    public MinioClientPool(MinioProperties minioProperties) {

        MinioClientPoolFactory factory = new MinioClientPoolFactory(minioProperties);

        GenericObjectPoolConfig<MinioClient> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(minioProperties.getPool().getMaxTotal());
        config.setMaxIdle(minioProperties.getPool().getMaxIdle());
        config.setMinIdle(minioProperties.getPool().getMinIdle());
        config.setMaxWaitMillis(minioProperties.getPool().getMaxWait().toMillis());
        config.setBlockWhenExhausted(minioProperties.getPool().isBlockWhenExhausted());
        minioClientPool = new GenericObjectPool<>(factory, config);
    }

    public GenericObjectPool<MinioClient> getMinioClientPool() {
        return minioClientPool;
    }

    public static class MinioClientPoolFactory extends BasePooledObjectFactory<MinioClient> {

        private final MinioProperties minioProperties;

        public MinioClientPoolFactory(MinioProperties minioProperties) {
            this.minioProperties = minioProperties;
        }

        @Override
        public MinioClient create() throws Exception {
            return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        }

        @Override
        public PooledObject<MinioClient> makeObject() throws Exception {
            return super.makeObject();
        }

        @Override
        public PooledObject<MinioClient> wrap(MinioClient minioClient) {
            return new DefaultPooledObject<>(minioClient);
        }
    }
}
