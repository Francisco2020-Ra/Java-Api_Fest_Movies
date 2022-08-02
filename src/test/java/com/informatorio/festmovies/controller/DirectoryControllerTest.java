package com.informatorio.festmovies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.festmovies.entities.DirectoryEntity;
import com.informatorio.festmovies.repository.DirectoryRepository;
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
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class DirectoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DirectoryRepository directoryRepository;

    //------------------------------------Create Test Directory -------------------------
    @Test
    void when_callMethodAddDirectoryAndNonExistent_then_returnBadRequest() throws Exception {
        mockMvc.perform(post("/directory").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_callMethodAddDirectoryAndMissingRequiredAttributes_then_returnBadRequest() throws Exception {
        mockMvc.perform(post("/directory").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(directoryEntity(null, null))))
                .andExpect(jsonPath("$.listMessage.[0]",is("name must not be blank")))
                .andExpect(status().isBadRequest());
    }

    //------------------------------------Read Test Directory -------------------------
    @Test
    void when_callMethodGetAllDirectoryAndListEmpty_then_returnNotFound() throws Exception {
        when(directoryRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/directory"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("List Empty")));
    }

    @Test
    void when_callMethodGetAllDirectory_then_returnListDirectory() throws Exception {
        when(directoryRepository.findAll())
                .thenReturn(Arrays.asList(
                        directoryEntity(1L, "Francisco"),
                        directoryEntity(2L, "Maria")));

        mockMvc.perform(get("/directory"))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    //------------------------------------Update Test Directory -------------------------
    @Test
    void when_callMethodUpdateDirectoryAndNotExistId_then_returnNotFond() throws Exception {
        when(directoryRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/directory/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(directoryEntity(1L, "Francisco"))))
                .andExpect(jsonPath("$.message", is("Not found id: 1")));

    }

    @Test
    void when_callMethodUpdateDirectory_then_returnOk() throws Exception {
        when(directoryRepository.findById(1L)).thenReturn(Optional.of(directoryEntity(1L, "Francisco")));
        when(directoryRepository.save(directoryEntity(1L, "Ariel"))).thenReturn(directoryEntity(1L, "Ariel"));

        mockMvc.perform(put("/directory/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directoryEntity(1L, "Ariel"))))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ariel")));

    }

    @Test
    void when_callMethodUpdateDirectoryAndMissingRequiredAttributes_then_returnBadRequest() throws Exception {
        mockMvc.perform(put("/directory/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directoryEntity(1L, null))))
                .andExpect(jsonPath("$.listMessage.[0]",is("name must not be blank")))
                .andExpect(status().isBadRequest());

    }

    //------------------------------------Delete Test Directory -------------------------

    @Test
    void when_callMethodDeleteAndIdNotExist_then_returnNotFound() throws Exception {
        when(directoryRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/directory/1"))
                .andExpect(jsonPath("$.message", is("Not found id: 1")));
    }

    @Test
    void when_callMethodDelete_then_returnNotContent() throws Exception {
        when(directoryRepository.findById(1L)).thenReturn(Optional.of(directoryEntity(1L, "Francisco")));
        mockMvc.perform(delete("/directory/1"))
                .andExpect(status().isNoContent());
    }

    private DirectoryEntity directoryEntity(Long id, String name){
        return DirectoryEntity.builder()
                .id(id)
                .name(name)
                .lastName("Romero")
                .birthDate(LocalDate.parse("1990-02-13"))
                .passport(34599434)
                .inscription(LocalDate.parse("2022-03-12"))
                .build();
    }
}