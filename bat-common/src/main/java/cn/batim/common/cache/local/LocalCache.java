package cn.batim.common.cache.local;

import cn.batim.common.cache.BatCache;
import cn.batim.common.queue.BatQueue;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本机存储
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 18:31
 */
public class LocalCache implements BatCache {
    private Map<String, Object> LINK_MAP;
    /**
     * 过期守候队列
     */
    private BatQueue<String> clearQueue;

    public LocalCache() {
        LINK_MAP = new ConcurrentHashMap<>(32);
        clearQueue = BatQueue.getInstance("cache");
        clearQueue.start(this::remove);
    }

    @Override
    public void putList(String key, String value) {
        Object o = LINK_MAP.get(key);
        List<String> list;
        if (o == null) {
            list = Lists.newArrayList();
            LINK_MAP.put(key, list);
        } else {
            list = (List<String>) o;
        }
        list.add(value);
    }

    /**
     * 删除
     *
     * @param key
     * @param value
     */
    @Override
    public void removeList(String key, String value) {
        List<String> list = this.getList(key);
        if (CollectionUtils.isNotEmpty(list)) {
            list.remove(value);
        }
    }

    @Override
    public List<String> getList(String key) {
        Object o = LINK_MAP.get(key);
        List<String> list = null;
        if (o != null) {
            list = (List<String>) o;
        }
        return list;
    }

    /**
     * 清空所有缓存
     *
     * @param likeKey
     */
    @Override
    public void clear(String likeKey) {
        Collection<String> keys = keys(likeKey);
        if (CollectionUtil.isNotEmpty(keys)){
            for (String key : keys) {
                LINK_MAP.remove(key);
            }
        }
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return BatConfigUtil.getInt
     */
    @Override
    public Serializable get(String key) {
        Object o = LINK_MAP.get(key);
        if (o != null) {
            return JSONObject.parseObject(o.toString(), Serializable.class);
        }
        return null;
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @param clazz
     * @return
     * @author: wchao
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object o = LINK_MAP.get(key);
        if (o != null) {
            return JSONObject.parseObject(o.toString(), clazz);
        }
        return null;
    }

    /**
     * 获取所有的相似key
     *
     * @param likeKey
     * @return
     */
    @Override
    public Collection<String> keys(String likeKey) {
        List<String> keyList = Lists.newArrayList();
        Set<String> keys = LINK_MAP.keySet();
        if (CollectionUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                if (key.contains(likeKey)) {
                    keyList.add(key);
                }
            }
        }
        return keyList;
    }

    /**
     * 将key value保存到缓存中
     *
     * @param key
     * @param value
     * @param timeout 过期时间（秒）
     */
    @Override
    public void put(String key, Serializable value, int timeout) {
        put(key, JSONObject.toJSONString(value));
        clearQueue.put(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void put(String key, Serializable value) {
        put(key, JSONObject.toJSONString(value));
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void put(String key, String value) {
        LINK_MAP.put(key, value);
    }

    /**
     * 删除一个key
     *
     * @param key
     * @return BatConfigUtil.getInt
     */
    @Override
    public void remove(String key) {
        LINK_MAP.remove(key);
    }
}
