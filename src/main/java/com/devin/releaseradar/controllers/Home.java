package com.devin.releaseradar.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.devin.releaseradar.components.Album;
import com.devin.releaseradar.components.Artist;
import com.devin.releaseradar.components.Client;
import com.devin.releaseradar.components.Token;
import com.devin.releaseradar.repository.AlbumRepository;
import com.devin.releaseradar.repository.ArtistRepository;
import com.devin.releaseradar.service.AlbumService;
import com.devin.releaseradar.service.ArtistService;
import com.devin.releaseradar.service.TrackService;
import com.devin.releaseradar.service.AuthService;
import com.devin.releaseradar.service.Greeting;


@Controller
public class Home {

    @Autowired
    ArtistService artistService;

    @Autowired
    AlbumService albumService;

    @Autowired
    TrackService trackService;

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("artists", artistService.getArtists());
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }
    
    @PostMapping("/")
    public String postMethodName(@ModelAttribute Greeting greeting, Model model) {
        //TODO: process POST request
        //generate token
        AuthService authService = new AuthService();
        Token token = authService.getAuth(new Client("3e078e19a7584e069eacd7cd4a6e4fac", "b922b2a704e64e65a87438e01ba3b46a"));
        artistService.setToken(token);
        System.out.println(token.getAccess_token());

        //parse names into new users
        System.out.println("Parsing Users");
        List<Artist> artists = artistService.parseArtistsNames(greeting.getName());
        //add greeting artists names to db
        System.out.println("Collecting Data");
        for (Artist artist: artists) {
            System.out.println(artist);
            Collection<Album> albums = artistService.getAlbumsByArtist(artist);
            for (Album album : albums) {
                albumService.save(album);
                System.out.println(album);
            }
            artist.setAlbums(albums);
            artistService.save(artist);
        }

        //do processing of data/api calls to get songs
        



        //return with a list of the albums within the date range
        System.out.println("Filtering Albums");
        artists = artistService.getArtists();
        for (Artist artist: artists) {
            Collection<Album> albums = artist.getAlbums();
            Collection<Album> newAlbums = new ArrayList<>();
            for (Album album: albums) {
                if (album.getRelease_date().after(greeting.getDate())) {
                    newAlbums.add(album);
                }
            }
            artist.setAlbums(newAlbums);
        }

        //get Songs from those albums
        for (Artist artist : artists) {
            for (Album album : artist.getAlbums()) {
                album.setTracks(trackService.getSongsFromAlbum(album, token));
            }
        }

        model.addAttribute("artists", artists);
        model.addAttribute("greeting", greeting);
        return "result";
    }
    

}
