package com.informatorio.festmovies.repository;

import com.informatorio.festmovies.entities.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Long> {
}
