package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.entities.CharacterEntity;
import com.informatorio.festmovies.entities.MovieEntity;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.mapper.MovieMapper;
import com.informatorio.festmovies.repository.CategoryRepository;
import com.informatorio.festmovies.repository.CharacterRepository;
import com.informatorio.festmovies.repository.MovieRepository;
import com.informatorio.festmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CategoryRepository categoryRepository,
                            CharacterRepository characterRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) throws ResourceNotFoundException {
        MovieEntity movieEntity = movieMapper.toEntity(movieDTO);
        categoryRepository.findById(movieDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found category id: " + movieDTO.getCategory().getId()));
        MovieEntity movieEntity1 = movieRepository.save(movieEntity);
        return movieMapper.toDTO(movieEntity1);
    }

    @Override
    public List<MovieDTO> getAllMovies() throws ResourceNotFoundException {
        List<MovieEntity> listMovie = movieRepository.findAll();
        if (listMovie.size() == 0) {
            throw new ResourceNotFoundException("List Empty");
        }
        return movieMapper.toListMovieDTO(listMovie);
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) throws ResourceNotFoundException {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        MovieEntity updateMovie = movieMapper.setMovieEntity(movieEntity, movieDTO);
        MovieEntity movie = movieRepository.save(updateMovie);
        return movieMapper.toDTO(movie);
    }

    @Override
    public void deleteMovieId(Long id) throws ResourceNotFoundException {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found id: " + id));

        movieRepository.delete(movie);
    }

    @Override
    public MovieDTO addCharacterToMovie(Long idMovie, List<Long> characterId) throws ResourceNotFoundException {
        MovieEntity movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new ResourceNotFoundException("Not found id: " + idMovie));

        List<CharacterEntity> characters = new ArrayList<>();
        for (Long id : characterId) {
            characters.add(characterRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found id: " + id)));
        }
        characters.forEach(movie::addCharacter);
        movieRepository.save(movie);

        return movieMapper.toFullMovieDTO(movie);
    }



}
