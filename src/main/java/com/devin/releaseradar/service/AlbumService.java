package com.devin.releaseradar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devin.releaseradar.components.Album;
import com.devin.releaseradar.repository.AlbumRepository;

@Service
public class AlbumService {
    
    @Autowired
    AlbumRepository albumRepository;

    public void save(Album album) {
        albumRepository.save(album);
    }

}
