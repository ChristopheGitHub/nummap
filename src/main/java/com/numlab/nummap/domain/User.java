package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numlab.nummap.domain.enumerations.CategoryEnum;
import com.numlab.nummap.domain.enumerations.CustomersTypeEnum;
import com.numlab.nummap.domain.enumerations.FieldEnum;
import com.numlab.nummap.domain.enumerations.SectorEnum;
import com.numlab.nummap.domain.enumerations.RaisonSocialeEnum;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A user.
 */
@Document(collection = "T_USER")
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$")
    @Size(min = 1, max = 50)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @Email
    @Size(max = 100)
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
    private boolean activated = false;
    private boolean validatedByAdmin = false;

    @Size(min = 2, max = 5)
    @Field("lang_key")
    private String langKey;

    @Size(max = 20)
    @Field("activation_key")
    private String activationKey;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() { return description ;}

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<CustomersTypeEnum> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomersTypeEnum> customers) {
        this.customers = customers;
    }

    public List<FieldEnum> getFields() {
        return fields;
    }

    public void setFields(List<FieldEnum> fields) {
        this.fields = fields;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
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

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
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
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", authorities=" + authorities +
            '}';
    }

    public boolean isValidatedByAdmin() {
        return validatedByAdmin;
    }

    public void setValidatedByAdmin(boolean validatedByAdmin) {
        this.validatedByAdmin = validatedByAdmin;
    }
}
