package org.jast.mybatis.cms.entity;

import java.io.Serializable;

/**
 * Created by zhiwen on 15-6-4.
 */
public class Category implements Serializable{

    private Long cid;
    private String cname;
    private String cdescribe;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCdescribe() {
        return cdescribe;
    }

    public void setCdescribe(String cdescribe) {
        this.cdescribe = cdescribe;
    }
}
