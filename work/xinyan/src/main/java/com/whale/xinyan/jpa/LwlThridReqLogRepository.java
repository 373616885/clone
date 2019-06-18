package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlThridReqLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 记录调用所有第三方接口的请求信息
 */
public interface LwlThridReqLogRepository extends JpaRepository<LwlThridReqLog, Long>,
        JpaSpecificationExecutor<LwlThridReqLog> {


}