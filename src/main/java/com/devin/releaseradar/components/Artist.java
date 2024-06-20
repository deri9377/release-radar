package com.devin.releaseradar.components;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {

    private String name;
    @Id
    private String id;
    @OneToMany
    @JoinColumn(name="album_id")
    private Collection<Album> albums;

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
    
    public Collection<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(Collection<Album> albums) {
        this.albums = albums;
    }


    public String toString() {
        return "Name: " + getName() + " Id: " + getId();
    }
}
