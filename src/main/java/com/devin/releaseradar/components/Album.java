package com.devin.releaseradar.components;

import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Album {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "track_id")
    private Collection<Track> tracks;
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

    public Collection<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(Collection<Track> tracks) {
        this.tracks = tracks;
    }


    public Date getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }
    
    
    
    public String toString() {
        return "Album: " + getName() + " Id: " + getId() + " Date: " + getRelease_date();
    }
}
