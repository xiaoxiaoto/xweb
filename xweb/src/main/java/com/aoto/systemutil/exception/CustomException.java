package com.aoto.systemutil.exception;

/**
 * 异常类
 */

public class CustomException extends  RuntimeException{
	private static final long serialVersionUID = 1L;
	private Integer ecode;
    private  String msg;

    public CustomException() {
    }

    public CustomException(Integer ecode, String msg) {
        super(msg);
        this.ecode = ecode;
    }

    public Integer getEcode() {
        return ecode;
    }

    public void setEcode(Integer ecode) {
        this.ecode = ecode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return super.toString()+",ResultException{" +
                "ecode=" + ecode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
