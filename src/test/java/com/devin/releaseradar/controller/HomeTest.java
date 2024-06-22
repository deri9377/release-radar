package com.devin.releaseradar.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devin.releaseradar.controllers.Home;
import com.devin.releaseradar.repository.ArtistRepository;
import com.devin.releaseradar.repository.AlbumRepository;
import com.devin.releaseradar.service.AlbumService;
import com.devin.releaseradar.service.ArtistService;
import com.devin.releaseradar.service.TrackService;


@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HomeTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void getMethodNameTest() throws Exception {
        
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("artists"))
            .andExpect(model().attributeExists("greeting"));
    }

    @Test
    void happyPath() throws Exception {
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/health-check"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString().equals("Happy!");

    }
}
