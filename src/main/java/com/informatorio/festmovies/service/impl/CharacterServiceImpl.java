package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.entities.CharacterEntity;
import com.informatorio.festmovies.exception.ExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.mapper.CharacterMapper;
import com.informatorio.festmovies.repository.CharacterRepository;
import com.informatorio.festmovies.service.CharacterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterMapper characterMapper;
    private CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterMapper characterMapper,
                                CharacterRepository characterRepository){
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
    }
    @Override
    public CharacterDTO addCharacter(CharacterDTO characterDTO) throws ExistException {
        CharacterEntity character = characterMapper.toEntity(characterDTO);
        if(characterRepository.existsCharacteryByName(character.getName())){
            throw new ExistException("Character " +character.getName()+ " exist");
        }
        CharacterEntity characterEntity = characterRepository.save(character);

        return characterMapper.toDTO(characterEntity);
    }

    @Override
    public List<CharacterDTO> getAllCharacter() throws ResourceNotFoundException {
        List<CharacterEntity> listCharacter = characterRepository.findAll();
        if(listCharacter.isEmpty()){
            throw new ResourceNotFoundException("List Empty");
        }
        return characterMapper.toListDTOCharacter(listCharacter);
    }

    @Override
    public CharacterDTO updateCharacter(Long id, CharacterDTO characterDTO) throws ResourceNotFoundException {
        CharacterEntity character = characterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        CharacterEntity characterSet = characterMapper.toSet(character, characterDTO);
        CharacterEntity characterSave = characterRepository.save(characterSet);
        return characterMapper.toDTO(characterSave);
    }

    @Override
    public void deleteCharacter(Long id) throws ResourceNotFoundException {
        characterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        characterRepository.deleteById(id);
    }
}
