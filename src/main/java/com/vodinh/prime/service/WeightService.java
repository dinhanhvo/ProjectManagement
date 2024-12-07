package com.vodinh.prime.service;

import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.WeightMapper;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.repositories.LineRepository;
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
    private final LineRepository lineRepository;

    public WeightService(WeightRepository weightRepository, WeightMapper weightMapper, LineRepository lineRepository) {
        this.weightRepository = weightRepository;
        this.weightMapper = weightMapper;
        this.lineRepository = lineRepository;
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
        if (weightDTO.getId() > 0) {
        } else {
            throw new ResourceNotFoundException("Line not existed", "lineId", weightDTO.getLineId());
        }
        Weight found = w.get();
        Line line = lineRepository.findById(weightDTO.getId()).get();
        found.setLine(line);
        BeanUtils.copyProperties(weightDTO, found);

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