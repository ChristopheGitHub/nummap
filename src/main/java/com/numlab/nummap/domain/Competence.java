package com.numlab.nummap.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eisti on 3/24/15.
 */

@Document(collection = "T_COMPETENCE")
public class Competence extends AbstractAuditingEntity implements Serializable {
    @Override
    public String toString() {
        return "Competence{" +
                "name='" + name + '\'' +
                '}';
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
