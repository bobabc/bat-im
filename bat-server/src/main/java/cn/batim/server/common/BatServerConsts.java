package cn.batim.server.common;

import cn.batim.common.model.user.BatChannelUser;
import io.netty.util.AttributeKey;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 14:50
 */
public interface BatServerConsts {
    AttributeKey<BatChannelUser> BAT_SESSION = AttributeKey.valueOf("user");
}
