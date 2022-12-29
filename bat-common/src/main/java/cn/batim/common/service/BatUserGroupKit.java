package cn.batim.common.service;

import cn.batim.common.consts.key.BatUserKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户群组
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 19:11
 */
@Slf4j
public class BatUserGroupKit extends BatUserKit {

    /**
     * 加入群组
     *
     * @param userId
     * @param groupId
     */
    public static synchronized void join(String userId, String groupId) {
        String key = getKey(userId).getJoinedGroup();
        getCache().putList(key, groupId);
    }

    /**
     * 离开群组
     *
     * @param userId
     * @param groupId
     */
    public static synchronized void leave(String userId, String groupId) {
        String key = getKey(userId).getJoinedGroup();
        getCache().putList(key, groupId);
    }

    /**
     * 用户已加入群组列表
     *
     * @param userId
     * @return
     */
    public static List<String> getList(String userId) {
        String key = getKey(userId).getJoinedGroup();
        return getCache().getList(key);
    }

    /**
     * 获取已加入群组用户列表
     *
     * @param groupId
     * @return
     */
    public static Set<String> getJoinedUserList(String groupId) {
        Set<String> result = new HashSet<>(50);
        // 如果查询慢，后期可以添加本地缓存
        Collection<String> keys = getCache().keys(":join");
        if (CollectionUtils.isNotEmpty(keys)) {
            for (String key : keys) {
                List<String> list = getCache().getList(key);
                if (CollectionUtils.isNotEmpty(list) && list.contains(groupId)) {
                    String userId = BatUserKey.getUserIdByKey(key);
                    if (StringUtils.isNotEmpty(userId)) {
                        result.add(userId);
                    }
                }
            }
        }
        return result;
    }
}
