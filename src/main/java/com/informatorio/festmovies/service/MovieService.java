package com.informatorio.festmovies.service;

import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.exception.ResourceNotFoundException;

public interface MovieService {

    MovieDTO addMovie(MovieDTO movieDTO) throws ResourceNotFoundException;
}
