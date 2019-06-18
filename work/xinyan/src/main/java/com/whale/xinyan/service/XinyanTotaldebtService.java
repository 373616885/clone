package com.whale.xinyan.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.whale.common.util.HttpUtils;
import com.whale.common.util.JsonUtil;
import com.whale.xinyan.bean.ThirdLogModeEnum;
import com.whale.xinyan.bean.XinYanEnum;
import com.whale.xinyan.dto.*;
import com.whale.xinyan.entity.LoanProbe;
import com.whale.xinyan.entity.LoanTotaldebt;
import com.whale.xinyan.entity.LoanTotaldebtDetail;
import com.whale.xinyan.jpa.LoanTotaldebtDetailRepository;
import com.whale.xinyan.jpa.LoanTotaldebtRepository;
import com.whale.xinyan.rsa.RsaCodingUtil;
import com.whale.xinyan.rsa.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author qinjp
 * @date 2019-06-06
 **/
@Slf4j
@Service
public class XinyanTotaldebtService  {

    @Autowired
    private LoanTotaldebtRepository loanTotaldebtRepository;

    @Autowired
    private LoanTotaldebtDetailRepository loanTotaldebtDetailRepository;

    @Autowired
    private XinyanBaseService xinyanBaseService;

    private final TypeReference<XinyanResp<TotaldebtData>> jsonType = new TypeReference<XinyanResp<TotaldebtData>>() {};


    /**
     * 新颜-共债
     */
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public XinyanResp<TotaldebtData> totaldebt(final String idNo,
                          final String idName,
                          final Integer uid,
                          final Integer loanId) {

        // 业务请求参数xinyanBusinessReq加密后变成  请求参数XinyanReq类的 data_content 字段
        XinyanBusinessReq xinyanBusinessReq = xinyanBaseService.buildXinyanBusinessReq()
                .setId_no(SecurityUtil.MD5(idNo))
                .setId_name(SecurityUtil.MD5(idName))
                .setVersions(XinYanEnum.TOTALDEBT_REPORT.getVersions());

        // base64 编码
        String base64str = SecurityUtil.Base64Encode(JsonUtil.beanToJson(xinyanBusinessReq));

        // 参数加密
        String dataContent = RsaCodingUtil.encryptByPrivateKey(base64str, xinyanBaseService.getPrivateKey());

        // 请求参数
        XinyanReq xinyanReq = xinyanBaseService.buildXinyanReq()
                .setData_content(dataContent);

        String reqStr = JsonUtil.beanToJson(xinyanReq);

        log.info("新颜-共债请求: uid:{}  loanId:{}  idNo:{}  idName:{}", uid, loanId, idNo, idName);
        log.info("新颜-共债请求:{} ", reqStr);

        // 接口URL
        String apiUrl = XinYanEnum.TOTALDEBT_REPORT.getApiUrl(xinyanBaseService.getHost());

        // 远程调用新颜-共债接口
        String respStr = HttpUtils.doPostSSL(apiUrl, null, JsonUtil.jsonToMap(reqStr));

        // respStr = "{    \"success\": true,    \"data\": {        \"code\": \"0\",        \"desc\": \"查询成功\",        \"trans_id\": \"14910304379231213\",        \"trade_no\": \"201704011507240100057329\",        \"fee\": \"Y\",        \"id_no\": \"0783231bcc39f4957e99907e02ae401c\",        \"id_name\": \"dd67a5943781369ddd7c594e231e9e70\",        \"versions\": \"1.0.0\",        \"result_detail\": {            \"current_order_count\":\"12\",               \"current_org_count\":\"8\",               \"current_order_amt\":\"10000-20000\",               \"current_order_lend_amt\":\"1000-4000\",            \"totaldebt_detail\": [                {                    \"totaldebt_order_count\":\"21\",                    \"totaldebt_date\":\"201711\",                    \"totaldebt_order_amt\":\"20000-40000\",                    \"new_or_old\":\"N\",                    \"totaldebt_org_count\":\"13\",                    \"totaldebt_order_lend_amt\":\"20000-40000\"                },                {                    \"totaldebt_order_count\":\"19\",                    \"totaldebt_date\":\"201710\",                    \"totaldebt_order_amt\":\"20000-40000\",                    \"new_or_old\":\"Y\",                    \"totaldebt_org_count\":\"13\",                    \"totaldebt_order_lend_amt\":\"20000-40000\"                }             ]         }    },    \"errorCode\": null,    \"errorMsg\": null}";

        // respStr = "{    \"success\": false,    \"data\":null,    \"errorCode\":\"S1000\",    \"errorMsg\":\"请求参数有误\"}";

        log.info("新颜-共债返回:{}", respStr);

        XinyanResp<TotaldebtData> xinyanResp = JsonUtil.jsonToBean(respStr, jsonType);

        if (Objects.isNull(xinyanResp)) {
            return null;
        }

        TotaldebtData data = xinyanResp.getData();

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

        // ========== 保存共债详情 Start ========== //
        if (xinyanResp.isSuccess() && data != null) {
            // 保存 lwl_loan_probe表
            LoanProbe saveProbe = xinyanBaseService.saveProbe(data, idNo, idName, uid, loanId, XinYanEnum.TOTALDEBT_REPORT);
            // 判断之前是否保存过
            if (Objects.nonNull(saveProbe) && data.getResult_detail() != null) {
                // 保存债详情
                saveTotaldebtData(data.getResult_detail(), idNo, idName, uid, loanId);
            }        }
        // ========== 保存共债详情 End ========== //


        return xinyanResp;
    }




