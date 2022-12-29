package cn.batim.common.service;

import cn.batim.common.model.group.BatGroupInfo;
import cn.batim.common.utils.BatObjectUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 群组
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/27 16:35
 */
@Slf4j
public class BatGroupKit extends BatUserKit {
    /**
     * 创建群组
     *
     * @param userId
     * @param batGroupInfo
     */
    public static synchronized void create(String userId, BatGroupInfo batGroupInfo) {
        String key = getKey(userId).getSelfGroup();
        batGroupInfo.setId(createGroupId());
        batGroupInfo.setCreator(userId);
        getCache().putList(key, JSONObject.toJSONString(batGroupInfo));
        log.info("创建群组成功:{}", batGroupInfo);

    }

    public static BatGroupInfo get(String userId, String groupId) {
        List<BatGroupInfo> list = list(userId);
        if (CollectionUtil.isNotEmpty(list)) {
            for (BatGroupInfo batGroupInfo : list) {
                if (batGroupInfo.getId().equals(groupId)) {
                    return batGroupInfo;
                }
            }
        }
        return null;
    }

    /**
     * 用户创建的群组列表
     *
     * @param userId
     * @return
     */
    public static List<BatGroupInfo> list(String userId) {
        String key = getKey(userId).getSelfGroup();
        List<String> list = getCache().getList(key);
        return BatObjectUtil.jsonConvertObject(list, BatGroupInfo.class);
    }

    /**
     * 全部群组列表
     *
     * @return
     */
    public static List<BatGroupInfo> getAllGroup() {
        Collection<String> keys = getCache().keys(":group");
        List<BatGroupInfo> result = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                List<String> list = getCache().getList(key);
                if (CollectionUtil.isNotEmpty(list)) {
                    List<BatGroupInfo> batGroupInfos = BatObjectUtil.jsonConvertObject(list, BatGroupInfo.class);
                    result.addAll(batGroupInfos);
                }
            }
        }
        return result;
    }

    /**
     * 删除
     *
     * @param userId
     * @param groupId
     */
    public static synchronized BatGroupInfo remove(String userId, String groupId) {
        String key = getKey(userId).getSelfGroup();
        BatGroupInfo batGroupInfo = get(userId, groupId);
        if (batGroupInfo != null) {
            getCache().removeList(key, groupId);
        }
        return batGroupInfo;
    }

    /**
     * 创建群组ID
     *
     * @return
     */
    private static String createGroupId() {
        return RandomUtil.simpleUUID();
    }
}
