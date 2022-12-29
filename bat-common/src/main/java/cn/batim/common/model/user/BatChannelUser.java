package cn.batim.common.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 存储在Channel
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 14:55
 */
@Data
public class BatChannelUser implements Serializable {
    private String userId;
    private String token;

    public BatChannelUser(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public BatChannelUser(String userId) {
        this.userId = userId;
    }
}
