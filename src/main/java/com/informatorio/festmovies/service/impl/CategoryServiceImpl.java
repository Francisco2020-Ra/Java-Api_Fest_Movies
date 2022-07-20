package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.exception.CategoryExistException;
import com.informatorio.festmovies.mapper.CategoryMapper;
import com.informatorio.festmovies.repository.CategoryRepository;
import com.informatorio.festmovies.service.CategoryService;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws CategoryExistException {
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(categoryDTO);
        if(categoryRepository.existsCategoryByName(categoryEntity.getName())){
            throw new CategoryExistException("Existing category");
        }
        CategoryEntity categoryEntity1 = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategoryDTO(categoryEntity1);
    }
}
