package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import cn.batim.common.consts.GlobalCode;
import cn.batim.common.model.group.BatGroupInfo;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.common.model.user.BatUserInfo;
import cn.batim.common.service.BatClusterKit;
import cn.batim.common.service.BatGroupKit;
import cn.batim.common.service.BatUserGroupKit;
import cn.batim.common.service.BatUserInfoKit;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import cn.batim.server.listener.event.BatEventParser;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 对外公共工具
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 15:00
 */
@Slf4j
public class BatKit implements BatConst {
    /**
     * 全部终端下线
     */
    public static void allOffLine() {
        List<BatSession> all = BatSessionKit.all();
        if (CollectionUtils.isNotEmpty(all)) {
            for (BatSession batSession : all) {
                log.info("下线:{}",batSession);
                // 处理连接消息
                BatSessionMsg batSessionMsg = BatSessionMsg.getInstance(batSession.getClient(), Cmd.CLIENT_OFFLINE);
                batSessionMsg
                        .setBatSession(batSession)
                        .setClient(batSession.getClient())
                        .setMe(batSession.getUserId());
                BatEventParser.parse(null, batSessionMsg);
            }
        }
    }

    /**
     * 用户创建群组列表
     *
     * @param userId
     * @return
     */
    public static R<List<BatGroupInfo>> getGroupListByUserId(String userId) {
        return R.ok(BatGroupKit.list(userId));
    }

    /**
     * 全部群组列表
     *
     * @return
     */
    public static R<List<BatGroupInfo>> getGroupList() {
        return R.ok(BatGroupKit.getAllGroup());
    }

    /**
     * 群组成员
     *
     * @param groupId
     * @return
     */
    public static R<List<BatUserInfo>> getUserListByGroupId(String groupId) {
        List<BatUserInfo> userInfos = Lists.newArrayList();
        Set<String> groupUserList = BatUserGroupKit.getGroupUserList(groupId);
        if (CollectionUtils.isNotEmpty(groupUserList)) {
            for (String userId : groupUserList) {
                BatUserInfo userInfo = BatUserInfoKit.get(userId);
                if (userInfo != null) {
                    userInfos.add(userInfo);
                }
            }
        }
        return R.ok(userInfos);
    }

    /**
     * 广播
     *
     * @param msg
     * @return
     */
    public static R<String> sendPub(BatMsg msg) {
        List<BatSession> all = BatSessionKit.all();
        if (CollectionUtils.isNotEmpty(all)) {
            for (BatSession batSession : all) {
                BatChannelKit.send(batSession, msg);
            }
        }
        return R.ok();
    }

    /**
     * 发送消息 到 自己加入的全部群组
     *
     * @param msg
     */
    public static R<String> sendJoinedGroup(BatMsg msg) {
        R<String> ret = BatMsgKit.checkMsg(msg);
        if (!ret.success()) {
            return ret;
        }
        String userId = msg.getMe();
        Set<String> userList = BatUserGroupKit.getJoinedGroupUserList(userId);
        if (CollectionUtils.isNotEmpty(userList)) {
            for (String uid : userList) {
                if (!userId.equals(uid)) {
                    List<BatSession> sessionList = BatSessionKit.list(uid);
                    for (BatSession batSession : sessionList) {
                        BatChannelKit.send(batSession, msg);
                    }
                }
            }
        }
        return R.ok();
    }

    /**
     * 加入群组
     *
     * @param msg
     * @param msg
     * @return
     */
    public static R<String> joinGroup(BatMsg msg) {
        if (StringUtils.isEmpty(msg.getTo())) {
            return R.failure(GlobalCode.PARAM_3000, "群组ID不能为空");
        }
        BatUserGroupKit.join(msg.getMe(), msg.getTo());
        // 通知群组
        sendOtg(msg);
        return R.ok();
    }

