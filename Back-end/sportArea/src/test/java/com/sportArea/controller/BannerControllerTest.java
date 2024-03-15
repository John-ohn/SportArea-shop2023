package com.sportArea.controller;

import com.sportArea.entity.Banner;
import com.sportArea.service.BannerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BannerService bannerService;

    private Banner bannerOne;

    private Banner bannerSecond;

    @BeforeEach
    void createBanners() {
        bannerOne = new Banner(1L,
                "Banner1",
                "https://icon-imeg-store.ama.com/BANNER1.jpg");

        bannerSecond = new Banner(2L,
                "Banner2",
                "https://icon-imeg-store.ama.com/BANNER2.jpg");
    }

    @Test
    @DisplayName("Test BannerController method findAll")
    void testMethodFindAll() throws Exception {

        List<Banner> list = List.of(bannerOne, bannerSecond);

        when(bannerService.findAll()).thenReturn(list);

        mockMvc.perform(get("/api/v1/banners").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].bannerId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0]").value(bannerOne));
    }
}