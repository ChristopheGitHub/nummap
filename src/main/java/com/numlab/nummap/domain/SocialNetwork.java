package com.numlab.nummap.domain;

import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

/**
 * Created by christo on 16/03/15.
 */
public class SocialNetwork {
    private SocialNetworkEnum type;
    private String address;

    public SocialNetwork(SocialNetworkEnum type, String address) {
        this.type = type;
        this.address = address;
    }

    public SocialNetworkEnum getType() {
        return type;
    }

    public void setType(SocialNetworkEnum type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
