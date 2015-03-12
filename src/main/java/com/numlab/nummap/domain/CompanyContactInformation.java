package com.numlab.nummap.domain;

import com.google.common.collect.Multimap;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

/**
 * Created by christo on 11/03/15.
 */
public class CompanyContactInformation {
    private String companyName;
    private String phone;
    private String email;
    private Address adress;
    private String website;
    private Multimap<SocialNetworkEnum, String> SocialNetworkList;
    private int siren;

    public CompanyContactInformation(String companyName, String phone, String email, Address adress, String website,
                                     Multimap<SocialNetworkEnum, String> socialNetworkList, int siren) {
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.adress = adress;
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

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Multimap<SocialNetworkEnum, String> getSocialNetworkList() {
        return SocialNetworkList;
    }

    public void setSocialNetworkList(Multimap<SocialNetworkEnum, String> socialNetworkList) {
        SocialNetworkList = socialNetworkList;
    }

    public int getSiren() {
        return siren;
    }

    public void setSiren(int siren) {
        this.siren = siren;
    }
}
