package com.vodinh.prime.service;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.WeightMapper;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.repositories.WeightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeightService {

    private final WeightRepository weightRepository;
    private final WeightMapper weightMapper;

    public WeightService(WeightRepository weightRepository, WeightMapper weightMapper) {
        this.weightRepository = weightRepository;
        this.weightMapper = weightMapper;
    }

    public Weight getWeightsById(Long id) {
        return weightRepository.findById(id).orElse(null);
    }
    public Page<Weight> getWeightsByUserId(Pageable pageable, Long userId) {
        return weightRepository.findByUserId(pageable, userId);
    }

    public Weight getWeightsBySeriNumber(String seriNumber) {
        return weightRepository.findBySerialNumber(seriNumber).orElse(null);
    }

    public Weight getWeightsByModel(String model) {
        return weightRepository.findByModel(model).get();
    }

    public WeightDTO updateWeight(WeightDTO weightDTO) {
        Optional<Weight> w = Optional.ofNullable(weightRepository.findById(weightDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Weight not existed", "id", weightDTO.getId())
        ));
        Weight found = w.get();
        if (found.getId().equals(weightDTO.getId())) {
            BeanUtils.copyProperties(weightDTO, found);
        } else {
            throw new ResourceNotFoundException("Weight not existed", "id", weightDTO.getId());
        }
        return weightMapper.toDTO(weightRepository.save(found));
    }

    public WeightDTO createWeight(WeightDTO weightDTO) {
        Weight w = weightMapper.toEntity(weightDTO);
        Weight saved = weightRepository.save(w);
        return weightMapper.toDTO(saved);
    }

    public boolean deleteWeight(Long weightId) {
        weightRepository.deleteById(weightId);
        return true;
    }
}