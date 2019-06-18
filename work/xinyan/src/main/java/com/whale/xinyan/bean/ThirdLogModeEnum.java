package com.whale.xinyan.bean;


/**
 * 第三方日志模块
 */
public enum ThirdLogModeEnum {

    /**
     * 友盟
     */
    THIRD_XIN_YAN(5, "新颜探针");

    private Integer code;
    private String name;

    ThirdLogModeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
