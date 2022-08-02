package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.DirectoryDTO;
import com.informatorio.festmovies.entities.DirectoryEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DirectoryMapper {

    public DirectoryEntity toEntity(DirectoryDTO directoryDTO){
        return DirectoryEntity.builder()
                .id(directoryDTO.getId())
                .name(directoryDTO.getName())
                .lastName(directoryDTO.getLastName())
                .birthDate(LocalDate.parse(directoryDTO.getBirthDate()))
                .passport(directoryDTO.getPassport())
                .inscription(LocalDate.parse(directoryDTO.getInscription()))
                .build();
    }

    public DirectoryDTO toDTO(DirectoryEntity directoryEntity){
        return DirectoryDTO.builder()
                .id(directoryEntity.getId())
                .name(directoryEntity.getName())
                .lastName(directoryEntity.getLastName())
                .birthDate(directoryEntity.getBirthDate().toString())
                .passport(directoryEntity.getPassport())
                .inscription(directoryEntity.getInscription().toString())
                .build();
    }

    public List<DirectoryDTO> toListDTO (List<DirectoryEntity> list){
        return list.stream()
                .map(this::toDTO)
                .collect(toList());
    }

    public DirectoryEntity setEntity(DirectoryEntity directory, DirectoryDTO directoryDTO){
        directory.setId(directoryDTO.getId());
        directory.setName(directoryDTO.getName());
        directory.setLastName(directoryDTO.getLastName());
        directory.setBirthDate(LocalDate.parse(directoryDTO.getBirthDate()));
        directory.setPassport(directoryDTO.getPassport());
        directory.setInscription(LocalDate.parse(directoryDTO.getInscription()));
        return directory;
    }
}
