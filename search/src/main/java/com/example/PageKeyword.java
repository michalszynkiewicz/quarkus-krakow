package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class PageKeyword extends PanacheEntity {
    public String page;
    public String keyword;
}
