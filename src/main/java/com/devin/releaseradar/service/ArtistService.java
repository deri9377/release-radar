package com.devin.releaseradar.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devin.releaseradar.components.Album;
import com.devin.releaseradar.components.Artist;
import com.devin.releaseradar.components.Token;
import com.devin.releaseradar.repository.AlbumRepository;
import com.devin.releaseradar.repository.ArtistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;
    
    private List<String> names;
    private List<Artist> artists;
    private Token token;


    public List<Artist> parseArtistsNames(String unparsedNames) {
        names = new ArrayList<>();
        String [] nameList = unparsedNames.split(",");
        for (String name : nameList) {
            names.add(name.trim());
        }
        artists = new ArrayList<>();
        for (String name: names) {
            artists.add(createNewArtist(name));
        }
        return artists;
    }

    private Artist createNewArtist(String name) {
        System.out.println("finding " + name);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.spotify.com/v1/search?type=artist&q=" + name;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token.getAccess_token());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);
        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray items = jsonObject.getJSONObject("artists").getJSONArray("items");
            try {
                Collection<Artist> artists = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(
                    items.toString(), new TypeReference<Collection<Artist>>() { }
                );
                return parseArtists(artists, name);
            } catch (JsonProcessingException e) {
                System.out.println(e);
            }
        }catch (JSONException err){
            System.out.print(err);
        }

        return null;


    }

    public Collection<Album> getAlbumsByArtist(Artist artist) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.spotify.com/v1/artists/" + artist.getId() + "/albums";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token.getAccess_token());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);

        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray items = jsonObject.getJSONArray("items");
            try {
                Collection<Album> albums = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(
                    items.toString(), new TypeReference<Collection<Album>>() { }
                );
                return albums;
            } catch (JsonProcessingException e) {
                System.out.println(e);
            }
        }catch (JSONException err){
            System.out.print(err);
        }
        return null;
    }

    private Artist parseArtists(Collection<Artist> artists, String name) {
        for (Artist artist: artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void save(Artist artist) {
        artistRepository.save(artist);
    }

    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add);
        return artists;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
