package com.whale.xinyan.service;


import com.whale.common.AppException;
import com.whale.common.util.StringUtil;
import com.whale.common.util.TimeUtil;
import com.whale.xinyan.bean.XinYanConfig;
import com.whale.xinyan.bean.XinYanEnum;
import com.whale.xinyan.dto.XinyanBusinessReq;
import com.whale.xinyan.dto.XinyanBussinessResp;
import com.whale.xinyan.dto.XinyanReq;
import com.whale.xinyan.entity.LoanProbe;
import com.whale.xinyan.entity.LwlThridReqLog;
import com.whale.xinyan.jpa.LoanProbeRepository;
import com.whale.xinyan.jpa.LwlThridReqLogRepository;
import com.whale.xinyan.rsa.RsaReadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Objects;

/**
 * 新颜基本公共类
 *
 * @author qinjp
 * @date 2019-06-11
 **/
@Slf4j
@Component
public final class XinyanBaseService {

    @Autowired
    private XinYanConfig xinYanConfig;

    @Autowired
    private LwlThridReqLogRepository thridReqLogRepository;

    private PrivateKey privateKey;

    private String host;

    @PostConstruct
    public void init() {
        // 创建新颜私钥
        if (Paths.get(xinYanConfig.getPfxPath()).toFile().exists()) {
            privateKey = RsaReadUtil.getPrivateKeyFromFile(xinYanConfig.getPfxPath(), xinYanConfig.getPfxPwd());
            // 成功创建
            if (Objects.nonNull(privateKey)) {
                host = xinYanConfig.getHost();
                return;
            }
        }
        throw new AppException("新颜私钥创建失败");
    }


    @Autowired
    private LoanProbeRepository loanProbeRepository;

    /**
     * 保存新颜接口返回结果到lwl_loan_probe表
     *   空  : 已经插入过数
     * 不为空 : 成功插入
     */
    final LoanProbe saveProbe(final XinyanBussinessResp data,
                            final String idNo,
                            final String idName,
                            final Integer uid,
                            final Integer loanId,
                            final XinYanEnum xinYanEnum) {
        LoanProbe probe = loanProbeRepository.findByLoanIdAndApiTypeAndUid(loanId, xinYanEnum.getApiType(), uid);
        if (probe != null) {
            log.error("{} uid:{} loanId:{} 无需重复插入", xinYanEnum.getName(), uid, loanId);
            return null;
        }
        LoanProbe loanProbe = new LoanProbe();
        loanProbe.setUid(uid);
        loanProbe.setLoanId(loanId);
        loanProbe.setApiType(xinYanEnum.getApiType());
        loanProbe.setCode(Integer.valueOf(data.getCode()));
        loanProbe.setDescription(data.getDesc());
        loanProbe.setIdName(idName);
        loanProbe.setIdNo(idNo);
        loanProbe.setFee(data.getFee());
        loanProbe.setTradeNo(data.getTrade_no());
        loanProbe.setTransId(data.getTrans_id());
        loanProbe.setVersions(data.getVersions());
        return loanProbeRepository.save(loanProbe);
    }


    private static final String DATA_TYPE = "json";

    /**
     * 创建XinyanReq请求对象
     */
    final XinyanReq buildXinyanReq() {
        return XinyanReq.builder()
                .member_id(xinYanConfig.getMerchNo())
                .terminal_id(xinYanConfig.getMerchNo())
                .data_type(DATA_TYPE)
                .build();
    }


    private static final String ENCRYPT_TYPE = "MD5";

    /**
     * 创建XinyanBusinessReq请求对象
     */
    final XinyanBusinessReq buildXinyanBusinessReq() {
        return XinyanBusinessReq.builder()
                .member_id(xinYanConfig.getMerchNo())
                .terminal_id(xinYanConfig.getMerchNo())
                .trans_id(StringUtil.getUUID())
                .trade_date(TimeUtil.minDatetimeFormate(TimeUtil.now()))
                .encrypt_type(ENCRYPT_TYPE)
                .build();
    }


    /**
     * 返回解密的 PrivateKey
     */
    final PrivateKey getPrivateKey() {
        return privateKey;
    }


    /**
     * 新颜host
     */
    final String getHost() {
        return host;
    }


    /**
     * 保存第三方请求日志
     */
    final void saveThirdLog(Integer uid, Integer loanId, Integer modeType, String modeName, String url,
                             String reqText, String requestNo, String responseNo, String code, String msg) {
        LwlThridReqLog logInfo = new LwlThridReqLog(uid, loanId, modeType, modeName, url, reqText, requestNo,responseNo,code,msg);
        thridReqLogRepository.save(logInfo);
    }


}
