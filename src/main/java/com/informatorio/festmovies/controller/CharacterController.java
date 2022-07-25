package com.informatorio.festmovies.controller;


import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.exception.ExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/character")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService){
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<?> addCharacter(@Valid @RequestBody CharacterDTO characterDTO)
            throws ExistException {
        return new ResponseEntity<>(characterService.addCharacter(characterDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCharacter() throws ResourceNotFoundException {
        return new ResponseEntity<>(characterService.getAllCharacter(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharacter(@PathVariable Long id, @Valid @RequestBody CharacterDTO characterDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(characterService.updateCharacter(id, characterDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable Long id) throws ResourceNotFoundException {
        characterService.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
