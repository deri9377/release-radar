package com.devin.releaseradar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devin.releaseradar.components.Album;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Test
    void saveAlbum() {
        Album album = new Album("Circles");
        album.setId("1");
        albumService.save(album);
        
        assertEquals(album, albumService.getAlbumById((album.getId())));

    }


}
