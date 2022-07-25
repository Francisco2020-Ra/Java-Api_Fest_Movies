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
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    /*---------------------------------Create Character Test ------------------------------*/
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
        when(characterRepository.save(getCharacter(null, "Baty"))).thenReturn(getCharacter(1L, "Baty"));

        mockMvc.perform(post("/character").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCharacter(1L, "Baty"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Baty")));
    }

    /*---------------------------------Read Character Test ------------------------------*/
    @Test
    void when_callMethodGetAllCharacterAndThereAreNoCharacters_then_returnListEmpty() throws Exception {
        when(characterRepository.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/character"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("List Empty")));
    }

    @Test
    void when_callMethodGetAllCharacterAndCharactersExist_then_returnListCharacters() throws Exception {
        when(characterRepository.findAll())
                .thenReturn(
                        Arrays.asList(
                                getCharacter(1L, "Baty"),
                                getCharacter(2L, "Batman"))
                );

        mockMvc.perform(get("/character"))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    /*---------------------------------Update Character Test ------------------------------*/

    @Test
    void when_callMethodUpdateCharacterAndThereAreNoCharacters_then_returnIdNonExistent() throws Exception {
        when(characterRepository.findById(1l)).thenReturn(Optional.empty());

        mockMvc.perform(put("/character/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCharacter(1L, "Baty"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Not found id: 1")));
    }

    @Test
    void when_callMethodUpdateCharacter_then_returnCreated() throws Exception {
        when(characterRepository.findById(1l)).thenReturn(Optional.of(getCharacter(1L,"Baty")));
        when(characterRepository.save(getCharacter(1L, "Baty"))).thenReturn(getCharacter(1L, "Baty"));

        mockMvc.perform(put("/character/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCharacter(1L, "Baty"))))
                .andExpect(jsonPath("$.name", is("Baty")))
                .andExpect(status().isCreated());
    }

    /*---------------------------------Delete Character Test ------------------------------*/

    @Test
    void when_callMethodDeleteAndThereAreNoCharacters_then_returnIdNonExistent() throws Exception {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/character/1")).andExpect(jsonPath("$.message", is("Not found id: 1")));
    }
    @Test
    void when_callMethdoDeleteAndExistCharacter_then_returnNoContent() throws Exception {
        when(characterRepository.findById(1L)).thenReturn(Optional.of(getCharacter(1L, "Baty")));

        mockMvc.perform(delete("/character/1")).andExpect(status().isNoContent());
    }

    private CharacterEntity getCharacter(Long id, String name) {
        return CharacterEntity.builder()
                .id(id)
                .name(name)
                .lastName("Batman")
                .birthDate(LocalDate.parse("2021-06-02"))
                .passport(34599434)
                .build();
    }
}