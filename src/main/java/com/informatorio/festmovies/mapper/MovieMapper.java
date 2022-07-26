package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.dto.CharacterDTO;
import com.informatorio.festmovies.dto.MovieDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.entities.CharacterEntity;
import com.informatorio.festmovies.entities.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class MovieMapper {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CategoryMapper categoryMapper;

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
        return categoryMapper.toCategoryEntity(movieDTO.getCategory());
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
        return categoryMapper.toCategoryDTO(movieEntity.getCategory());
    }

    public List<MovieDTO> toListMovieDTO(List<MovieEntity> movieEntity){
        return movieEntity.stream()
                .map(this::toFullMovieDTO)
                .collect(toList());
    }

    public MovieDTO toFullMovieDTO(MovieEntity movieEntity){
        MovieDTO movieDTO = toDTO(movieEntity);
        movieDTO.setCharacters(setCharacterDTO(movieEntity.getCharacters()));
        return  movieDTO;
    }


    public Set<CharacterDTO> setCharacterDTO(Set<CharacterEntity> characterEntitySet){
        return characterMapper.toSetDTOCharacter(characterEntitySet);
    }

    public MovieEntity setMovieEntity(MovieEntity movieEntity, MovieDTO movieDTO){
        movieEntity.setId(movieDTO.getId());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setDescription(movieDTO.getDescription());
        movieEntity.setDuration(movieDTO.getDuration());
        movieEntity.setInscription(LocalDate.parse(movieDTO.getInscription()));
        movieEntity.setCategory(getCategoryEntity(movieDTO));
        return movieEntity;
    }
}
