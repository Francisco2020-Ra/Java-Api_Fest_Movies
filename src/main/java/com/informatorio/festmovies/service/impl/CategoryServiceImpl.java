package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.entities.CategoryEntity;
import com.informatorio.festmovies.exception.CategoryExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.mapper.CategoryMapper;
import com.informatorio.festmovies.repository.CategoryRepository;
import com.informatorio.festmovies.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws CategoryExistException {
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(categoryDTO);
        if (categoryRepository.existsCategoryByName(categoryEntity.getName())) {
            throw new CategoryExistException("Existing category");
        }
        CategoryEntity categoryEntity1 = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategoryDTO(categoryEntity1);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws ResourceNotFoundException {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found category id: " + id));
        categoryEntity.setName(categoryDTO.getName());
        CategoryEntity categoryEntity1 = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategoryDTO(categoryEntity1);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> listCategory = categoryRepository.findAll();
        return categoryMapper.toListCategoryDTO(listCategory);
    }

    @Override
    public void deleteCategory(Long id) throws ResourceNotFoundException {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found category id: " + id));
        categoryRepository.deleteById(id);
    }
}
