package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by christo on 11/03/15.
 */
public class Address {

    private String city;

    private String street;

    private int postalCode;

    private int postalBox;


    public Address(String city, String street, int postalCode, int postalBox, String adressComplement) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.postalBox = postalBox;
        this.adressComplement = adressComplement;
    }

    private String adressComplement;

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
        return adressComplement;
    }

    public void setAdressComplement(String adressComplement) {
        this.adressComplement = adressComplement;
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

}
