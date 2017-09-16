package com.services.micro.commons.vip.api;

import com.services.micro.commons.vip.config.VipConstants;


public class VipResponse {
    private String local = VipConstants.DOWN;
    private String global = VipConstants.DOWN;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

}
