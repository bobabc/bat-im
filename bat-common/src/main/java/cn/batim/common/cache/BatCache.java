package cn.batim.common.cache;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author bob
 * 2022年8月10日 上午11:38:26
 */
public interface BatCache {

    /**
     * 清空所有缓存
     *
     * @param likeKey
     */
    void clear(String likeKey);

    /**
     * 根据key获取value
     *
     * @param key
     * @return BatConfigUtil.getInt
     */
    Serializable get(String key);

    /**
     * 根据key获取value
     *
     * @param key
     * @param clazz
     * @return
     * @author: wchao
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 获取所有的相似key
     *
     * @param likeKey
     * @return
     */
    Collection<String> keys(String likeKey);

    /**
     * 将key value保存到缓存中
     *
     * @param key
     * @param value BatConfigUtil.getInt
     */
    void put(String key, Serializable value, int timeout);

    /**
     * @param key
     * @param value
     */
    void put(String key, Serializable value);

    /**
     * 删除一个key
     *
     * @param key
     * @return BatConfigUtil.getInt
     */
    void remove(String key);

}
