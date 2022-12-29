package cn.batim.common.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zlb
 * @version 1.0
 * @date 2022/12/26 18:43
 */
@Data
public class BatUserInfo implements Serializable {
    private String userId;
    private String name;
}
