package com.numlab.nummap.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.numlab.nummap.domain.CompanyContactInformation;
import com.numlab.nummap.domain.Location;
import com.numlab.nummap.domain.PersonContactInformation;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.CustomersTypeEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.domain.enumerations.SectorEnum;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private Location location;
    private CategoryEnum category;
    private String description;
    private String raisonSociale;
    private PersonContactInformation personContactInformation;
    private CompanyContactInformation companyContactInformation;
    private List<String> competencies;
    private List<SectorEnum> sectors;
    private List<FieldEnum> fields;
    private List<CustomersTypeEnum> customers;
    private String siren;
    private boolean validatedByAdmin;

    @Size(min = 2, max = 5)
    private String langKey;

    private List<String> roles;

    public UserDTO() {
    }

    @JsonCreator
    public UserDTO(
        @JsonProperty("login") String login,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email,
        @JsonProperty("category") CategoryEnum category,
        @JsonProperty("description") String description,
        @JsonProperty("raisonSociale") String raisonSociale,
        @JsonProperty("PersonContactInformation") PersonContactInformation personContactInformation,
        @JsonProperty("CompanyContactInformation") CompanyContactInformation companyContactInformation,
        @JsonProperty("competencies") List<String> competencies,
        @JsonProperty("sectors") List<SectorEnum> sectors,
        @JsonProperty("fields") List<FieldEnum> fields,
        @JsonProperty("customers") List<CustomersTypeEnum> customers,
        @JsonProperty("siren") String siren,
        @JsonProperty("langKey") String langKey,
        @JsonProperty("role") List<String> roles
    ) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.category = category;
        this.description = description;
        this.raisonSociale = raisonSociale;
        this.personContactInformation = personContactInformation;
        this.companyContactInformation = companyContactInformation;
        this.competencies = competencies;
        this.sectors = sectors;
        this.fields = fields;
        this.customers = customers;
        this.langKey = langKey;
        this.roles = roles;
        this.siren = siren;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
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

    public List<String> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(List<String> competencies) {
        this.competencies = competencies;
    }

    public List<SectorEnum> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorEnum> sectors) {
        this.sectors = sectors;
    }

    public List<FieldEnum> getFields() {
        return fields;
    }

    public void setFields(List<FieldEnum> fields) {
        this.fields = fields;
    }

    public List<CustomersTypeEnum> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomersTypeEnum> customers) {
        this.customers = customers;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", location=" + location +
            ", category=" + category +
            ", description='" + description + '\'' +
            ", raisonSociale='" + raisonSociale + '\'' +
            ", personContactInformation=" + personContactInformation +
            ", companyContactInformation=" + companyContactInformation +
            ", competencies=" + competencies +
            ", sectors=" + sectors +
            ", fields=" + fields +
            ", customers=" + customers +
            ", siren='" + siren + '\'' +
            ", langKey='" + langKey + '\'' +
            ", roles=" + roles +
            '}';
    }

    public static UserDTO fromJsonToUserDTO(String json) {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userDTO = new UserDTO();
        try {
            final JsonNode responsePerson = mapper.readTree(json);
            userDTO = mapper.reader(UserDTO.class).readValue(responsePerson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    public boolean isValidatedByAdmin() {
        return validatedByAdmin;
    }

    public void setValidatedByAdmin(boolean validatedByAdmin) {
        this.validatedByAdmin = validatedByAdmin;
    }
}
