package com.informatorio.festmovies.mapper;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO){
        return CategoryEntity.builder().name(categoryDTO.getName()).build();
    }

    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        return CategoryDTO.builder().id(categoryEntity.getId()).name(categoryEntity.getName()).build();
    }
}
