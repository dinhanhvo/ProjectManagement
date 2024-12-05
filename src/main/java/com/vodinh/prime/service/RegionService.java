package com.vodinh.prime.service;

import com.vodinh.prime.entities.Region;
import com.vodinh.prime.mappers.RegionMapper;
import com.vodinh.prime.model.RegionDTO;
import com.vodinh.prime.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    public Page<RegionDTO> getAllRegions(Pageable pageable) {
        return regionRepository.findAll(pageable).map(regionMapper::toDTO);
    }

    public RegionDTO getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        return regionMapper.toDTO(region);
    }

    public RegionDTO createRegion(RegionDTO dto) {
        Region region = regionMapper.toEntity(dto);
        return regionMapper.toDTO(regionRepository.save(region));
    }

    public RegionDTO updateRegion(Long id, RegionDTO dto) {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        BeanUtils.copyProperties(dto, existingRegion);
        return regionMapper.toDTO(regionRepository.save(existingRegion));
    }

    public void deleteRegion(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + id));
        regionRepository.delete(region);
    }
}
