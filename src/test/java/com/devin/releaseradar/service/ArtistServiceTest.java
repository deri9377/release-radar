package com.devin.releaseradar.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.devin.releaseradar.components.Artist;
import com.devin.releaseradar.components.Token;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    ArtistService artistService;

    @Test
    void parseArtistsTest() {
        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Bobby Flay"));
        artists.add(new Artist("Mac Miller"));

        assertTrue(artistService.parseArtists(artists, "Mac Miller").getName().equals("Mac Miller"));
    }

    @Test
    void createNewArtistTest() {
        Artist artist = new Artist("Mac Miller");
        Token fakeToken = new Token("1", "1", 1);
        String resourceUrl = "https://api.spotify.com/v1/search?type=artist&q=" + artist.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ fakeToken.getAccess_token());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        JSONObject jsonArtists = new JSONObject();
        JSONObject jsonItems = new JSONObject();
        JSONArray jsonList = new JSONArray();

        jsonList = jsonList.put(new JSONObject().put("name", "Mac Miller"));
        jsonList = jsonList.put(new JSONObject().put("name", "Mac Miller"));
        jsonItems = jsonItems.put("items", jsonList);
        jsonArtists = jsonArtists.put("artists", jsonItems);
        System.out.println(jsonArtists.toString());

        Mockito
            .when(restTemplate.exchange(resourceUrl, HttpMethod.GET, requestEntity, String.class))
            .thenReturn(new ResponseEntity(jsonArtists.toString(), HttpStatus.OK));

        assertTrue(artist.equals(artistService.createNewArtist("Mac Miller", fakeToken)));

        
    }

}
