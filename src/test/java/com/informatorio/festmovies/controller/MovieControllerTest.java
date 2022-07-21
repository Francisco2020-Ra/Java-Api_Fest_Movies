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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    void when_receiveMovieDTO_then_returnStatusCreated() throws Exception {
        when(movieRepository.save(movieEntity(null))).thenReturn(movieEntity(1L));

        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieEntity(null))))
                .andExpect(jsonPath("$.title", is("Baty: Una Gatita Coreana")))
                .andExpect(jsonPath("$.category.id", is(1)));
    }

    private MovieEntity movieEntity(Long id){
        return MovieEntity.builder()
        .id(id)
        .title("Baty: Una Gatita Coreana")
        .description("Baty: es una gatita coreana dramatica")
        .duration(54.5d)
        .inscription(LocalDate.parse("2022-02-13"))
        .category(categoryEntity())
        .build();
    }

    private CategoryEntity categoryEntity(){
        return CategoryEntity.builder()
                .id(1L)
                .name("DRAMA")
                .build();
    }
}