package com.numlab.nummap.service;


import com.numlab.nummap.domain.*;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.domain.enumerations.SocialNetworkEnum;
import com.numlab.nummap.repository.UserRepository;
import com.numlab.nummap.web.rest.AccountResource;
import com.numlab.nummap.web.rest.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


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
            int acc = 0;

            /* Ajout des différents utilisateurs dans la LinkedList, chaque hashMap correspondant à un utilisateur */
            while ((ligne=br.readLine())!=null){
                /* On enleve la premiere ligne qui correspondant au nom des différents champs du csv */
                if(acc != 0) {
                /* Ajout de la map correspondant à un utililsateur dans la liste */
                    linkedList.add(ParsingLine(ligne.split(";")));
                }
                acc++;
            }
            br.close();

            System.out.println("SizeLinkedList"+linkedList.size());
                for(HashMap<String, String> hashMap : linkedList){
                    /* Enregistrement des nouveaux utilisateurs */
                    System.out.println();
                    registerUser(hashMapToUserDTO(hashMap));
                }



        }
        catch (Exception e){
            System.out.println(e.toString());
        }


    }

    private CategoryEnum getCategory(HashMap<String, String> hashMap){
        CategoryEnum categoryEnum;
        try {
            categoryEnum = CategoryEnum.valueOf(hashMap.get("category"));
        }
        catch(IllegalArgumentException iAE){
            throw new IllegalArgumentException("Value of category doesn't exist or Category is null");
        }
        return(categoryEnum);
    }

    private List<FieldEnum> getFieldList(HashMap<String, String> hashMap){
        LinkedList<FieldEnum> fieldEnumList = new LinkedList<>();
        String Fields = hashMap.get("fields");

        if(!Fields.equals("") && Fields != null){
            String[] ArrayField = Fields.split(",");
                for (String field : ArrayField) {
                    try {
                        fieldEnumList.add(FieldEnum.valueOf(field));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(field+ "value doesn't exist");
                    }
                }
        }
        return(fieldEnumList);
    }

    /**
     * Fonction permettant de récuperer l'adresse dans la hashMap, le paramèter key permet de récuperer l'adresse,
     * dans le personContactInformation ainsi que dans companyContactInformation
     * @param hashMap map contenant le csv uploadé, chaque key correspondant à une colonne du csv
     * @param key "companyContactInformation" ou "personContactInformation"
     * @return
     * @throws NullPointerException
     */
    private Address getAddressFormHashMap(HashMap<String, String> hashMap, String key) throws NullPointerException{

        String City = hashMap.get(key+".address.city");
        String Street = hashMap.get(key+".address.street");
        String PostalCode = hashMap.get(key+".address.postalCode");
        String PostalBox = hashMap.get(key+".address.postalBox");
        String AdressComplement =  hashMap.get(key+".address.adressComplement");

        System.out.println(hashMap.get(("Login")));
        System.out.println("City"+City);

        Address address = new Address();

        if(AdressComplement == null){
            AdressComplement = "";
        }

        if(PostalBox == null){
            PostalBox = "0";
        }

        if(Street == null || City.equals("") ||City == null ||Street.equals("")||PostalCode == null || PostalCode.equals("")){
            //throw new NullPointerException("Street, City or PostalCode null");
        }else{
            address.setCity(City);
            address.setStreet(Street);
            address.setPostalBox(Integer.parseInt(PostalBox));
            address.setPostalCode(Integer.parseInt(PostalCode));
            address.setAdressComplement(AdressComplement);
        }

        return(address);
    }

    /**
     * Fonction permettant de récuperer les réseaux sociaux dans la hashMap, le paramèter key permet de récuperer les réseaux sociaux,
     * dans le personContactInformation ainsi que dans companyContactInformation
     * @param hashMap map contenant le csv uploadé, chaque key correspondant à une colonne du csv
     * @param key "companyContactInformation" ou "personContactInformation"
     * @return
     * @throws NullPointerException
     */
    private List<SocialNetwork> getSocialNetworkListFromHashMap(HashMap<String, String> hashMap, String key) throws NullPointerException{
        List<SocialNetwork> socialNetworkList = null;

        String socialNetworkListAddress = hashMap.get(key+"socialNetworkList.address");
        String socialNetworkListType = hashMap.get(key+".socialNetworkList.type");
        String[] socialNetworkListAddresses;
        String[] socialNetworkListTypes;

        if(socialNetworkListAddress != null && socialNetworkListType != null) {
            socialNetworkListAddresses = socialNetworkListAddress.split(",");
            socialNetworkListTypes = socialNetworkListType.split(",");


            if (socialNetworkListAddresses.length != socialNetworkListTypes.length) {
                throw new NullPointerException("Social NetWork address and type size are not equal");
            }

            try {
                for (int i = 0; i < socialNetworkListAddresses.length; ++i) {
                    socialNetworkList.add(new SocialNetwork(SocialNetworkEnum.valueOf(socialNetworkListTypes[0]), socialNetworkListAddresses[0]));
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("SocialNetworkEnum value doesn't exist in the enum");
            }
        }
        return(socialNetworkList);
    }

    /**
     * Permet de récuperer une liste de String, présente dans la String d'ogirine sous la fome : str1,str2,str3
     * La string à splitter possède la clef de la map : key
     * @param hashMap
     * @param key
     * @return
     */
    private List<String> getListFromMap(HashMap<String, String> hashMap, String key ){
        String str = hashMap.get(key);
        LinkedList<String> Str_List = new LinkedList<>();

        if(!str.equals("") && str != null){
            System.out.println("STR NOT NULL");
            String[] arrayString = str.split(",");
            System.out.println("SPLIT sur un élement");
            for(String string: arrayString){
                System.out.println(string);
                Str_List.add(string);
                System.out.println("Ajout dans a liste");
            }
        }
        return(Str_List);
    }

    private PersonContactInformation getPersonContactInformationFormHashMap(HashMap<String, String> hashMap){
         Address address = getAddressFormHashMap(hashMap, "personContactInformation");
         List<SocialNetwork> socialNetworkList = getSocialNetworkListFromHashMap(hashMap, "personContactInformation");
         String firstName = hashMap.get("personContactInformation.firstName");
         String lastName = hashMap.get("personContactInformation.lastName");
         String phone = hashMap.get("personContactInformation.phone");
         String email = hashMap.get("personContactInformation.email");
         String website = hashMap.get("personContactInformation.website");
        return(new PersonContactInformation(firstName, lastName, phone, email, address, website, socialNetworkList));
    }

    private CompanyContactInformation getCompanyContactInformationFromHashMap(HashMap<String, String> hashMap){
        Address address = getAddressFormHashMap(hashMap, "companyContactInformation");
        List<SocialNetwork> socialNetworkList = getSocialNetworkListFromHashMap(hashMap, "companyContactInformation");
        String name = hashMap.get("companyContactInformation.name");
        String phone = hashMap.get("companyContactInformation.phone");
        String email = hashMap.get("companyContactInformation.email");
        String website = hashMap.get("companyContactInformation.website");
        return(new CompanyContactInformation(name, phone, email, address, website, socialNetworkList));
    }

    private boolean hasCompanyContactInformation(HashMap<String, String> hashMap){
        String companyName = hashMap.get("companyContactInformation.name");
        System.out.println(hashMap.get("login") +" COMPANY "+ (!companyName.equals("") && companyName != null));
        return(!companyName.equals("") && companyName != null);
    }

    private boolean hasPersonContactInformation(HashMap<String, String> hashMap){
        String lastName = hashMap.get("personContactInformation.lastName");
        System.out.println(hashMap.get("login") +" CONTACT "+ (!lastName.equals("") && lastName != null));
        return(!lastName.equals("") && lastName != null);
    }

    private HashMap<String, String> ParsingLine(String[] line){
        HashMap<String , String> mapString = new HashMap<>();
        mapString.put("login" , line[0]);
        mapString.put("email", line[1]);
        mapString.put("category", line[2]);
        mapString.put("description", line[3]);
        mapString.put("siren", line[4]);

        mapString.put("competencies", line[28]);
        mapString.put("sectors", line[29]);
        mapString.put("fields", line[30]);

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

        return(mapString);
    }

    private UserDTO hashMapToUserDTO(HashMap<String, String> hashMap){
     PersonContactInformation personContactInformation = null;
     CompanyContactInformation companyContactInformation = null;

              if(hasCompanyContactInformation(hashMap)){
                  companyContactInformation = getCompanyContactInformationFromHashMap(hashMap);
              }

              if(hasPersonContactInformation(hashMap)){
                  personContactInformation = getPersonContactInformationFormHashMap(hashMap);
              }

     String login = hashMap.get("login");
     String email = hashMap.get("email");

     if(login == null || email == null){
         throw new NullPointerException("Login or Email NULL");
     }

     CategoryEnum category;

     try{
         category = getCategory(hashMap);
     }catch(IllegalArgumentException iAE){
        throw iAE;
     }

     String password = UUID.randomUUID().toString().substring(0,6);
     String description = hashMap.get("description");
     Location location = null;

     if(hasCompanyContactInformation(hashMap)){
         location = locationService.getLocationFromAddress(getAddressFormHashMap(hashMap, "companyContactInformation"));
     }else{
         location = locationService.getLocationFromAddress(getAddressFormHashMap(hashMap, "personContactInformation"));
     }

     List<String> competencies = getListFromMap(hashMap, "competencies");
     List<String> sectors = getListFromMap(hashMap, "sectors");
     List<FieldEnum> fields = getFieldList(hashMap);
     String siren = hashMap.get("siren");

     return(new UserDTO(login, password, email, location, category, description, null, personContactInformation, companyContactInformation,
     competencies, sectors, fields, siren, "fr", null));
 }

    private ResponseEntity<?> registerUser(UserDTO userDTO){
        return userRepository.findOneByLogin(userDTO.getLogin())
                .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                                .orElseGet(() -> {
                                    User user = userService.createUserInformation(
                                            userDTO.getLogin(),
                                            userDTO.getPassword(),
                                            userDTO.getEmail().toLowerCase(),
                                            userDTO.getLocation(),
                                            userDTO.getCategory(),
                                            userDTO.getDescription(),
                                            userDTO.getRaisonSociale(),
                                            userDTO.getPersonContactInformation(),
                                            userDTO.getCompanyContactInformation(),
                                            userDTO.getCompetencies(),
                                            userDTO.getSectors(),
                                            userDTO.getFields(),
                                            userDTO.getLangKey());
                                    String baseUrl = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getScheme() + // "http"
                                            "://" +                                // "://"
                                            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getServerName() +              // "myhost"
                                            ":" +                                  // ":"
                                            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getServerPort();               // "80"

                                    mailService.sendActivationEmail(user, baseUrl);
                                    return new ResponseEntity<>(HttpStatus.CREATED);
                                })
                );

    }

}
