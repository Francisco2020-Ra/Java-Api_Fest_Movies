package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.entities.CharacterEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Component
public class CharacterMapper {

    public CharacterEntity toEntity(CharacterDTO characterDTO){
        return CharacterEntity.builder()
                .name(characterDTO.getName())
                .lastName(characterDTO.getLastName())
                .birthDate(LocalDate.parse(characterDTO.getBirthDate()))
                .passport(characterDTO.getPassport())
                .build();
    }

    public CharacterDTO toDTO(CharacterEntity characterEntity){
        return CharacterDTO.builder()
                .id(characterEntity.getId())
                .name(characterEntity.getName())
                .lastName(characterEntity.getLastName())
                .birthDate(characterEntity.getBirthDate().toString())
                .passport(characterEntity.getPassport())
                .build();
    }

    public List<CharacterDTO> toListDTOCharacter(List<CharacterEntity> characterList){
        return characterList.stream()
                .map(this::toDTO)
                .collect(toList());
    }
}
