package cn.batim.server.common.kit;

import cn.batim.common.consts.BatConst;
import cn.batim.common.consts.GlobalCode;
import cn.batim.common.model.msg.BatMsg;
import cn.batim.common.model.msg.impl.BatClusterMsg;
import cn.batim.common.model.msg.impl.BatHttpProxyMsg;
import cn.batim.common.model.reponse.R;
import cn.batim.server.common.model.BatSession;
import cn.batim.server.common.model.msg.BatSessionMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * 消息处理
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/29 16:24
 */
public class BatMsgKit {
    /**
     * 消息校验
     *
     * @param batMsg
     * @return
     */
    public static R<String> checkMsg(BatMsg batMsg) {
        BatConst.Cmd cmd = batMsg.getCmd();
        if (cmd == null) {
            return R.failure(GlobalCode.PARAM_3000, "命令错误");
        }
        if (batMsg instanceof BatClusterMsg) {
            BatClusterMsg batClusterMsg = (BatClusterMsg) batMsg;
            String nodeId = batClusterMsg.getNodeId();
            if (StringUtils.isEmpty(nodeId)) {
                return R.failure(GlobalCode.PARAM_3000, "服务ID不能为空");
            }
        } else if (batMsg instanceof BatHttpProxyMsg) {
            BatHttpProxyMsg batClusterMsg = (BatHttpProxyMsg) batMsg;
            String token = batClusterMsg.getToken();
            if (StringUtils.isEmpty(token)) {
                return R.failure(GlobalCode.PARAM_3000, "Token不能为空");
            }
        } else if (batMsg instanceof BatSessionMsg) {
            BatSessionMsg batClusterMsg = (BatSessionMsg) batMsg;
            BatSession batSession = batClusterMsg.getBatSession();
            BatConst.Client client = batClusterMsg.getClient();
            if (batSession == null || client == null) {
                return R.failure(GlobalCode.PARAM_3000, "用户Session和终端类型不能为空");
            }
        } else {
            switch (cmd) {
                case OTO:
                    if (StringUtils.isAnyEmpty(batMsg.getTo(), batMsg.getMe())) {
                        return R.failure(GlobalCode.PARAM_3000, "【发送人|收件人】不能为空");
                    }
                    break;
                case OTG:
                    if (StringUtils.isAnyEmpty(batMsg.getTo(), batMsg.getMe())) {
                        return R.failure(GlobalCode.PARAM_3000, "【发送人|群组ID】不能为空");
                    }
                    break;
                case PUB:
                    if (StringUtils.isAnyEmpty(batMsg.getBody())) {
                        return R.failure(GlobalCode.PARAM_3000, "消息体不能为空");
                    }
                    break;
                default:
            }
        }
        return R.ok();
    }
}
