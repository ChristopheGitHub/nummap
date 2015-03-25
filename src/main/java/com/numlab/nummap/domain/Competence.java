package com.numlab.nummap.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eisti on 3/24/15.
 */

@Document(collection = "T_COMPETENCE")
public class Competence implements Serializable {

    @Id
    String Id;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Competence{" +
            "name='" + name + '\'' +
            '}';
    }
}
