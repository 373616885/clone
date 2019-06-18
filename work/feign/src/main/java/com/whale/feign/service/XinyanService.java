package com.whale.feign.service;

import com.whale.feign.bean.BussinessReq;
import com.whale.feign.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author qinjp
 * @date 2019-06-18
 **/
@FeignClient(value = "data-xinyan", qualifier = "xinyanService")
public interface XinyanService {

    @RequestMapping(value = "/xinyan/overdue", method = RequestMethod.POST)
    Result overdue(BussinessReq req);

    @RequestMapping(value = "/xinyan/probe", method = RequestMethod.POST)
    Result probe(BussinessReq req);

    @RequestMapping(value = "/xinyan/radar", method = RequestMethod.POST)
    Result radar(BussinessReq req);

    @RequestMapping(value = "/xinyan/totaldebt", method = RequestMethod.POST)
    Result totaldebt(BussinessReq req);

}

