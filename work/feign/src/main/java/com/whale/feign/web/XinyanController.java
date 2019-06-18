package com.whale.feign.web;

import com.whale.feign.bean.BussinessReq;
import com.whale.feign.bean.Result;
import com.whale.feign.service.XinyanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author qinjp
 * @date 2019-06-18
 **/
@RestController
public class XinyanController {

    @Autowired
    XinyanService xinyanService;

    @RequestMapping("/xinyan/overdue")
    public Result overdue(@Valid BussinessReq bussinessReq) throws Exception {
        Result result = xinyanService.overdue(bussinessReq);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/xinyan/probe")
    public Result probe(@Valid BussinessReq bussinessReq) throws Exception {
        Result result = xinyanService.probe(bussinessReq);
        System.out.println(result);
        return result;
    }


    @RequestMapping("/xinyan/radar")
    public Result radar(@Valid BussinessReq bussinessReq) throws Exception {
        Result result = xinyanService.radar(bussinessReq);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/xinyan/totaldebt")
    public Result totaldebt(@Valid BussinessReq bussinessReq) throws Exception {
        Result result = xinyanService.totaldebt(bussinessReq);
        System.out.println(result);
        return result;
    }

}
