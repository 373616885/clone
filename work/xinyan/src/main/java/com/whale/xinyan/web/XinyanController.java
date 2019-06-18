package com.whale.xinyan.web;

import com.whale.xinyan.dto.*;
import com.whale.xinyan.service.XinyanOverdueService;
import com.whale.xinyan.service.XinyanProbeService;
import com.whale.xinyan.service.XinyanRadarService;
import com.whale.xinyan.service.XinyanTotaldebtService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author qinjp
 * @date 2019-06-17
 **/
@Slf4j
@RestController
public class XinyanController {

    @Autowired
    private XinyanOverdueService xinyanOverdueService;

    @Autowired
    private XinyanProbeService xinyanProbeService;

    @Autowired
    private XinyanRadarService xinyanRadarService;

    @Autowired
    private XinyanTotaldebtService xinyanTotaldebtService;


    /**
     * 新颜智能评估逾期档案
     */
    @SneakyThrows(Exception.class)
    @PostMapping("/xinyan/overdue")
    public XinyanResp<OverdueData> overdue(@Valid @RequestBody BussinessReq bussinessReq) {
        return xinyanOverdueService.overdue(bussinessReq.getIdNo(),
                bussinessReq.getIdName(),
                bussinessReq.getUid(),
                bussinessReq.getLoanId());
    }


    /**
     * 新颜探针A 查询
     */
    @SneakyThrows(Exception.class)
    @PostMapping("/xinyan/probe")
    public XinyanResp<ProbeData> probe(@Valid @RequestBody BussinessReq bussinessReq) {
        return xinyanProbeService.probe(bussinessReq.getIdNo(),
                bussinessReq.getIdName(),
                bussinessReq.getUid(),
                bussinessReq.getLoanId());
    }


    /**
     * 新颜全景雷达报告
     */
    @SneakyThrows(Exception.class)
    @PostMapping("/xinyan/radar")
    public XinyanResp<RadarData> radar(@Valid @RequestBody BussinessReq bussinessReq) {
        return xinyanRadarService.radar(bussinessReq.getIdNo(),
                bussinessReq.getIdName(),
                bussinessReq.getUid(),
                bussinessReq.getLoanId());
    }


    /**
     * 新颜-共债
     */
    @SneakyThrows(Exception.class)
    @PostMapping("/xinyan/totaldebt")
    public XinyanResp<TotaldebtData> totaldebt(@Valid @RequestBody BussinessReq bussinessReq) {
        return xinyanTotaldebtService.totaldebt(bussinessReq.getIdNo(),
                bussinessReq.getIdName(),
                bussinessReq.getUid(),
                bussinessReq.getLoanId());
    }
}