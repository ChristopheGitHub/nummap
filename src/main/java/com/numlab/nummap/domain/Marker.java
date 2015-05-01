package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eisti on 4/3/15.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
        this.name = user.getLogin();
        /* On set la location */
        this.lat = user.getLocation().getLatitude();
        this.lng = user.getLocation().getLongitude();
        this.category =  user.getCategory();

        /* Par default draggable et focus sont à false */
        this.draggable = false;
        this.focus = false;

        /* Description */
        this.description = user.getDescription();

        /*Adresse*/
        this.adresse = createAddress(user);

        /* Création du message */
        this.message = createMessage(user);


        /* Liste des compétences */
        if(user.getCompetencies() != null){
            this.competencies = user.getCompetencies();
        }

          /* Liste des sectors */
        if(user.getSectors() != null){
            this.sectors = user.getSectors();
        }

          /* Liste des fields */
        if(user.getFields() != null){
            this.fields = user.getFields();
        }

        /* PersonContactInformation */
       if(user.getCompanyContactInformation() != null){
           this.companyContactInformation = user.getCompanyContactInformation();
       }

        /* CompanyContactInformation */
        if(user.getPersonContactInformation() != null){
           this.personContactInformation = user.getPersonContactInformation();
        }

    }


    public String createAddress(User user){
        String res = "";
        if(category.equals(CategoryEnum.COMPANY) || category.equals(CategoryEnum.ASSOCIATION)) {
            return user.getCompanyContactInformation().getAddress().toPostalFormat();
        /*} else if (category.equals(CategoryEnum.PROFESSOR) || category.equals(CategoryEnum.STUDENT) ||*/
            /*category.equals(CategoryEnum.FREELANCE)) {*/
        } else {
            return user.getPersonContactInformation().getAddress().toPostalFormat();
        }
        /*if(user.getCompanyContactInformation() != null) {
            Address address = user.getCompanyContactInformation().getAddress();
            if (address != null) {
                res = address.toPostalFormat();
            }
        }
        else if(user.getPersonContactInformation() != null){
            Address address = user.getPersonContactInformation().getAddress();
            if(address != null) {
                res = address.toPostalFormat();
            }
        }

        return(adresse);*/
    }

    public String createMessage(User user){
        String message = "";

        if(user.getCompanyContactInformation() != null){
           message = "<div class=\"markerTitle\">"+user.getCompanyContactInformation().getName()+"</div>";
           message += "<div class=\"markerAdresse\">"+createAddress(user)+"</div>";
            if(user.getCompanyContactInformation().getWebsite() != null){
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\""+user.getCompanyContactInformation().getWebsite()+"\" target=\"_blank\">"+user.getCompanyContactInformation().getWebsite()+"</a></div>";
            }
        }else if(user.getPersonContactInformation() != null){
            message = "<div class=\"markerTitle\">"+user.getPersonContactInformation().getFirstName()+" "+user.getPersonContactInformation().getLastName()+"</div>";
            message += "<div class=\"markerAdresse\">"+createAddress(user)+"</div>";
            if(user.getPersonContactInformation().getWebsite() != null){
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\""+user.getPersonContactInformation().getWebsite()+"\" target=\"_blank\">"+user.getPersonContactInformation().getWebsite()+"</a></div>";
            }
        }
        return(message);
    }
}
