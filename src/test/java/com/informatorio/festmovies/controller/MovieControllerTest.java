package com.informatorio.festmovies.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.entities.MovieEntity;
import com.informatorio.festmovies.repository.MovieRepository;

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
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void when_receiveMovieDTOWhitCategoryNonExistent_then_returnNotFound() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieEntity(1L, categoryEntity()))))
                .andExpect(jsonPath("$.message", is("Not found category id: 1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_receiveMovieDTOWhitoutCategory_then_returnBadRequest() throws Exception {
        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieEntity(1L, null))))
                .andExpect(jsonPath("$.listMessage.[0]", is("category must not be null")))
                .andExpect(status().isBadRequest());
    }
    @Test
    void when_receiveMovieDTO_then_returnStatusCreated() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity(1L, categoryEntity())));
        when(movieRepository.save(movieEntity(null, categoryEntity()))).thenReturn(movieEntity(1L, categoryEntity()));

        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieEntity(null, categoryEntity()))))
                .andExpect(jsonPath("$.title", is("Baty: Una Gatita Coreana")))
                .andExpect(jsonPath("$.category.id", is(1)));
    }

    private MovieEntity movieEntity(Long id, CategoryEntity categoryEntity){
        return MovieEntity.builder()
        .id(id)
        .title("Baty: Una Gatita Coreana")
        .description("Baty: es una gatita coreana dramatica")
        .duration(54.5D)
        .inscription(LocalDate.parse("2022-02-13"))
        .category(categoryEntity)
        .build();
    }

    private CategoryEntity categoryEntity(){
        return CategoryEntity.builder()
                .id(1L)
                .name("DRAMA")
                .build();
    }
}