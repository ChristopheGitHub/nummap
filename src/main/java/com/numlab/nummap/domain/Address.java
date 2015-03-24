package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by christo on 11/03/15.
 */
public class Address {

    private String city;

    private String street;

    private int postalCode;

    private int postalBox;

    private String addressComplement;

    @JsonCreator
    public Address(
        @JsonProperty("city") String city,
        @JsonProperty("street") String street,
        @JsonProperty("postalCode") int postalCode,
        @JsonProperty("postalBox") int postalBox,
        @JsonProperty("addressComplement") String addressComplement) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.postalBox = postalBox;
        this.addressComplement = addressComplement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdressComplement() {
        return addressComplement;
    }

    public void setAdressComplement(String adressComplement) {
        this.addressComplement = adressComplement;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getPostalBox() {
        return postalBox;
    }

    public void setPostalBox(int postalBox) {
        this.postalBox = postalBox;
    }

    public String toMapSearchFormat(){
        return street + ", " + postalCode + " " + city;
    }

}
