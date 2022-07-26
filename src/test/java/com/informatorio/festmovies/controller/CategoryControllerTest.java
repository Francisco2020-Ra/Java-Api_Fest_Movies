package com.informatorio.festmovies.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void when_receiveACategoryDTO_then_saveCategoryAndReturnWhithId() throws Exception {
        when(categoryRepository.save(categoryEntity(null, "DRAMA"))).thenReturn(categoryEntity(1L, "DRAMA"));
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity(null, "DRAMA"))))
                .andExpect(jsonPath("$.name", is("DRAMA")));
    }

    @Test
    void when_receiveACategoryDTOExist_then_returnMessageCategoryExist() throws Exception {
        when(categoryRepository.existsCategoryByName("DRAMA")).thenReturn(true);
        mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity(1L, "DRAMA"))))
                .andExpect(jsonPath("$.message", is("Existing category")));
    }

    @Test
    void when_receiveACategoryDTOWhitoutName_then_returnNameNonNull() throws Exception {
        mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity(1L, " "))))
                .andExpect(jsonPath("$.listMessage.[0]", is("name must not be blank")));
    }

    @Test
    void when_updateCategoryNonExistent_then_returnNotFound() throws Exception {

        when(categoryRepository.findById(categoryEntity(1L, "ACCION").getId())).thenReturn(Optional.empty());
        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity(1L, "DRAMA"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Not found category id: 1")));
    }

    @Test
    void when_updateCategory_then_returnOk() throws Exception {
        when(categoryRepository.findById(categoryEntity(1L, "ACCION").getId())).thenReturn(Optional.of(categoryEntity(1L, "ACCION")));
        when(categoryRepository.save(categoryEntity(1L, "DRAMA"))).thenReturn(categoryEntity(1L, "DRAMA"));

        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity(1L, "DRAMA"))))
                .andExpect(status().isOk());
    }

    @Test
    void when_getAllCategory_then_returnListCategory() throws Exception {
        List<CategoryEntity> list = Arrays.asList(
                categoryEntity(1L, "ACCION"),
                categoryEntity(2L, "DRAMA")
        );

        when(categoryRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    @Test
    void when_deleteCategoryNonExistent_then_notFound() throws Exception {
        when(categoryRepository.findById(categoryEntity(1L, "DRAMA").getId())).thenReturn(Optional.empty());
        mockMvc.perform(delete("/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Not found category id: 1")));
    }

    @Test
    void when_deleteCategory_then_noContent() throws Exception {
        when(categoryRepository.findById(categoryEntity(1L, "DRAMA").getId())).thenReturn(Optional.of(categoryEntity(1L, "DRAMA")));

        mockMvc.perform(delete("/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private CategoryEntity categoryEntity(Long id, String name) {
        return CategoryEntity
                .builder()
                .id(id).name(name)
                .build();
    }
}