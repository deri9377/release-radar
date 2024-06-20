package com.devin.releaseradar.service;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devin.releaseradar.components.Album;
import com.devin.releaseradar.components.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.devin.releaseradar.components.Token;

@Service
public class TrackService {
    

    public Collection<Track> getSongsFromAlbum(Album album, Token token) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://api.spotify.com/v1/albums/" + album.getId() + "/tracks";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token.getAccess_token());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class);

        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray items = jsonObject.getJSONArray("items");
            try {
                Collection<Track> tracks = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(
                    items.toString(), new TypeReference<Collection<Track>>() { }
                );
                return tracks;
            } catch (JsonProcessingException e) {
                System.out.println(e);
            }
        }catch (JSONException err){
            System.out.print(err);
        }
        return null;
    }
}
