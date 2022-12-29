package cn.batim.common.service;

import cn.batim.common.model.user.BatChannelUser;

/**
 * 登录业务
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/28 15:55
 */
public interface BatLoginService {
    /**
     * token登录
     *
     * @param token
     * @return
     */
    BatChannelUser login(String token);
}
