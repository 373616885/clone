package com.whale.xinyan.entity;

import com.whale.common.bean.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lwl_third_req_log")
@EqualsAndHashCode(callSuper = true)
public class LwlThridReqLog extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "loan_id")
    private Integer loanId;

    @Column(name = "mode_type")
    private Integer modeType;

    @Column(name = "mode_name")
    private String modeName;

    @Column(name = "url")
    private String url;

    @Column(name = "req_text")
    private String reqText;

    @Column(name = "code")
    private String code;

    @Column(name = "msg")
    private String msg;

    @Column(name = "request_no")
    private String requestNo;

    @Column(name = "response_no")
    private String responseNo;

    public LwlThridReqLog() {

    }

    public LwlThridReqLog(Integer uid, Integer loanId, Integer modeType, String modeName, String url,
                          String reqText, String requestNo, String responseNo, String code, String msg) {
        this.uid = uid;
        this.loanId = loanId;
        this.modeType = modeType;
        this.modeName = modeName;
        this.url = url;
        this.reqText = reqText;
        this.requestNo = requestNo;
        this.responseNo = responseNo;
        this.code = code;
        this.msg = msg;
    }
}
