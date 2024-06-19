package com.devin.releaseradar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.devin.releaseradar.components.Artist;
import com.devin.releaseradar.components.Client;
import com.devin.releaseradar.components.Token;
import com.devin.releaseradar.repository.ArtistRepository;
import com.devin.releaseradar.service.ArtistService;
import com.devin.releaseradar.service.AuthService;
import com.devin.releaseradar.service.Greeting;


@Controller
public class Home {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }
    
    @PostMapping("/")
    public String postMethodName(@ModelAttribute Greeting greeting, Model model) {
        //TODO: process POST request
        //generate token
        AuthService authService = new AuthService();
        Token token = authService.getAuth(new Client("3e078e19a7584e069eacd7cd4a6e4fac", "b922b2a704e64e65a87438e01ba3b46a"));
        System.out.println(token.getAccess_token());

        //parse names into new users
        ArtistService artistService = new ArtistService(token);
        artistService.parseArtistsNames(greeting.getName());
        List<Artist> artists = artistService.getArtists();
        //add greeting artists names to db
        for (Artist artist: artists) {
            artistRepository.save(artist);
            System.out.println(artist);
        }

        //do processing of data/api calls to get songs
        


        //return with a list of the songs
        model.addAttribute("greeting", greeting);
        return "result";
    }
    

}
