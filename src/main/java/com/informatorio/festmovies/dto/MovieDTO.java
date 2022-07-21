package com.informatorio.festmovies.dto;

import com.informatorio.festmovies.entities.CategoryEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class MovieDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private double duration;
    @NotBlank
    private LocalDate inscription;
    @NotBlank
    private CategoryDTO category;
}
