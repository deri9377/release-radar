package com.devin.releaseradar.service;


import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.devin.releaseradar.components.Client;
import com.devin.releaseradar.components.Token;


//rootProject.secret = "b922b2a704e64e65a87438e01ba3b46a"
//rootPorject.id = "3e078e19a7584e069eacd7cd4a6e4fac"

@Service
public class AuthService {
    
    public Token getAuth(Client client) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://accounts.spotify.com/api/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", "3e078e19a7584e069eacd7cd4a6e4fac");
        map.add("client_secret", "b922b2a704e64e65a87438e01ba3b46a");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        Token response = restTemplate.postForObject(resourceUrl, request, Token.class);
        return response;
    }
}