    /**
     * 离开群组
     *
     * @param msg
     * @return
     */
    public static R<String> leaveGroup(BatMsg msg) {
        if (StringUtils.isEmpty(msg.getTo())) {
            return R.failure(GlobalCode.PARAM_3000, "群组ID不能为空");
        }
        BatUserGroupKit.leave(msg.getMe(), msg.getTo());
        // 通知群组
        sendOtg(msg);
        return R.ok();
    }

    /**
     * 创建群组
     *
     * @param batGroupInfo
     * @return
     */
    public static R<String> createGroup(BatGroupInfo batGroupInfo) {
        if (StringUtils.isAnyEmpty(batGroupInfo.getName(), batGroupInfo.getCreator())) {
            return R.failure(GlobalCode.PARAM_3000, "【名称|创建人】不能为空");
        }
        if (StringUtils.isEmpty(batGroupInfo.getId())) {
            batGroupInfo.setId(RandomUtil.simpleUUID());
        }
        // 创建群组
        BatGroupKit.create(batGroupInfo.getCreator(), batGroupInfo);
        // 把自己加进去
        BatUserGroupKit.join(batGroupInfo.getCreator(), batGroupInfo.getId());
        return R.ok();
    }

    /**
     * 发送群组
     *
     * @param msg
     * @return
     */
    public static R<String> sendOtg(BatMsg msg) {
        R<String> ret = BatMsgKit.checkMsg(msg);
        if (!ret.success()) {
            return ret;
        }
        String groupId = msg.getTo();
        BatGroupInfo batGroupInfo = BatGroupKit.get(msg.getMe(), msg.getTo());
        if (batGroupInfo == null) {
            return R.failure(GlobalCode.PARAM_3000, "群组不存在");
        }
        boolean exist = BatUserGroupKit.isExist(msg.getMe(), msg.getTo());
        if (!exist) {
            return R.failure(GlobalCode.PARAM_3000, "您已离开群组");
        }
        Set<String> userList = BatUserGroupKit.getGroupUserList(groupId);
        if (CollectionUtils.isNotEmpty(userList)) {
            for (String userId : userList) {
                // 转发消息
                List<BatSession> sessionList = BatSessionKit.list(userId);
                for (BatSession batSession : sessionList) {
                    BatChannelKit.send(batSession, msg);
                }
            }
        }
        // 转发集群
        if (!(msg instanceof BatClusterMsg)) {
            BatClusterMsg batClusterMsg = BatClusterMsg.getInstance(msg.getCmd());
            BeanUtil.copyProperties(msg, batClusterMsg);
            BatClusterKit.send(batClusterMsg);
        }
        return R.ok();
    }

    public static R<String> demo(BatMsg msg) {
        R<String> ret = BatMsgKit.checkMsg(msg);
        if (!ret.success()) {
            return ret;
        }
        return R.ok();
    }

    /**
     * 单聊
     *
     * @param msg
     */
    public static R<String> sendOto(BatMsg msg) {
        R<String> ret = BatMsgKit.checkMsg(msg);
        if (!ret.success()) {
            return ret;
        }
        String to = msg.getTo();
        // 转发消息
        List<BatSession> sessionList = BatSessionKit.list(to);
        for (BatSession batSession : sessionList) {
            BatChannelKit.send(batSession, msg);
        }
        // 转发集群
        if (!(msg instanceof BatClusterMsg)) {
            BatClusterMsg batClusterMsg = BatClusterMsg.getInstance(msg.getCmd());
            BeanUtil.copyProperties(msg, batClusterMsg);
            BatClusterKit.send(batClusterMsg);
        }
        return R.ok();
    }

    /**
     * 用于接收集群消息
     *
     * @param batMsg
     */
    public static R<String> onMessage(BatClusterMsg batMsg) {
        R<String> ret = BatMsgKit.checkMsg(batMsg);
        if (!ret.success()) {
            return ret;
        }
        if (!batMsg.getNodeId().equals(BAT_ID)) {
            BatEventParser.parse(null, batMsg);
        }
        return R.ok();
    }
}
