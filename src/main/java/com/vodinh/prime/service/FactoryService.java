package com.vodinh.prime.service;

import com.vodinh.prime.entities.Factory;
import com.vodinh.prime.entities.Region;
import com.vodinh.prime.mappers.FactoryMapper;
import com.vodinh.prime.model.FactoryDTO;
import com.vodinh.prime.repositories.FactoryRepository;
import com.vodinh.prime.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FactoryService {

    private final FactoryRepository factoryRepository;
    private final RegionRepository regionRepository;
    private final FactoryMapper factoryMapper;

    public FactoryService(FactoryRepository factoryRepository, RegionRepository regionRepository, FactoryMapper factoryMapper) {
        this.factoryRepository = factoryRepository;
        this.regionRepository = regionRepository;
        this.factoryMapper = factoryMapper;
    }

    public Page<FactoryDTO> getAllFactories(Pageable pageable) {
        return factoryRepository.findAll(pageable).map(factoryMapper::toDTO);
    }

    public FactoryDTO getFactoryById(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factory not found with id: " + id));
        return factoryMapper.toDTO(factory);
    }

    public FactoryDTO createFactory(FactoryDTO dto) {
        Region region = regionRepository.findByRegionId(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + dto.getRegionId()));

        Factory factory = factoryMapper.toEntity(dto);
        factory.setRegion(region);

        return factoryMapper.toDTO(factoryRepository.save(factory));
    }

    public FactoryDTO updateFactory(Long id, FactoryDTO dto) {
        Factory existingFactory = factoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factory not found with id: " + id));

        Region region = regionRepository.findByRegionId(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + dto.getRegionId()));

        BeanUtils.copyProperties(dto, existingFactory);
        existingFactory.setRegion(region);

        return factoryMapper.toDTO(factoryRepository.save(existingFactory));
    }

    public void deleteFactory(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factory not found with id: " + id));
        factoryRepository.delete(factory);
    }
}
