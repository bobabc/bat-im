package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import cn.batim.server.common.model.BatSession;
import cn.hutool.core.collection.CollectionUtil;
import io.netty.channel.Channel;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户Session
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 16:03
 */
public class BatSessionKit {
    private static Map<String, List<BatSession>> SESSION_MAP = new ConcurrentHashMap<>(10);

    public static synchronized void save(BatSession batSession) {
        Channel channel = batSession.getChannel();
        String userId = batSession.getUserId();
        String id = BatChannelKit.getId(channel);
        BatSession session = getByChannelId(userId, id);
        if (session == null) {
            list(userId).add(batSession);
        }
    }

    public static List<BatSession> list(String userId) {
        return SESSION_MAP.computeIfAbsent(userId, k -> new ArrayList<>());
    }

    public static List<BatSession> list(String userId, BatConst.Client client) {
        List<BatSession> sessionList = list(userId);
        List<BatSession> list = Lists.newArrayList();
        for (BatSession batSession : sessionList) {
            if (batSession.getClient() == client){
                list.add(batSession);
            }
        }
        return list;
    }

    public static BatSession getByChannelId(String userId, String channelId) {
        List<BatSession> list = list(userId);
        if (CollectionUtil.isNotEmpty(list)) {
            for (BatSession userSession : list) {
                if (userSession.getId().equals(channelId)) {
                    return userSession;
                }
            }
        }
        return null;
    }

    public static BatSession getByChannelId(String userId, Channel channel) {
        return getByChannelId(userId, BatChannelKit.getId(channel));
    }

    public static BatSession getByChannelId(Channel channel) {
        return getByChannelId(BatChannelKit.getId(channel));
    }

    public static BatSession getByChannelId(String channelId) {
        if (StringUtils.isEmpty(channelId)) {
            return null;
        }
        List<BatSession> list = all();
        if (CollectionUtil.isNotEmpty(list)) {
            for (BatSession userSession : list) {
                if (userSession.getId().equals(channelId)) {
                    return userSession;
                }
            }
        }
        return null;
    }

    /**
     * 全部客户端
     *
     * @return
     */
    public static List<BatSession> all() {
        List<List<BatSession>> collect = new ArrayList<>(SESSION_MAP.values());
        List<BatSession> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)) {
            for (List<BatSession> userSessions : collect) {
                list.addAll(userSessions);
            }
        }
        return list;
    }

    public static synchronized void remove(String userId, String channelId) {
        List<BatSession> list = list(userId);
        if (CollectionUtil.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                BatSession userSession = list.get(i);
                if (userSession.getId().equals(channelId)) {
                    list.remove(i);
                    break;
                }
            }
        }
    }

    public static synchronized void remove(Channel channel) {
        BatSession session = getByChannelId(channel);
        if (session != null) {
            remove(session);
        }
    }

    public static synchronized void remove(BatSession session) {
        remove(session.getUserId(), BatChannelKit.getId(session.getChannel()));
    }

    public synchronized static void close(BatSession session) {
        if (session != null && session.getChannel() != null) {
            BatChannelKit.close(session.getChannel());
        }
    }
}
