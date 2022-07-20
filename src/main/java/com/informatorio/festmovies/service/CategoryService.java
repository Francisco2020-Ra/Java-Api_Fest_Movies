package com.informatorio.festmovies.service;


import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.exception.CategoryExistException;

public interface CategoryService {
    CategoryDTO addCategory (CategoryDTO categoryDTO) throws CategoryExistException;

}
