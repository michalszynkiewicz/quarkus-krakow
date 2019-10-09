package com.example.search;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 08/10/2019
 */
@Entity
public class PageKeyword extends PanacheEntity {
    public String page;
    public String keyword;
}
