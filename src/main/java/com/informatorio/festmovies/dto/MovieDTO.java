package com.informatorio.festmovies.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@Builder
public class MovieDTO {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Double duration;
    @NotBlank
    private String inscription;
    @NotNull
    private CategoryDTO category;
    @NotNull
    private DirectoryDTO directory;

    private Set<CharacterDTO> characters;
}
