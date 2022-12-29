package cn.batim.common.consts.key;

import cn.batim.common.consts.BatConst;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 18:49
 */
public class BatUserKey implements BatConst {
    private String id;

    public BatUserKey(String userId) {
        this.id = userId;
    }

    /**
     * 用户ID
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 用户父Key
     *
     * @return
     */
    public String getKey() {
        return CacheKey.USER + id;
    }

    /**
     * 用户信息Key
     *
     * @return
     */
    public String getInfo() {
        return getKey().concat(":").concat("info");
    }

    /**
     * 用户创建的群组Key
     *
     * @return
     */
    public String getSelfGroup() {
        return getKey().concat(":").concat("group");
    }

    /**
     * 用户已加入群组Key
     *
     * @return
     */
    public String getJoinedGroup() {
        return getKey().concat(":").concat("join");
    }

    /**
     * 客户端
     *
     * @return
     */
    public String getClient() {
        return getKey().concat(":").concat("client");
    }

    /**
     * 通过Key获取UserId
     *
     * @param key 示例：
     *            <ul>
     *                     <li>bat:user:xxxx:join</li>
     *                     <li>bat:user:xxxx:client</li>
     *                     <li>bat:user:xxxx:group</li>
     *                     <li>bat:user:xxxx:info</li>
     *            </ul>
     * @return
     */
    public static String getUserIdByKey(String key) {
        String[] split = key.split(":");
        if (split.length == Number.FOUR) {
            return split[2];
        }
        return null;
    }
}
