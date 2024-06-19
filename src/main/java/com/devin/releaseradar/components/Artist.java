package com.devin.releaseradar.components;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Artist {

    private String name;
    @Id
    private String id;

    public Artist() {

    }
    
    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String toString() {
        return "Name: " + getName() + " Id: " + getId();
    }
}
