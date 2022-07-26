package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieDTO movieDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(movieService.updateMovie(id, movieDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) throws ResourceNotFoundException {
        movieService.deleteMovieId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/character")
    public ResponseEntity<?> addCharacterToMovie(@PathVariable Long id, @RequestBody List<Long> actorId) throws ResourceNotFoundException {
        return new ResponseEntity<>(movieService.addCharacterToMovie(id, actorId), HttpStatus.CREATED);
    }

}
