package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.entities.MovieEntity;
import com.informatorio.festmovies.mapper.MovieMapper;
import com.informatorio.festmovies.repository.MovieRepository;
import com.informatorio.festmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper, MovieRepository movieRepository){
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }
    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        MovieEntity movieEntity = movieMapper.toEntity(movieDTO);
        MovieEntity movieEntity1 = movieRepository.save(movieEntity);
        return movieMapper.toDTO(movieEntity1);
    }
}
