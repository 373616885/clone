package com.whale.xinyan.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.whale.common.util.HttpUtils;
import com.whale.common.util.JsonUtil;
import com.whale.xinyan.assembler.RadarApplyAssembler;
import com.whale.xinyan.assembler.RadarBehaviorAssembler;
import com.whale.xinyan.assembler.RadarCurrentAssembler;
import com.whale.xinyan.bean.ThirdLogModeEnum;
import com.whale.xinyan.bean.XinYanEnum;
import com.whale.xinyan.dto.*;
import com.whale.xinyan.entity.LoanProbe;
import com.whale.xinyan.entity.LwlLoanRadarApply;
import com.whale.xinyan.entity.LwlLoanRadarBehavior;
import com.whale.xinyan.entity.LwlLoanRadarCurrent;
import com.whale.xinyan.jpa.LwlLoanRadarApplyRepository;
import com.whale.xinyan.jpa.LwlLoanRadarBehaviorRepository;
import com.whale.xinyan.jpa.LwlLoanRadarCurrentRepository;
import com.whale.xinyan.rsa.RsaCodingUtil;
import com.whale.xinyan.rsa.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 新颜全景雷达报告  数据接口
 *
 * @author guanxiaoyong
 * @create 2019-06-06
 **/
@Slf4j
@Service
public class XinyanRadarService  {

    @Autowired
    private XinyanBaseService xinyanBaseService;

    @Autowired
    private LwlLoanRadarApplyRepository loanRadarApplyRepository;

    @Autowired
    private LwlLoanRadarBehaviorRepository loanRadarBehaviorRepository;

    @Autowired
    private LwlLoanRadarCurrentRepository loanRadarCurrentRepository;

    private final TypeReference<XinyanResp<RadarData>> jsonType = new TypeReference<XinyanResp<RadarData>>() {};


    /**
     * 新颜全景雷达报告
     */
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public XinyanResp<RadarData> radar(final String idNo,
                                       final String idName,
                                       final Integer uid,
                                       final Integer loanId) {

        // 业务请求参数xinyanBusinessReq加密后变成  请求参数XinyanReq类的 data_content 字段
        XinyanBusinessReq xinyanBusinessReq = xinyanBaseService.buildXinyanBusinessReq()
                .setId_no(SecurityUtil.MD5(idNo))
                .setId_name(SecurityUtil.MD5(idName))
                .setVersions(XinYanEnum.RADAR_REPORT.getVersions());

        // base64 编码
        String base64str = SecurityUtil.Base64Encode(JsonUtil.beanToJson(xinyanBusinessReq));

        // 参数加密
        String dataContent = RsaCodingUtil.encryptByPrivateKey(base64str, xinyanBaseService.getPrivateKey());

        // 请求参数
        XinyanReq xinyanReq = xinyanBaseService.buildXinyanReq()
                .setData_content(dataContent);

        String reqStr = JsonUtil.beanToJson(xinyanReq);

        log.info("新颜-全景-雷达请求: uid:{}  loanId:{}  idNo:{}  idName:{}", uid, loanId, idNo, idName);
        log.info("新颜-全景-雷达请求:{}", reqStr);

        // 接口URL
        String apiUrl = XinYanEnum.RADAR_REPORT.getApiUrl(xinyanBaseService.getHost());

        // 远程调用新颜-全景-雷达接口
        String respStr = HttpUtils.doPostSSL(apiUrl, null, JsonUtil.jsonToMap(reqStr));

        // respStr = "{\"success\":true,\"data\":{\"code\":\"0\",\"desc\":\"查询成功\",\"trans_id\":\"d31b818aacaf4cf99ea03ca47aa222d3\",\"trade_no\":\"201704011507240100057329\",\"fee\":\"Y\",\"id_no\":\"0783231bcc39f4957e99907e02ae401c\",\"id_name\":\"dd67a5943781369ddd7c594e231e9e70 \",\"versions\":\"1.0.0\",\"result_detail\":{\"apply_report_detail\":{\"apply_score\":\"189\",\"apply_credibility\":\"84\",\"query_org_count\":\"7\",\"query_finance_count\":\"2\",\"query_cash_count\":\"2\",\"query_sum_count\":\"13\",\"latest_query_time\":\"2017-09-03\",\"latest_one_month\":\"1\",\"latest_three_month\":\"5\",\"latest_six_month\":\"12\"},\"behavior_report_detail\":{\"loans_score\":\"199\",\"loans_credibility\":\"90\",\"loans_count\":\"300\",\"loans_settle_count\":\"280\",\"loans_overdue_count\":\"20\",\"loans_org_count\":\"5\",\"consfin_org_count\":\"3\",\"loans_cash_count\":\"2\",\"latest_one_month\":\"3\",\"latest_three_month\":\"20\",\"latest_six_month\":\"23\",\"history_suc_fee\":\"30\",\"history_fail_fee\":\"25\",\"latest_one_month_suc\":\"5\",\"latest_one_month_fail\":\"20\",\"loans_long_time\":\"130\",\"loans_latest_time\":\"2017-09-16\"},\"current_report_detail\":{\"loans_credit_limit\":\"1400\",\"loans_credibility\":\"80\",\"loans_org_count\":\"7\",\"loans_product_count\":\"8\",\"loans_max_limit\":\"2000\",\"loans_avg_limit\":\"1000\",\"consfin_credit_limit\":\"1500\",\"consfin_credibility\":\"90\",\"consfin_org_count\":\"8\",\"consfin_product_count\":\"5\",\"consfin_max_limit\":\"5000\",\"consfin_avg_limit\":\"3000\"}}},\"errorCde\":null,\"errorMsg\":null}";

        // respStr = "{ \"success\": false, \"data\":null, \"errorCode\":\"S1000\", \"errorMsg\":\"请求参数有误\" }";

        log.info("新颜-全景-雷达返回:{}", respStr);

        XinyanResp<RadarData> xinyanResp = JsonUtil.jsonToBean(respStr, jsonType);

        if (Objects.isNull(xinyanResp)) {
            return null;
        }

        RadarData data = xinyanResp.getData();

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


        // ========== 保存全景雷达报告详情 Start ========== //
        if (xinyanResp.isSuccess() && data != null) {
            // 保存 lwl_loan_probe表
            LoanProbe saveProbe = xinyanBaseService.saveProbe(data, idNo, idName, uid, loanId, XinYanEnum.RADAR_REPORT);
            // 判断之前是否保存过
            if (Objects.nonNull(saveProbe) && data.getResult_detail() != null) {
                // 保存全景雷达报告详情
                saveRadarData(data.getResult_detail(), uid, loanId);
            }
        }
        // ========== 保存全景雷达报告详情 End ========== //

        return xinyanResp;
    }