    /**
     * 保存第三方返回的数据
     */
    private void saveTotaldebtData(final TotaldeResultDetail resultDetail,
                                   final String idNo,
                                   final String idName,
                                   final Integer uid,
                                   final Integer loanId) {
        LoanTotaldebt loanTotaldebt = loanTotaldebtRepository.findByLoanIdAndUid(loanId, uid);
        // 判断之前是否已经存在的
        if (Objects.isNull(loanTotaldebt)) {
            // 将 resultDetail 保存到 loanTotaldebt
            loanTotaldebt = saveLoanTotaldebt(resultDetail, idNo, idName, uid, loanId);
            // 将 Totaldebt_detail 保存到 LoanTotaldebtDetail
            if (CollectionUtils.isNotEmpty(resultDetail.getTotaldebt_detail())) {
                // 将 Totaldebt_detail 保存到 LoanTotaldebtDetail
                saveLoanTotaldebtDetail(resultDetail.getTotaldebt_detail(), loanTotaldebt.getTotaldebtId(), uid, loanId);
            }
        }
    }


    /**
     * 将 Result_detail 保存到 loanTotaldebt
     */
    private LoanTotaldebt saveLoanTotaldebt(final TotaldeResultDetail resultDetail,
                                            final String idNo,
                                            final String idName,
                                            final Integer uid,
                                            final Integer loanId) {
        LoanTotaldebt loanTotaldebt = new LoanTotaldebt();
        loanTotaldebt.setLoanId(loanId);
        loanTotaldebt.setUid(uid);
        loanTotaldebt.setIdNo(idNo);
        loanTotaldebt.setIdName(idName);
        loanTotaldebt.setCurrentOrgCount(resultDetail.getCurrent_org_count());
        loanTotaldebt.setCurrentOrderCount(resultDetail.getCurrent_order_count());
        loanTotaldebt.setCurrentOrderAmt(resultDetail.getCurrent_order_amt());
        loanTotaldebt.setCurrentOrderLendAmt(resultDetail.getCurrent_order_lend_amt());
        return loanTotaldebtRepository.save(loanTotaldebt);
    }


    /**
     * 将 Totaldebt_detail 保存到 LoanTotaldebtDetail
     */
    private void saveLoanTotaldebtDetail(final List<TotaldebtDetail> totaldebtDetails,
                                         final Integer totaldebtId,
                                         final Integer uid,
                                         final Integer loanId) {
        for (TotaldebtDetail totaldebtDetail : totaldebtDetails) {
            LoanTotaldebtDetail loanTotaldebtDetail = new LoanTotaldebtDetail();
            loanTotaldebtDetail.setTotaldebtId(totaldebtId);
            loanTotaldebtDetail.setUid(uid);
            loanTotaldebtDetail.setLoanId(loanId);
            loanTotaldebtDetail.setTotaldebtDate(totaldebtDetail.getTotaldebt_date());
            loanTotaldebtDetail.setTotaldebtOrgCount(totaldebtDetail.getTotaldebt_org_count());
            loanTotaldebtDetail.setTotaldebtOrderCount(totaldebtDetail.getTotaldebt_order_count());
            loanTotaldebtDetail.setTotaldebtOrderAmt(totaldebtDetail.getTotaldebt_order_amt());
            loanTotaldebtDetail.setNewOrOld(totaldebtDetail.getNew_or_old());
            loanTotaldebtDetail.setTotaldebtOrderLendAmt(totaldebtDetail.getTotaldebt_order_lend_amt());
            loanTotaldebtDetailRepository.save(loanTotaldebtDetail);
        }
    }

}
