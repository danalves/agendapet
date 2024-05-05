package com.danalves.agendapet.controller;

import com.danalves.agendapet.dto.NewAnimalRequest;
import com.danalves.agendapet.service.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<NewAnimalRequest> newAnimalRequestJson;

    @MockBean
    private AnimalService animalService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void addAnimalCase1() throws Exception {
        var response = mvc.perform(post("/api/animal/add"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void addAnimalCase2() throws Exception {
        var response = mvc.perform(post("/api/animal/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAnimalRequestJson.write(
                                new NewAnimalRequest("Theo", "Gato", "SRD", "Macho", "01.01.2022")
                            ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

}