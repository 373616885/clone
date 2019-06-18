package com.whale.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dengjihai
 * @create 2019-03-18
 **/
@MappedSuperclass
public class IdEntity implements Entity {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "c_time", updatable = false)
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "u_time")
    private Date updateTime;


    @PrePersist
    protected void prePersist() {
        if (createTime == null) {
            createTime = new Date();
        }
        updateTime = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        updateTime = new Date();
    }

    @PostUpdate
    protected void postUpdate(){
        afterUpdate();
    }


    /**
     * 更新操作事务提交之后触发
     * 子类根据需求自己实现
     */
    protected void afterUpdate(){}


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
