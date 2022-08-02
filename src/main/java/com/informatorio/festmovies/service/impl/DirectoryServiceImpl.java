package com.informatorio.festmovies.service.impl;

import com.informatorio.festmovies.dto.DirectoryDTO;
import com.informatorio.festmovies.entities.DirectoryEntity;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import com.informatorio.festmovies.mapper.DirectoryMapper;
import com.informatorio.festmovies.repository.DirectoryRepository;
import com.informatorio.festmovies.service.DirectoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private DirectoryMapper directoryMapper;
    private DirectoryRepository directoryRepository;

    public DirectoryServiceImpl(DirectoryMapper directoryMapper,
                                DirectoryRepository directoryRepository){
        this.directoryMapper = directoryMapper;
        this.directoryRepository = directoryRepository;
    }
    @Override
    public DirectoryDTO addDirectory(DirectoryDTO directoryDTO) {
        DirectoryEntity directory = directoryMapper.toEntity(directoryDTO);
        directoryRepository.save(directory);
        return directoryMapper.toDTO(directory);
    }

    @Override
    public List<DirectoryDTO> getAllDirectory() throws ResourceNotFoundException {
        List<DirectoryEntity> directoryEntityList = directoryRepository.findAll();
        if(directoryEntityList.isEmpty()){
            throw new ResourceNotFoundException("List Empty");
        }

        return directoryMapper.toListDTO(directoryEntityList);
    }

    @Override
    public DirectoryDTO updateDirectory(Long id, DirectoryDTO directoryDTO) throws ResourceNotFoundException {
        DirectoryEntity directory = directoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        DirectoryEntity directoryEntity = directoryMapper.setEntity(directory, directoryDTO);
        DirectoryEntity directorySave = directoryRepository.save(directoryEntity);
        return directoryMapper.toDTO(directorySave);
    }

    @Override
    public void deleteDirectory(Long id) throws ResourceNotFoundException {
        directoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );

        directoryRepository.deleteById(id);
    }
}
