package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by christo on 26/04/15.
 * A School, for the form of students and professors.
 */
@Document(collection = "T_SCHOOL")
public class School extends AbstractAuditingEntity implements Serializable{

    @Id
    private String id;

    private String name;
    private String completeName;
    private String description;
    private String website;
    private Address address;
    private Location location;

    @JsonCreator
    public School(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("completeName") String completeName,
        @JsonProperty("description") String description,
        @JsonProperty("website") String website,
        @JsonProperty("Address") Address address) {
        this.id = id;
        this.name = name;
        this.completeName = completeName;
        this.description = description;
        this.website = website;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Schools{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", completeName='" + completeName + '\'' +
            ", address=" + address +
            ", website=" + website +
            ", location=" + location +
            '}';
    }
}
