package com.informatorio.festmovies.service;

import com.informatorio.festmovies.dto.DirectoryDTO;
import com.informatorio.festmovies.exception.ResourceNotFoundException;

import java.util.List;

public interface DirectoryService {

    DirectoryDTO addDirectory(DirectoryDTO directoryDTO);

    List<DirectoryDTO> getAllDirectory() throws ResourceNotFoundException;

    DirectoryDTO updateDirectory(Long id, DirectoryDTO directoryDTO) throws ResourceNotFoundException;

    void deleteDirectory(Long id) throws ResourceNotFoundException;
}
