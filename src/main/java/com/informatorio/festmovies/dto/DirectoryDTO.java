package com.informatorio.festmovies.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class DirectoryDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    private String birthDate;
    @NotNull
    private Integer passport;
    @NotNull
    private String inscription;
}
