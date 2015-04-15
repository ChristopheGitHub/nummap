package com.numlab.nummap.domain;

import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eisti on 4/3/15.
 */
public class Marker {
    private String name;
    private Double lat;
    private Double lng;
    private String message;
    private boolean focus;
    private boolean draggable;
    private CategoryEnum category;
    private List<String> competencies;
    private List<String> sectors;
    private List<FieldEnum> fields;
    private PersonContactInformation personContactInformation;
    private CompanyContactInformation companyContactInformation;
    private String description;
    private String adresse;


    /**
     * Ce constructeur est appelé seulement, si le user possède une location ainsi qu'une catégorie
     * @param user
     */
    public Marker(User user){
        this.setName(user.getLogin());
        /* On set la location */
        this.setLat(user.getLocation().getLatitude());
        this.setLng(user.getLocation().getLongitude());
        this.setCategory(user.getCategory());

        /* Par default draggable et focus sont à false */
        this.setDraggable(false);
        this.setFocus(false);

        /* Description */
        this.setDescription(user.getDescription());

        /*Adresse*/
        this.setAdresse(makeAdresse(user));

        /* Création du message */
        this.setMessage(setMessage(user));


        /* Liste des compétences */
        if(user.getCompetencies() != null){
            this.setCompetencies(user.getCompetencies());
        }

          /* Liste des sectors */
        if(user.getSectors() != null){
            this.setSectors(user.getSectors());
        }

          /* Liste des fields */
        if(user.getFields() != null){
            this.setFields(user.getFields());
        }

        /* PersonContactInformation */
       if(user.getCompanyContactInformation() != null){
           this.setCompanyContactInformation(user.getCompanyContactInformation());
       }

        /* CompanyContactInformation */
        if(user.getPersonContactInformation() != null){
           this.setPersonContactInformation(user.getPersonContactInformation());
        }

    }


    public String makeAdresse(User user){
        String adresse = "";
        if(user.getCompanyContactInformation() != null) {
            Address address = user.getCompanyContactInformation().getAddress();
            if (address != null) {
                if ((address.getPostalBox() == new Integer(0).intValue())) {
                    adresse = address.getStreet() + ", " + address.getPostalCode() + ", " + address.getCity();
                } else {
                    adresse = address.getStreet() + ", " + address.getPostalCode() + ", " + address.getCity() + ", " + address.getPostalBox();
                }
            }
        }
        else if(user.getPersonContactInformation() != null){
        Address address = user.getPersonContactInformation().getAddress();
        if(address != null) {
                if ((address.getPostalBox() == new Integer(0).intValue())) {
                    adresse = address.getStreet() + ", " + address.getPostalCode() + ", " + address.getCity();
                } else {
                    adresse = address.getStreet() + ", " + address.getPostalCode() + ", " + address.getCity() + ", " + address.getPostalBox();
                }
           }
        }

        return(adresse);
    }

    public String setMessage(User user){
        String message = "";

        if(user.getCompanyContactInformation() != null){
           message = "<div class=\"markerTitle\">"+user.getCompanyContactInformation().getName()+"</div>";
           message += "<div class=\"markerAdresse\">"+makeAdresse(user)+"</div>";
            if(user.getCompanyContactInformation().getWebsite() != null){
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\""+user.getCompanyContactInformation().getWebsite()+"\">"+user.getCompanyContactInformation().getWebsite()+"</a></div>";
            }
        }else if(user.getPersonContactInformation() != null){
            message = "<div class=\"markerTitle\">"+user.getPersonContactInformation().getFirstName()+" "+user.getPersonContactInformation().getLastName()+"</div>";
            message += "<div class=\"markerAdresse\">"+makeAdresse(user)+"</div>";
            if(user.getPersonContactInformation().getWebsite() != null){
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\""+user.getPersonContactInformation().getWebsite()+"\">"+user.getPersonContactInformation().getWebsite()+"</a></div>";
            }
        }
        return(message);
    }





    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public List<String> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(List<String> competencies) {
        this.competencies = competencies;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    public List<FieldEnum> getFields() {
        return fields;
    }

    public void setFields(List<FieldEnum> fields) {
        this.fields = fields;
    }

    public PersonContactInformation getPersonContactInformation() {
        return personContactInformation;
    }

    public void setPersonContactInformation(PersonContactInformation personContactInformation) {
        this.personContactInformation = personContactInformation;
    }

    public CompanyContactInformation getCompanyContactInformation() {
        return companyContactInformation;
    }

    public void setCompanyContactInformation(CompanyContactInformation companyContactInformation) {
        this.companyContactInformation = companyContactInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
