package com.whale.feign.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2019-03-06
 **/
@Data
public class BussinessReq implements Serializable {

    @NotEmpty(message = "身份证号不能为空")
    private String idNo;

    @NotEmpty(message = "姓名不能为空")
    private String idName;

    @NotNull(message = "用户ID")
    private Integer uid;

    @NotNull(message = "姓名不能为空")
    private Integer loanId;
}
