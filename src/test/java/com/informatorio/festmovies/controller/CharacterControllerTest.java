package com.informatorio.festmovies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.festmovies.entities.CharacterEntity;
import com.informatorio.festmovies.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CharacterControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CharacterRepository characterRepository;

    @Test
    void when_saveCharacterDTOExistent_then_returnConflict() throws Exception {
        when(characterRepository.existsCharacteryByName(getCharacter(1L, "Baty").getName())).thenReturn(true);
        mockMvc.perform(post("/character").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCharacter(1L, "Baty"))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Character Baty exist")));
    }

    @Test
    void when_receiveCharacterDTOMissingAttributes_then_returnNotNull() throws Exception {
        mockMvc.perform(post("/character")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCharacter(1L, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.listMessage.[0]", is("name must not be blank")));
    }

    @Test
    void when_saveCharacterDTONonExistent_then_returnCreated() throws Exception {
        when(characterRepository.existsCharacteryByName(getCharacter(1L, "Baty").getName())).thenReturn(false);
        when(characterRepository.save(getCharacter(1L, "Baty"))).thenReturn(getCharacter(1L, "Baty"));

        mockMvc.perform(post("/character").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCharacter(1L, "Baty"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Baty")));
    }

    private CharacterEntity getCharacter(Long id, String name) {
        return CharacterEntity.builder()
                .name(name)
                .lastName("Batman")
                .birthDate(LocalDate.parse("2021-06-02"))
                .passport(34599434)
                .build();
    }
}