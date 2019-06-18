package com.whale.xinyan.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author qinjp
 * @date 2019-06-13
 **/
@Setter
@Getter
public class ProbeData extends XinyanBussinessResp implements Serializable {

    private ProbeResultDetail result_detail;

}
