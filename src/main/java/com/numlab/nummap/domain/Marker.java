package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;

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
    private String address;


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
        this.description = user.getDescription();
        this.address = createAddress(user);

        /* Par default draggable et focus sont à false */
        this.draggable = false;
        this.focus = false;


        /*Adresse*/




        if(user.getCompetencies() != null){
            this.competencies = user.getCompetencies();
        }

        if(user.getSectors() != null){
            this.sectors = user.getSectors();
        }

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

          /* Création du message */
        this.message = createMessage(user);


    }


    public String createAddress(User user){
        String res = "";
        if(category.equals(CategoryEnum.COMPANY) || category.equals(CategoryEnum.ASSOCIATION)) {
            return user.getCompanyContactInformation().getAddress().toPostalFormat();
        } else {
            return user.getPersonContactInformation().getAddress().toPostalFormat();
        }
    }

    public String createMessage(User user){
        String message = "";

        if(user.getCompanyContactInformation() != null){
           message = "<div class=\"markerTitle\">"+user.getCompanyContactInformation().getName()+"</div>";
           message += "<div class=\"markerAdresse\">"+address+"</div>";
            if(user.getCompanyContactInformation().getWebsite() != null){
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\""+user.getCompanyContactInformation().getWebsite()+"\" target=\"_blank\">"+user.getCompanyContactInformation().getWebsite()+"</a></div>";
            }
            /* Affichage du Contact de l'entreprise */
          if(user.getPersonContactInformation() != null){
                message += "<div class=\"markerGrayInput \"> Contact : "+user.getPersonContactInformation().getFirstName()+" "+user.getPersonContactInformation().getLastName()+"</div>";

          }

           /* Affichage du des réseaux sociaux de l'entreprise */
            if(user.getCompanyContactInformation().getSocialNetworkList() != null){
                List<SocialNetwork> socialNetworkEnumList = user.getCompanyContactInformation().getSocialNetworkList();
                for(SocialNetwork social : socialNetworkEnumList){
                    message += "<div class=\"socialNetWork\">";
                    message += "  <div>"+toLowerCaseButFirstChar(social.getType().toString())+" : "+social.getAddress()+" </div>";
                    message += "</div>";
                }

            }

        }else if(user.getPersonContactInformation() != null) {
            message = "<div class=\"markerTitle\">" + user.getPersonContactInformation().getFirstName() + " " + user.getPersonContactInformation().getLastName() + "</div>";
            message += "<div class=\"markerAdresse\">" + address + "</div>";
            if (user.getPersonContactInformation().getWebsite() != null) {
                message += "<div class=\"markerWebSite\"><a class=\"link\" href=\"" + user.getPersonContactInformation().getWebsite() + "\" target=\"_blank\">" + user.getPersonContactInformation().getWebsite() + "</a></div>";

            }

                  /* Affichage du des réseaux sociaux du contact de l'entreprise */
            if(user.getPersonContactInformation().getSocialNetworkList() != null){
                List<SocialNetwork> socialNetworkEnumList = user.getPersonContactInformation().getSocialNetworkList();
                for(SocialNetwork social : socialNetworkEnumList){
                    message += "<div class=\"socialNetWork\">";
                    message += "  <div>"+toLowerCaseButFirstChar(social.getType().toString())+" : "+social.getAddress()+" </div>";
                    message += "</div>";
                }
            }



        }





      /* Ajout des compétences */
        if(this.competencies.size() != 0){
            message += "<div class=\"markerDomain\">Compétences :";

            for(String competence : this.competencies){
              message +=  "<span class=\"competenceList\">"+competence+"</span><span> </span>";
            }
            message += "</div>";
        }

           /* Ajout des secteurs */
        if(this.sectors.size() != 0){
            message += "<div class=\"markerDomain\">Secteurs : ";
            for(String sector : this.sectors){
                message +=  "<span class=\"competenceList\">"+sector+"</span><span> </span>";
            }
            message += "</div>";
        }

    /* Ajout des Domaines*/
        if(this.fields.size() != 0){
            message += "<div class=\"markerDomain\">Domaines :";
            for(FieldEnum domain : this.fields){
                message +=  "<span class=\"competenceList\">"+toLowerCaseButFirstChar(domain.toString())+"</span><span> </span>";
            }
            message += "</div>";
        }

        return(message);
    }

    /**
     * Fonction pour mettre la String en minuscule sauf la première lettre, notamment pour les Domaines
     * @param str
     * @return
     */
    String toLowerCaseButFirstChar(String str){
        str  = str.toLowerCase();
        String newString = Character.toString(str.charAt(0)).toUpperCase()+str.substring(1);
        return(newString);
    }


}


