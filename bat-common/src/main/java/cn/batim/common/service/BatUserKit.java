package cn.batim.common.service;

import cn.batim.common.cache.BatCache;
import cn.batim.common.cache.local.LocalCache;
import cn.batim.common.config.BatConfig;
import cn.batim.common.consts.BatConst;
import cn.batim.common.consts.key.BatUserKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 19:00
 */
public class BatUserKit implements BatConst {
    private static Map<String, BatUserKey> KEY_MAP = new HashMap<>(10);
    private static BatCache batCache;

    /**
     * 用户key
     *
     * @param userId
     * @return
     */
    protected static BatUserKey getKey(String userId) {
        BatUserKey userInfoKey = KEY_MAP.get(userId);
        if (userInfoKey == null) {
            userInfoKey = new BatUserKey(userId);
            KEY_MAP.put(userId, userInfoKey);
        }
        return userInfoKey;
    }

    /**
     * 获取缓存实例
     *
     * @return
     */
    protected static BatCache getCache() {
        return BatConfig.me().getBatCache();
    }
}
