package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieDTO movieDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllMovie() throws ResourceNotFoundException {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }


}
