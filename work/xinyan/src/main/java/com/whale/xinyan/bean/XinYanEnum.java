package com.whale.xinyan.bean;

/**
 * @author dengjihai
 * @create 2019-03-13
 **/
public enum XinYanEnum {

    /**
     * 新颜探针A接口
     */
    PROBE_A("新颜探针A接口", "/product/negative/v4/black", "1.4.0", 1),

    /**
     * 新颜智能评估逾期接口
     */
    OVERDUE_RECORD("新颜智能评估逾期接口", "/product/archive/v3/overdue", "1.3.0", 2),

    /**
     * 新颜共债档案接口
     */
    TOTALDEBT_REPORT("新颜共债档案接口", "/product/archive/v1/totaldebt", "1.1.0", 3),

    /**
     * 新颜全景雷达报告接口
     */
    RADAR_REPORT("新颜全景雷达报告接口", "/product/radar/v3/report", "1.3.0", 4);

    /**
     * 接口请求名称
     */
    private final String name;

    /**
     * 接口请求方法
     */
    private final String method;

    /**
     * 接口请求版本号
     */
    private final String versions;

    /**
     * 保存lwl_loan_probe表的类型
     */
    private final Integer apiType;


    XinYanEnum(String name, String method, String versions, Integer apiType) {
        this.name = name;
        this.method = method;
        this.versions = versions;
        this.apiType = apiType;
    }

    public String getName() {
        return this.name;
    }

    public String getVersions() {
        return this.versions;
    }

    public Integer getApiType() {
        return apiType;
    }

    public String getApiUrl(String host) {
        return host + this.method;
    }


}
