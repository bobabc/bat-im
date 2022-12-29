package cn.batim.common.consts;

/**
 * 全局响应码
 */
public enum GlobalCode {
    /**
     * 操作成功
     **/
    SUCCESS(1000, "操作成功"),
    /**
     * Token失效
     **/
    AUTH_FAIL(2000, "Token失效"),
    /**
     * 参数为空
     **/
    PARAM_3000(3000, "参数为空"),
    /**
     * 参数错误
     **/
    PARAM_3001(3001, "参数错误"),

    /**
     * 数据不存在
     **/
    DATA_4000(4000, "数据不存在"),
    /**
     * 数据已存在
     **/
    DATA_4001(4001, "数据已存在"),
    /**
     * <pre>系统错误</pre>
     * <code> code = 5000 </code>
     */
    SYS_5000(5000, "系统错误"),
    /**
     * 无权操作
     **/
    OP_6000(6000, "无权操作");


    private int code;
    private String msg;

    private GlobalCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
