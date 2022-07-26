package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CategoryMapper {

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO){
        return CategoryEntity.builder().id(categoryDTO.getId()).name(categoryDTO.getName()).build();
    }

    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        return CategoryDTO.builder().id(categoryEntity.getId()).name(categoryEntity.getName()).build();
    }

    public List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> categoryEntity){
        return categoryEntity.stream()
                .map(this::toCategoryDTO)
                .collect(toList());
    }
}
