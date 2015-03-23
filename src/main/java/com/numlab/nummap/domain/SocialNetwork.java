package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

/**
 * Created by christo on 16/03/15.
 */
public class SocialNetwork {
    private SocialNetworkEnum type;
    private String address;

    @JsonCreator
    public SocialNetwork(
        @JsonProperty("SocialNetworkEnum") SocialNetworkEnum type,
        @JsonProperty("address") String address
    ) {
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
