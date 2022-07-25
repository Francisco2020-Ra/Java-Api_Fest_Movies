package com.informatorio.festmovies.service;

import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.exception.ExistException;

public interface CharacterService {

    CharacterDTO addCharacter(CharacterDTO characterDTO) throws ExistException;
}
