package cn.batim.common.service;

import cn.batim.common.consts.key.BatUserKey;
import cn.batim.common.model.user.BatUserInfo;

/**
 * 用户信息
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 18:41
 */
public class BatUserInfoKit extends BatUserKit {

    public static synchronized void save(BatUserInfo userInfo) {
        BatUserKey userInfoKey = getKey(userInfo.getUserId());
        getCache().put(userInfoKey.getInfo(), userInfo);
    }

    public static BatUserInfo get(String userId) {
        BatUserKey userInfoKey = getKey(userId);
        return getCache().get(userInfoKey.getInfo(), BatUserInfo.class);
    }

    public static synchronized void remove(String userId) {
        BatUserKey userInfoKey = getKey(userId);
        getCache().remove(userInfoKey.getInfo());
    }
}