    /**
     * 保存全景雷达报告详情数据
     */
    private void saveRadarData(final RadarResultDetail resultDetail,
                               final Integer uid,
                               final Integer loanId) {

        //保存申请雷达报告详情
        LoanRadarApplyDto apply_report_detail = resultDetail.getApply_report_detail();
        saveRadarApplyData(apply_report_detail, uid, loanId);

        //保存行为雷达报告详情
        LoanRadarBehaviorDto behavior_report_detail = resultDetail.getBehavior_report_detail();
        saveRadarBehaviorData(behavior_report_detail, uid, loanId);

        //保存信用现状报告详情
        LoanRadarCurrentDto current_report_detail = resultDetail.getCurrent_report_detail();
        saveRadarCurrentData(current_report_detail, uid, loanId);
    }

    /**
     * 保存申请雷达报告详情
     */
    private void saveRadarApplyData(final LoanRadarApplyDto apply_report_detail,
                                    final Integer uid,
                                    final Integer loanId) {
        LwlLoanRadarApply radarApply = loanRadarApplyRepository.findByLoanIdAndUid(loanId, uid);
        if (Objects.nonNull(radarApply)) {
            log.error("保存申请雷达报告详情已经存在");
        }
        LwlLoanRadarApply loanRadarApply = new LwlLoanRadarApply();
        RadarApplyAssembler.convertToEntity(apply_report_detail, loanRadarApply, uid, loanId);

        loanRadarApplyRepository.save(loanRadarApply);
    }

    /**
     * 保存行为雷达报告详情
     */
    private void saveRadarBehaviorData(final LoanRadarBehaviorDto behavior_report_detail,
                                       final Integer uid,
                                       final Integer loanId) {
        LwlLoanRadarBehavior radarBehavior = loanRadarBehaviorRepository.findByLoanIdAndUid(loanId, uid);
        if (Objects.nonNull(radarBehavior)) {
            log.error("保存行为雷达报告详情已经存在");
        }
        LwlLoanRadarBehavior loanRadarBehavior = new LwlLoanRadarBehavior();
        RadarBehaviorAssembler.convertToEntity(behavior_report_detail, loanRadarBehavior, uid, loanId);

        loanRadarBehaviorRepository.save(loanRadarBehavior);
    }

    /**
     * 保存信用现状报告详情
     */
    private void saveRadarCurrentData(final LoanRadarCurrentDto current_report_detail,
                                      final Integer uid,
                                      final Integer loanId) {
        LwlLoanRadarCurrent radarCurrent = loanRadarCurrentRepository.findByLoanIdAndUid(loanId, uid);
        if (Objects.nonNull(radarCurrent)) {
            log.error("保存信用现状报告详情已经存在");
        }
        LwlLoanRadarCurrent loanRadarCurrent = new LwlLoanRadarCurrent();
        RadarCurrentAssembler.convertToEntity(current_report_detail, loanRadarCurrent, uid, loanId);

        loanRadarCurrentRepository.save(loanRadarCurrent);
    }

}
