package com.informatorio.festmovies.repository;

import com.informatorio.festmovies.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Boolean existsCategoryByName(String name);
}
