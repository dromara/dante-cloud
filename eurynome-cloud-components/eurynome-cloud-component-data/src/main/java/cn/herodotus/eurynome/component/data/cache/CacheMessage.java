package cn.herodotus.eurynome.component.data.cache;

import java.io.Serializable;

/**
 * redis消息发布/订阅，传输的消息类
 * @author gengwei.zheng
 * @date 2019.09
 */
public class CacheMessage implements Serializable {

    public CacheMessage() {
    }

    private String cacheName;

    private Object key;

    public CacheMessage(String cacheName, Object key) {
        this.cacheName = cacheName;
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "RedisCaffeineCacheMessage{" +
                "cacheName='" + cacheName + '\'' +
                ", key=" + key +
                '}';
    }
}
