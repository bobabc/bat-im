package cn.batim.common.exception;


import cn.batim.common.consts.GlobalCode;

/**
 * 异常
 *
 * @author zhanglibo
 */
public class BatException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int errCode;
    private String errMsg;

    public int getErrCode() {
        return this.errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public BatException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BatException(GlobalCode globalCode, String errMsg) {
        this.errCode = globalCode.getCode();
        this.errMsg = errMsg;
    }

    public BatException(GlobalCode code) {
        this.errCode = code.getCode();
        this.errMsg = code.getMsg();
    }
}
