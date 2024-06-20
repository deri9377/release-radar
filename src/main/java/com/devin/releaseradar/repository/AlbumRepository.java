package com.devin.releaseradar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devin.releaseradar.components.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    
}
