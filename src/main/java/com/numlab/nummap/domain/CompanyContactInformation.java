package com.numlab.nummap.domain;

import com.google.common.collect.Multimap;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

import java.util.List;

/**
 * Created by christo on 11/03/15.
 */
public class CompanyContactInformation {
    private String companyName;
    private String phone;
    private String email;
    private Address address;
    private String website;
    private List<SocialNetwork> SocialNetworkList;
    private int siren;

    public CompanyContactInformation(String companyName, String phone, String email, Address address, String website,
                                     List<SocialNetwork> socialNetworkList, int siren) {
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.website = website;
        SocialNetworkList = socialNetworkList;
        this.siren = siren;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<SocialNetwork> getSocialNetworkList() {
        return SocialNetworkList;
    }

    public void setSocialNetworkList(List<SocialNetwork> socialNetworkList) {
        SocialNetworkList = socialNetworkList;
    }

    public int getSiren() {
        return siren;
    }

    public void setSiren(int siren) {
        this.siren = siren;
    }
}
