package com.informatorio.festmovies.service;


import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.exception.CategoryExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory (CategoryDTO categoryDTO) throws CategoryExistException;

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws ResourceNotFoundException;

    List<CategoryDTO> getAllCategory();

    void deleteCategory(Long id) throws ResourceNotFoundException;

}
