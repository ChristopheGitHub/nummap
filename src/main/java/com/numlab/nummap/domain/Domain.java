package com.numlab.nummap.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by eisti on 3/24/15.
 */
/*public class Domain implements Serializable {*/
@Document(collection = "T_DOMAIN")
public class Domain implements Serializable {

    @Id
    private String id;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Domain{" +
            "name='" + name + '\'' +
            '}';
    }
}

