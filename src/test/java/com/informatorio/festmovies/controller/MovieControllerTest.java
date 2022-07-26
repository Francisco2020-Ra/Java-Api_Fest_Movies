package com.informatorio.festmovies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.entities.CharacterEntity;
import com.informatorio.festmovies.entities.MovieEntity;
import com.informatorio.festmovies.repository.CategoryRepository;
import com.informatorio.festmovies.repository.CharacterRepository;
import com.informatorio.festmovies.repository.MovieRepository;


import lombok.var;
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
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CharacterRepository characterRepository;

    /*---------------------------------Create Movie Test ------------------------------*/
    @Test
    void when_receiveMovieDTOWhitCategoryNonExistent_then_returnNotFound() throws Exception {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

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
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity()));
        when(movieRepository.save(movieEntity(null, categoryEntity()))).thenReturn(movieEntity(1L, categoryEntity()));

        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieEntity(null, categoryEntity()))))
                .andExpect(jsonPath("$.title", is("Baty: Una Gatita Coreana")))
                .andExpect(jsonPath("$.category.id", is(1)));
    }

    /*---------------------------------Read Movie Test ------------------------------*/
    @Test
    void when_requestsAllMoviesAndListIsEmpty_then_returnListEmpty() throws Exception {
        when(movieRepository.findAll()).thenReturn(listMovieEntity());

        mockMvc.perform(get("/movie").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("List Empty")));
    }

    @Test
    void when_requestsAllMovies_then_returnListMovies() throws Exception {
        var list = listMovieEntity(
                movieEntity(1L, categoryEntity()),
                movieEntity(2L, categoryEntity())
        );
        when(movieRepository.findAll()).thenReturn(list);

        mockMvc.perform(get("/movie").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    /*---------------------------------Update Movie Test ------------------------------*/
    @Test
    void when_receiveAMovieNonExistent_then_returnNotFound() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/movie/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieEntity(1L, categoryEntity()))))
                .andExpect(jsonPath("$.message", is("Not found id: 1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_updateAMovie_then_returnOk() throws Exception {
        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movieEntity(1L, categoryEntity())));
        when(movieRepository.save(movieEntity(1L, categoryEntity())))
                .thenReturn(movieEntity(1L, categoryEntity()));

        mockMvc.perform(put("/movie/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(movieEntity(1L, categoryEntity()))))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(status().isOk());
    }

    /*---------------------------------Delete Movie Test ------------------------------*/
    @Test
    void when_callDeleteMethodUnregisteredId_then_returnNotFound() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/movie/1"))
                .andExpect(jsonPath("$.message", is("Not found id: 1")))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_callDeleteMethod_then_returnIsNoContent() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity(1L, categoryEntity())));

        mockMvc.perform(delete("/movie/1")).andExpect(status().isNoContent());
    }

    /*---------------------------------AddCharacterToMovie  Test ------------------------------*/

    @Test
    void when_receiveAIdMovieNonExistent_then_returnNotFoundId() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/movie/1/character").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(1,2,3))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Not found id: 1")));
    }

    @Test
    void when_receiveAIdMovieExistentAndThereAreNotCharacter_then_returnNotFoundId() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity(1L, categoryEntity())));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(characterEntity(1L)));
        when(characterRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/movie/1/character").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(1,2))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Not found id: 2")));
    }

    @Test
    void when_receiveAIdMovieExistentAndExistCharacter_then_returnCreated() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity(1L, categoryEntity())));

        when(characterRepository.findById(1L)).thenReturn(Optional.of(characterEntity(1L)));
        when(characterRepository.findById(2L)).thenReturn(Optional.of(characterEntity(2L)));

        mockMvc.perform(post("/movie/1/character").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(1,2))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.category.id", is(1)))
                .andExpect(jsonPath("$.characters.[0].id", is(2)))
                .andExpect(jsonPath("$.characters.[1].id", is(1)));

    }


    private MovieEntity movieEntity(Long id, CategoryEntity categoryEntity) {
        Set<CharacterEntity> character = new HashSet<>();
        return MovieEntity.builder()
                .id(id)
                .title("Baty: Una Gatita Coreana")
                .description("Baty: es una gatita coreana dramatica")
                .duration(54.5D)
                .inscription(LocalDate.parse("2022-02-13"))
                .category(categoryEntity)
                .characters(character)
                .build();
    }

    private CategoryEntity categoryEntity() {
        return CategoryEntity.builder()
                .id(1L)
                .name("DRAMA")
                .build();
    }

    private List<MovieEntity> listMovieEntity(MovieEntity... movieEntity) {
        return Arrays.asList(movieEntity);
    }

    private CharacterEntity characterEntity(Long id){
        Set<MovieEntity> movieEntities = new HashSet<>();
        movieEntities.add(movieEntity(1L, categoryEntity()));
        return CharacterEntity.builder()
                .id(id).name("Baty").lastName("Batman")
                .birthDate(LocalDate.parse("2021-02-13")).passport(34599434)
                .movies(movieEntities)
                .build();
    }
}