package com.devin.releaseradar.components;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Track {
    @Id
    private String id;
    private String name;
    private String exturnal_urls;
    private Date release_date;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getExturnal_urls() {
        return this.exturnal_urls;
    }

    public void setExturnal_urls(String exturnal_urls) {
        this.exturnal_urls = exturnal_urls;
    }



    public Date getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }


}
