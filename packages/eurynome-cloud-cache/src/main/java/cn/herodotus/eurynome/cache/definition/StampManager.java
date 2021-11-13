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
 * Module Name: eurynome-cloud-data
 * File Name: StampManager.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 18:58:26
 */

package cn.herodotus.eurynome.cache.definition;

import cn.herodotus.eurynome.assistant.exception.stamp.IllegalStampParameterException;
import cn.herodotus.eurynome.assistant.exception.stamp.StampDeleteFailedException;
import cn.herodotus.eurynome.assistant.exception.stamp.StampHasExpiredException;
import cn.herodotus.eurynome.assistant.exception.stamp.StampMismatchException;
import com.alicp.jetcache.AutoReleaseLock;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Stamp 服务接口 </p>
 * <p>
 * 此Stamp非OAuth2 Stamp。而是用于在特定条件下生成后，在一定时间就会消除的标记性Stamp。
 * 例如，幂等、短信验证码、Auth State等，用时生成，然后进行验证，之后再删除的标记Stamp。
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 18:58
 */
public interface StampManager<K, V> extends InitializingBean {

    /**
     * 过期时间
     *
     * @return {@link Duration}
     */
    Duration getExpire();

    /**
     * 保存与Key对应的Stamp签章值
     *
     * @param key              存储Key
     * @param value            与Key对应的Stamp
     * @param expireAfterWrite 过期时间
     * @param timeUnit         过期时间单位
     */
    void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit);

    /**
     * 保存与Key对应的Stamp签章值
     *
     * @param key    存储Key
     * @param value  与Key对应的Stamp值
     * @param expire 过期时间{@link Duration}
     */
    default void put(K key, V value, Duration expire) {
        put(key, value, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 保存与Key对应的Stamp签章值
     *
     * @param key   存储Key
     * @param value 与Key对应的Stamp值
     */
    default void put(K key, V value) {
        put(key, value, getExpire());
    }

    /**
     * 生成缓存值策略方法，该方法负责生成具体存储的值。
     *
     * @param key 签章存储Key值
     * @return {@link String}
     */
    V generate(K key);

    /**
     * 创建具体的Stamp签章值，并存储至本地缓存
     *
     * @param key              签章存储Key值
     * @param expireAfterWrite 写入之后过期时间。注意：该值每次写入都会覆盖。如果有一个时间周期内的反复存取操作，需要手动计算时间差。
     * @param timeUnit         时间单位
     * @return 创建的签章值
     */
    default V create(K key, long expireAfterWrite, TimeUnit timeUnit) {
        V token = this.generate(key);
        this.put(key, token, expireAfterWrite, timeUnit);
        return token;
    }

    /**
     * 创建具体的Stamp签章值，并存储至本地缓存
     *
     * @param key    签章存储Key值
     * @param expire 过期时间{@link Duration}
     * @return 创建的签章值
     */
    default V create(K key, Duration expire) {
        return create(key, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 创建具体的Stamp签章值，并存储至本地缓存
     *
     * @param key 与签章存储Key值
     * @return 创建的签章值
     */
    default V create(K key) {
        return create(key, getExpire());
    }

    /**
     * 校验Stamp值，与本地存储的Stamp 是否匹配
     *
     * @param key   与Stamp对应的Key值
     * @param value 外部传入的Stamp值
     * @throws IllegalStampParameterException 传入Stamp错误
     * @throws StampHasExpiredException       本地数据中没有Stamp或者Stamp已经过期。
     * @throws StampMismatchException         Stamp与本地存储值不匹配
     */
    boolean check(K key, V value) throws IllegalStampParameterException, StampHasExpiredException, StampMismatchException;

    /**
     * 根据key读取Stamp
     *
     * @param key 存储数据Key值
     * @return 存储的Stamp值
     */
    V get(K key);

    /**
     * 删除与Key对应的Stamp
     *
     * @param key 存储数据Key值
     * @throws StampDeleteFailedException Stamp删除错误
     */
    void delete(K key) throws StampDeleteFailedException;

    default boolean containKey(K key) {
        V value = get(key);
        return ObjectUtils.isNotEmpty(value);
    }

    /**
     * 锁定值
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key      存储Key
     * @param expire   过期时间
     * @param timeUnit 过期时间单位
     * @return {@link AutoReleaseLock}
     */
    AutoReleaseLock lock(K key, long expire, TimeUnit timeUnit);

    /**
     * 锁定值
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key    存储Key
     * @param expire 过期时间{@link Duration}
     * @return {@link AutoReleaseLock}
     */
    default AutoReleaseLock lock(K key, Duration expire) {
        return lock(key, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 锁定值
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key 存储Key
     * @return {@link AutoReleaseLock}
     */
    default AutoReleaseLock lock(K key) {
        return lock(key, getExpire());
    }

    /**
     * 锁定并执行操作
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key      存储Key
     * @param expire   过期时间
     * @param timeUnit 过期时间单位
     * @param action   需要执行的操作 {@link Runnable}
     * @return 是否执行成功
     */
    boolean lockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action);

    /**
     * 锁定并执行操作
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key    存储Key
     * @param expire 过期时间{@link Duration}
     * @param action 需要执行的操作 {@link Runnable}
     * @return 是否执行成功
     */
    default boolean lockAndRun(K key, Duration expire, Runnable action) {
        return lockAndRun(key, expire.toMillis(), TimeUnit.MILLISECONDS, action);
    }

    /**
     * 锁定并执行操作
     * <p>
     * 非堵塞的尝试获取一个锁，如果对应的key还没有锁，返回一个AutoReleaseLock，否则立即返回空。如果Cache实例是本地的，它是一个本地锁，在本JVM中有效；如果是redis等远程缓存，它是一个不十分严格的分布式锁。锁的超时时间由expire和timeUnit指定。多级缓存的情况会使用最后一级做tryLock操作。
     * {@see https://github.com/alibaba/jetcache/wiki/CacheAPI_CN}
     *
     * @param key    存储Key
     * @param action 需要执行的操作 {@link Runnable}
     * @return 是否执行成功
     */
    default boolean lockAndRun(K key, Runnable action) {
        return lockAndRun(key, getExpire(), action);
    }
}
