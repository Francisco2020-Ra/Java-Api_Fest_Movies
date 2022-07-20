package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.dto.CategoryDTO;
import com.informatorio.festmovies.exception.CategoryExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@Validated @RequestBody CategoryDTO categoryDTO) throws CategoryExistException {
        return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoryService.updateCategory(id,categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws ResourceNotFoundException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
