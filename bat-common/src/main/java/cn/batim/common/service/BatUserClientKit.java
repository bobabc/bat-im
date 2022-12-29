package cn.batim.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 用户终端
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 14:15
 */
@Slf4j
public class BatUserClientKit extends BatUserKit {
    /**
     * 保存终端
     *
     * @param userId
     * @param client
     */
    public static synchronized void save(String userId, Client client) {
        getCache().putList(getKey(userId).getClient(), client.getName());
    }

    /**
     * 删除终端
     *
     * @param userId
     * @param client
     */
    public static synchronized void remove(String userId, Client client) {
        getCache().removeList(getKey(userId).getClient(), client.getName());
    }

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    public static boolean isOnLine(String userId) {
        String key = getKey(userId).getClient();
        List<String> list = getCache().getList(key);
        return CollectionUtils.isNotEmpty(list);
    }

    /**
     * 终端是否在线
     *
     * @param userId
     * @param client
     * @return
     */
    public static boolean isOnLine(String userId, Client client) {
        String key = getKey(userId).getClient();
        List<String> list = getCache().getList(key);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.contains(client.getName());
        } else {
            return false;
        }
    }

    /**
     * 查询全部终端
     *
     * @param userId
     * @return
     */
    public static List<Client> getClient(String userId) {
        String key = getKey(userId).getClient();
        List<Client> clients = Lists.newArrayList();
        List<String> list = getCache().getList(key);
        if (CollectionUtils.isNotEmpty(list)) {
            for (String clientName : list) {
                Client client = Client.of(clientName);
                if (client != null) {
                    clients.add(client);
                }
            }
        }
        return clients;
    }
}
