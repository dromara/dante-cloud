package cn.herodotus.eurynome.data.annotation;

import cn.herodotus.eurynome.data.configuration.DataConfiguration;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.*;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: EnableRedisStorage </p>
 *
 * <p>Description: 开启平台数据存储 </p>
 * <p>
 * 目前主要功能：
 * 1.开启JpaAuditing
 * 2.开启Redis存储
 * 3.开启JetCache缓存
 *
 * @author : gengwei.zheng
 * @date : 2020/2/3 17:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaAuditing
@EnableRedisStorage
@EnableCreateCacheAnnotation
@Import(DataConfiguration.class)
public @interface EnableHerodotusData {
}
