package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Multimap;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

import java.util.List;

/**
 * Created by christo on 11/03/15.
 */
public class PersonContactInformation {

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private Address address;

    private String website;

    private List<SocialNetwork> socialNetworkList;


    public PersonContactInformation(String firstName, String lastName, String phone, String email, Address address,
                                    String website, List<SocialNetwork> socialNetworkList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.website = website;
        this.socialNetworkList = socialNetworkList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<SocialNetwork> getSocialNetworkList() {
        return socialNetworkList;
    }

    public void setSocialNetworkList(List<SocialNetwork> socialNetworkList) {
        socialNetworkList = socialNetworkList;
    }
}
