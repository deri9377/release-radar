package com.devin.releaseradar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devin.releaseradar.components.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, String> {
    
}