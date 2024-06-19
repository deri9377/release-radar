package com.devin.releaseradar.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.devin.releaseradar.components.Artist;
import com.devin.releaseradar.components.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArtistService {
    
    private List<String> names;
    private List<Artist> artists;
    private Token token;

    public ArtistService(Token token) {
        names = new ArrayList<>();
        artists = new ArrayList<>();
        this.token = token;
    }


    public void parseArtistsNames(String unparsedNames) {
        String [] nameList = unparsedNames.split(",");
        for (String name : nameList) {
            names.add(name.trim());
        }

        for (String name: names) {
            artists.add(createNewArtist(name));
        }
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

    public List<Artist> getArtists() {
        return artists;
    }

}
