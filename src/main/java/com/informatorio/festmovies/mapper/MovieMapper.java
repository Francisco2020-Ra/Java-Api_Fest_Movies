package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.entities.MovieEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MovieMapper {

    public MovieEntity toEntity(MovieDTO movieDTO){
        return MovieEntity.builder()
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .duration(movieDTO.getDuration())
                .inscription(LocalDate.parse(movieDTO.getInscription()))
                .category(getCategoryEntity(movieDTO))
                .build();
    }

    public CategoryEntity getCategoryEntity(MovieDTO movieDTO){
        return CategoryEntity.builder()
                .id(movieDTO.getCategory().getId())
                .name(movieDTO.getCategory().getName())
                .build();
    }

    public MovieDTO toDTO(MovieEntity movieEntity){
        return MovieDTO.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .duration(movieEntity.getDuration())
                .inscription(String.valueOf(movieEntity.getInscription()))
                .category(getCategoryDTO(movieEntity))
                .build();
    }

    public CategoryDTO getCategoryDTO(MovieEntity movieEntity){
        return CategoryDTO.builder()
                .id(movieEntity.getCategory().getId())
                .name(movieEntity.getCategory().getName())
                .build();
    }
}
