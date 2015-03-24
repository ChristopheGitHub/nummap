package com.numlab.nummap.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by eisti on 3/24/15.
 */


@Document(collection = "T_DOMAIN")
public class Domain extends AbstractAuditingEntity implements Serializable {

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
