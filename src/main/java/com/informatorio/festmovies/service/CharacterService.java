package com.informatorio.festmovies.service;

import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.exception.ExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;

import java.util.List;

public interface CharacterService {

    CharacterDTO addCharacter(CharacterDTO characterDTO) throws ExistException;

    List<CharacterDTO> getAllCharacter () throws ResourceNotFoundException;
}
