package com.whale.xinyan.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.whale.common.util.HttpUtils;
import com.whale.common.util.JsonUtil;
import com.whale.xinyan.bean.ThirdLogModeEnum;
import com.whale.xinyan.bean.XinYanEnum;
import com.whale.xinyan.dto.*;
import com.whale.xinyan.entity.LoanProbe;
import com.whale.xinyan.entity.LwlLoanOverdueRecord;
import com.whale.xinyan.entity.LwlLoanOverdueRecordDetails;
import com.whale.xinyan.jpa.LwlLoanOverdueRecordDetailsRepository;
import com.whale.xinyan.jpa.LwlLoanOverdueRecordRepository;
import com.whale.xinyan.rsa.RsaCodingUtil;
import com.whale.xinyan.rsa.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: HXY-1283
 * Date: 2019/6/5 0005
 * Time: 下午 5:48
 * 新颜-智能评估-逾期档案API规范V3.0.1
 */
@Slf4j
@Service
public class XinyanOverdueService {

    @Autowired
    private XinyanBaseService xinyanBaseService;

    @Autowired
    private LwlLoanOverdueRecordRepository loanOverdueRecordRepository;

    @Autowired
    private LwlLoanOverdueRecordDetailsRepository loanOverdueRecordDetailsRepository;

    private final TypeReference<XinyanResp<OverdueData>> jsonType = new TypeReference<XinyanResp<OverdueData>>() {};


    /**
     * 新颜智能评估逾期档案
     */
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public XinyanResp<OverdueData> overdue(final String idNo,
                                           final String idName,
                                           final Integer uid,
                                           final Integer loanId) {

        // 业务请求参数xinyanBusinessReq加密后变成  请求参数XinyanReq类的 data_content 字段
        XinyanBusinessReq xinyanBusinessReq = xinyanBaseService.buildXinyanBusinessReq()
                .setId_no(SecurityUtil.MD5(idNo))
                .setId_name(SecurityUtil.MD5(idName))
                .setVersions(XinYanEnum.OVERDUE_RECORD.getVersions());

        // base64 编码
        String base64str = SecurityUtil.Base64Encode(JsonUtil.beanToJson(xinyanBusinessReq));

        // 参数加密
        String dataContent = RsaCodingUtil.encryptByPrivateKey(base64str, xinyanBaseService.getPrivateKey());

        // 请求参数
        XinyanReq xinyanReq = xinyanBaseService.buildXinyanReq()
                .setData_content(dataContent);

        String reqStr = JsonUtil.beanToJson(xinyanReq);

        log.info("新颜-智能评估-逾期档案请求: uid:{}  loanId:{}  idNo:{}  idName:{}", uid, loanId, idNo, idName);
        log.info("新颜-智能评估-逾期档案请求:{}", reqStr);

        // 接口URL
        String apiUrl = XinYanEnum.OVERDUE_RECORD.getApiUrl(xinyanBaseService.getHost());

        // 远程调用新颜-智能评估-逾期档案接口
        String respStr = HttpUtils.doPostSSL(apiUrl, null, JsonUtil.jsonToMap(reqStr));

        log.info("新颜-智能评估-逾期档案返回:{}", respStr);

        //respStr = "{ \"success\":true, \"data\":{  \"fee\":\"Y\",  \"code\":\"0\",  \"desc\":\"查询成功\",  \"trans_id\":\"req2019052710045855421021\",  \"trade_no\":\"20190527100618766000000638167271\",  \"id_no\":\"52722601233546f1428bcc42107db666\",  \"id_name\":\"3327a8f84aa72e858920e2176626ae6a\",  \"versions\":\"1.3.0\",  \"result_detail\":{   \"debt_amount\":\"2900\",   \"order_count\":\"4\",   \"details\":[    {     \"date\":\"2019-03\",     \"amount\":\"1-1000\",     \"count\":\"M0\",     \"settlement\":\"N\"    },    {     \"date\":\"2019-02\",     \"amount\":\"1000-2000\",     \"count\":\"M0\",     \"settlement\":\"N\"    },    {     \"date\":\"2019-01\",     \"amount\":\"1-1000\",     \"count\":\"M0\",     \"settlement\":\"N\"    },    {     \"date\":\"2019-01\",     \"amount\":\"1-1000\",     \"count\":\"M0\",     \"settlement\":\"Y\"    }   ],   \"member_count\":\"3\"  } }, \"errorCode\":null, \"errorMsg\":null}";

        //respStr = "{ \"success\": false, \"data\":null, \"errorCode\":\"S1000\", \"errorMsg\":\"请求参数有误\" }";

        XinyanResp<OverdueData> xinyanResp = JsonUtil.jsonToBean(respStr, jsonType);

        if (Objects.isNull(xinyanResp)) {
            return null;
        }

        OverdueData data = xinyanResp.getData();

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

        // ========== 保存逾期档案信息 Start ========== //
        if (xinyanResp.isSuccess() && data != null) {
            // 保存 lwl_loan_probe表
            LoanProbe saveProbe = xinyanBaseService.saveProbe(data, idNo, idName, uid, loanId, XinYanEnum.OVERDUE_RECORD);
            // 判断之前是否保存过
            if (Objects.nonNull(saveProbe) && data.getResult_detail() != null) {
                // 保存逾期档案信息
                LwlLoanOverdueRecord lwlLoanOverdueRecord = saveOverdueRecord(data.getResult_detail(), uid, loanId);
                // 保存逾期档案详情
                saveOverdueRecordDetails(data.getResult_detail().getDetails(), lwlLoanOverdueRecord);
            }
        }
        // ========== 保存逾期档案信息 End ========== //

        return xinyanResp;
    }


    /**
     * 保存逾期档案信息
     */
    private LwlLoanOverdueRecord saveOverdueRecord(final OverdueResultDetail resultDetail,
                                                   final Integer uid,
                                                   final Integer loanId) {
        LwlLoanOverdueRecord loanOverdueRecord = new LwlLoanOverdueRecord();
        loanOverdueRecord.setLoanId(loanId);
        loanOverdueRecord.setUid(uid);
        loanOverdueRecord.setDebtAmount(resultDetail.getDebt_amount());
        loanOverdueRecord.setMemberCount(resultDetail.getMember_count());
        loanOverdueRecord.setOrderCount(resultDetail.getOrder_count());
        return loanOverdueRecordRepository.save(loanOverdueRecord);
    }

    /**
     * 保存逾期档案详情
     */
    private void saveOverdueRecordDetails(final List<OverdueDetails> voerdueDetails,
                                          final LwlLoanOverdueRecord lwlLoanOverdueRecord) {
        LwlLoanOverdueRecordDetails loanOverdueRecordDetails = null;
        for (OverdueDetails voerdueDetail : voerdueDetails) {
            loanOverdueRecordDetails = new LwlLoanOverdueRecordDetails();
            loanOverdueRecordDetails.setOverdueId(lwlLoanOverdueRecord.getOverdueId());
            loanOverdueRecordDetails.setAmount(voerdueDetail.getAmount());
            loanOverdueRecordDetails.setCount(voerdueDetail.getCount());
            loanOverdueRecordDetails.setDate(voerdueDetail.getDate());
            loanOverdueRecordDetails.setSettlement(voerdueDetail.getSettlement());
            loanOverdueRecordDetailsRepository.save(loanOverdueRecordDetails);
        }
    }


}
