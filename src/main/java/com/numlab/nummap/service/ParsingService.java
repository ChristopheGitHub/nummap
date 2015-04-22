package com.numlab.nummap.service;

import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.repository.UserRepository;
import com.numlab.nummap.web.rest.AccountResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by eisti on 4/21/15.
 */
@Service
public class ParsingService {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Inject
    private LocationService locationService;

    public void ParsingFichier(String path){
        LinkedList<HashMap<String, String>> linkedList = new LinkedList<>();

        try{
            InputStream ips=new FileInputStream(path);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null){
                /* Ajout de la map correspondant à un utililsateur dans la liste */
                linkedList.add(ParsingLine(ligne.split(";")));
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }


    }


    public HashMap<String, String> ParsingLine(String[] line){

        HashMap<String , String> mapString = new HashMap<>();
        mapString.put("login" , line[0]);
        mapString.put("email", line[1]);
        mapString.put("category", line[2]);
        mapString.put("description", line[3]);
        mapString.put("siren", line[4]);

        mapString.put("personContactInformation.firstName", line[5]);
        mapString.put("personContactInformation.lastName", line[6]);
        mapString.put("personContactInformation.phone", line[7]);
        mapString.put("personContactInformation.email", line[8]);
        mapString.put("personContactInformation.website", line[9]);
        mapString.put("personContactInformation.socialNetworkList.address", line[10]);
        mapString.put("personContactInformation.socialNetworkList.type", line[11]);
        mapString.put("personContactInformation.address.city", line[12]);
        mapString.put("personContactInformation.address.street", line[13]);
        mapString.put("personContactInformation.address.postalCode", line[14]);
        mapString.put("personContactInformation.address.postalbox", line[15]);
        mapString.put("personContactInformation.address.adressComplement", line[16]);

        mapString.put("companyContactInformation.name", line[17]);
        mapString.put("companyContactInformation.phone", line[18]);
        mapString.put("companyContactInformation.email", line[19]);
        mapString.put("companyContactInformation.website", line[20]);
        mapString.put("companyContactInformation.socialNetworkList.address", line[21]);
        mapString.put("companyContactInformation.socialNetworkList.type", line[22]);
        mapString.put("companyContactInformation.address.city", line[23]);
        mapString.put("companyContactInformation.address.street", line[24]);
        mapString.put("companyContactInformation.address.postalCode", line[25]);
        mapString.put("companyContactInformation.address.postalbox", line[26]);
        mapString.put("companyContactInformation.address.adressComplement", line[27]);

        mapString.put("competencies", line[28]);
        mapString.put("sectors", line[29]);
        mapString.put("fields", line[30]);

        return(mapString);
    }






}
