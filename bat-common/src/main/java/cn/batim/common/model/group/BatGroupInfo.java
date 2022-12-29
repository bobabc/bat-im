package cn.batim.common.model.group;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 群组
 *
 * @author zlb
 * @version 1.0
 * @date 2022/12/27 16:36
 */
@ToString
@Data
public class BatGroupInfo implements Serializable {
    private String id;
    private String name;
    private String img;
    private String creator;

    public BatGroupInfo(String name) {
        this.name = name;
    }

    public BatGroupInfo(String id, String name, String img, String creator) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.creator = creator;
    }
}
