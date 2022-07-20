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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
                .content(objectMapper.writeValueAsString(categoryEntity(1L, "DRAMA"))))
                .andExpect(jsonPath("$.name", is("DRAMA")));
    }

    @Test
    void when_reciveACategoryDTOExist_then_returnMessageCategoryExist() throws Exception {
        when(categoryRepository.existsCategoryByName("DRAMA")).thenReturn(true);
        mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryEntity(1L, "DRAMA"))))
                .andExpect(jsonPath("$.message", is("Existing category")));
    }

    @Test
    void when_reciveACategoryDTOWhitoutName_then_returnNameNonNull() throws Exception {
        mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryEntity(1L, " "))))
                .andExpect(jsonPath("$.listMessage.[0]", is("name must not be blank")));
    }


    private CategoryEntity categoryEntity(Long id, String name){
        return CategoryEntity
                .builder()
                .id(id).name(name)
                .build();
    }
}