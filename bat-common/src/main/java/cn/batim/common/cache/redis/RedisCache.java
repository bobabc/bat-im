package cn.batim.common.cache.redis;

import cn.batim.common.cache.BatCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wchao
 * 2017年8月10日 下午1:35:01
 */
@Slf4j
public class RedisCache implements BatCache {

    /**
     * @param key
     * @param value
     */
    @Override
    public void putList(String key, String value) {
        JedisTemplate.getInstance().listPushHead(key, value);
    }

    /**
     * 删除
     *
     * @param key
     * @param value
     */
    @Override
    public void removeList(String key, String value) {
        JedisTemplate.getInstance().listRemove(key,1, value);
    }
    /**
     * @param key
     * @return
     */
    @Override
    public List<String> getList(String key) {
        return JedisTemplate.getInstance().listGetAll(key);
    }

    @Override
    public void clear(String likeKey) {
        long start = System.currentTimeMillis();
        JedisTemplate.getInstance().delKeysLike(likeKey);
        long end = System.currentTimeMillis();
        long iv = end - start;
        log.info("clear cache {}, cost {}ms", likeKey, iv);
    }

    @Override
    public Serializable get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return JedisTemplate.getInstance().get(key, Serializable.class);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return JedisTemplate.getInstance().get(key, clazz);
    }

    @Override
    public Collection<String> keys(String likeKey) {
        return JedisTemplate.getInstance().keys(likeKey);
    }

    /**
     * 将key value保存到缓存中
     *
     * @param key
     * @param value
     * @param timeout
     * @author wchao
     */
    @Override
    public void put(String key, Serializable value, int timeout) {
        JedisTemplate.getInstance().set(key, value, timeout);
    }

    @Override
    public void put(String key, Serializable value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        JedisTemplate.getInstance().set(key, value);
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void put(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        JedisTemplate.getInstance().set(key, value);
    }

    @Override
    public void remove(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        JedisTemplate.getInstance().delKey(key);
    }

}
