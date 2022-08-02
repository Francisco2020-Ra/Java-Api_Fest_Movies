package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.dto.DirectoryDTO;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.service.DirectoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/directory")
public class DirectoryController {

    private DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService){
        this.directoryService = directoryService;
    }

    @PostMapping
    public ResponseEntity<?> addDirectory(@Valid @RequestBody DirectoryDTO directoryDTO){
        return new ResponseEntity<>(directoryService.addDirectory(directoryDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllDirectory() throws ResourceNotFoundException {
        return new ResponseEntity<>(directoryService.getAllDirectory(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDirectory(@PathVariable Long id,@Valid @RequestBody DirectoryDTO directoryDTO)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(directoryService.updateDirectory(id, directoryDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirectory(@PathVariable Long id) throws ResourceNotFoundException {
        directoryService.deleteDirectory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
