package com.whale.xinyan.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.whale.common.util.HttpUtils;
import com.whale.common.util.JsonUtil;
import com.whale.xinyan.bean.ThirdLogModeEnum;
import com.whale.xinyan.bean.XinYanEnum;
import com.whale.xinyan.dto.*;
import com.whale.xinyan.entity.LoanProbe;
import com.whale.xinyan.entity.LoanProbeDetail;
import com.whale.xinyan.jpa.LoanProbeDetailRepository;
import com.whale.xinyan.rsa.RsaCodingUtil;
import com.whale.xinyan.rsa.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 新颜探针A  数据接口
 *
 * @author dengjihai
 * @create 2019-03-06
 **/
@Slf4j
@Service
public class XinyanProbeService  {

    @Autowired
    private LoanProbeDetailRepository loanProbeDetailRepository;

    @Autowired
    private XinyanBaseService xinyanBaseService;

    private final TypeReference<XinyanResp<ProbeData>> jsonType = new TypeReference<XinyanResp<ProbeData>>() {};


    /**
     * 新颜探针A 查询
     */
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public XinyanResp<ProbeData> probe(final String idNo,
                            final String idName,
                            final Integer uid,
                            final Integer loanId) {

        // 业务请求参数xinyanBusinessReq加密后变成  请求参数XinyanReq类的 data_content 字段
        XinyanBusinessReq xinyanBusinessReq = xinyanBaseService.buildXinyanBusinessReq()
                .setId_no(SecurityUtil.MD5(idNo))
                .setId_name(SecurityUtil.MD5(idName))
                .setVersions(XinYanEnum.PROBE_A.getVersions());

        // base64 编码
        String base64str = SecurityUtil.Base64Encode(JsonUtil.beanToJson(xinyanBusinessReq));

        // 参数加密
        String dataContent = RsaCodingUtil.encryptByPrivateKey(base64str, xinyanBaseService.getPrivateKey());

        // 请求参数
        XinyanReq xinyanReq = xinyanBaseService.buildXinyanReq()
                .setData_content(dataContent);

        String reqStr = JsonUtil.beanToJson(xinyanReq);

        log.info("新颜-探针A请求: uid:{}  loanId:{}  idNo:{}  idName:{}", uid, loanId, idNo, idName);
        log.info("新颜-探针A请求:{}", reqStr);

        // 接口URL
        String apiUrl = XinYanEnum.PROBE_A.getApiUrl(xinyanBaseService.getHost());

        // 远程调用新颜-探针A接口
        String respStr = HttpUtils.doPostSSL(apiUrl, null, JsonUtil.jsonToMap(reqStr));

        // respStr = "{    \"success\": true,    \"data\": {        \"fee\": \"Y\",        \"code\": \"0\",        \"desc\": \"查询成功\",        \"trans_id\": \"req2019011111071431229908\",        \"trade_no\": \"20190111110930136000005183116997\",        \"id_no\": \"522627199205888888\",        \"id_name\": \"张三\",        \"versions\": \"1.4.0\",        \"result_detail\": {            \"result_code\": \"1\",            \"currently_overdue\": \"3\",            \"max_overdue_amt\": \"2000-3000\",            \"acc_sleep\": \"30\",            \"currently_performance\": \"27\",            \"max_overdue_days\": \"16-30\",            \"latest_overdue_time\": \"2018-07\",            \"acc_exc\": \"0\"        }    },    \"errorCode\": null,    \"errorMsg\": null}";

        // respStr = "{ \"success\": false, \"data\":null, \"errorCode\":\"S1000\", \"errorMsg\":\"请求参数有误\" }";

        log.info("新颜-探针A返回:{}", respStr);

        XinyanResp<ProbeData> xinyanResp = JsonUtil.jsonToBean(respStr, jsonType);

        if (Objects.isNull(xinyanResp)) {
            return null;
        }

        ProbeData data = xinyanResp.getData();

        // 保存第三方日记表信息
        xinyanBaseService.saveThirdLog(uid, loanId,
                ThirdLogModeEnum.THIRD_XIN_YAN.getCode(),
                ThirdLogModeEnum.THIRD_XIN_YAN.getName(),
                apiUrl,
                reqStr,
                data == null ? xinyanBusinessReq.getTrans_id() : data.getTrans_id(),
                data == null ? null : data.getTrade_no(),
                Strings.emptyToNull(xinyanResp.getErrorCode()),
                xinyanResp.getErrorMsg());


        // ========== 保存探针A返回信息 Start ========== //
        if (xinyanResp.isSuccess() && data != null) {
            // 保存 lwl_loan_probe表
            LoanProbe saveProbe = xinyanBaseService.saveProbe(data, idNo, idName, uid, loanId, XinYanEnum.PROBE_A);
            // 判断之前是否保存过
            if (Objects.nonNull(saveProbe) && data.getResult_detail() != null) {
                // 保存逾期总览明细
                saveProbeDetail(data.getResult_detail(), saveProbe.getLprobeId());
            }
        }
        // ========== 保存探针A返回信息 End ========== //

        return xinyanResp;
    }

    /**
     * 保存逾期总览明细
     */
    private void saveProbeDetail(final ProbeResultDetail resultDetail, final Integer lprobeId) {
        LoanProbeDetail loanProbeDetail = new LoanProbeDetail();
        loanProbeDetail.setLprobeId(lprobeId);
        loanProbeDetail.setResultCode(resultDetail.getResult_code());
        loanProbeDetail.setAccExc(resultDetail.getAcc_exc());
        loanProbeDetail.setAccSleep(resultDetail.getAcc_sleep());
        loanProbeDetail.setCurrentlyOverdue(resultDetail.getCurrently_overdue());
        loanProbeDetail.setCurrentlyPerformance(resultDetail.getCurrently_performance());
        loanProbeDetail.setLatestOverdueTime(resultDetail.getLatest_overdue_time());
        loanProbeDetail.setMaxOverdueAmt(resultDetail.getMax_overdue_amt());
        loanProbeDetail.setMaxOverdueDays(resultDetail.getMax_overdue_days());
        loanProbeDetailRepository.save(loanProbeDetail);
    }


}
