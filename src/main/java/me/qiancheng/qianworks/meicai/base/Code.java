package me.qiancheng.qianworks.meicai.base;

public enum Code {
    SUCCESS("ok", 1),
    FAILURE("error", 0);

    private String msg;
    private int code;

    Code(String msg, int code) {
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