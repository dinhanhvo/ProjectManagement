package com.vodinh.prime.service;

import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.exception.ResourceNotFoundException;
import com.vodinh.prime.mappers.WeightMapper;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.repositories.LineRepository;
import com.vodinh.prime.repositories.UserRepository;
import com.vodinh.prime.repositories.WeightRepository;
import com.vodinh.prime.requests.WeightRequest;
import com.vodinh.prime.specification.WeightSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class WeightService {

    private final WeightRepository weightRepository;
    private final WeightMapper weightMapper;
    private final LineRepository lineRepository;
    private final UserRepository userRepository;

    public WeightService(WeightRepository weightRepository, WeightMapper weightMapper, LineRepository lineRepository, UserRepository userRepository) {
        this.weightRepository = weightRepository;
        this.weightMapper = weightMapper;
        this.lineRepository = lineRepository;
        this.userRepository = userRepository;
    }

    public Weight getWeightsById(Long id) {
        return weightRepository.findById(id).orElse(null);
    }
    public Page<WeightDTO> getWeightsByUserId(Pageable pageable, Long userId) {
        return weightRepository.findByUserId(pageable, userId).map(weightMapper::toDTO);
    }

    public WeightDTO getWeightsBySeriNumber(String seriNumber) {
        return weightMapper.toDTO(weightRepository.findBySerialNumber(seriNumber).orElse(null));
    }

    public WeightDTO getWeightsByModel(String model) {
        return weightMapper.toDTO(weightRepository.findByModel(model).get());
    }

//    public WeightDTO updateWeight(WeightDTO weightDTO) {
//        Optional<Weight> w = Optional.ofNullable(weightRepository.findById(weightDTO.getId()).orElseThrow(
//                () -> new ResourceNotFoundException("Weight not existed", "id", weightDTO.getId())
//        ));
//        if (weightDTO.getId() > 0) {
//        } else {
//            throw new ResourceNotFoundException("Line not existed", "lineId", weightDTO.getLineId());
//        }
//        Weight found = w.get();
//        Line line = lineRepository.findById(weightDTO.getId()).get();
//        found.setLine(line);
//        BeanUtils.copyProperties(weightDTO, found);
//
//        return weightMapper.toDTO(weightRepository.save(found));
//    }

    public WeightDTO upsertWeight(WeightRequest weightRequest) {
        Weight weight;

        if (Objects.nonNull(weightRequest.getId()) && weightRequest.getId() > 0) {
            weight = weightRepository.findById(weightRequest.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Weight not existed", "id", weightRequest.getId())
            );
        } else {
            weight = new Weight();
        }

        BeanUtils.copyProperties(weightRequest, weight);

        User user = userRepository.findById(weightRequest.getContactId()).get();
        weight.setUser(user);

        if (Objects.nonNull(weightRequest.getLineId()) && weightRequest.getLineId() > 0) {
            Line line = lineRepository.findByClientAndId(user, weightRequest.getLineId()).get();
            weight.setLine(line);
        }

        Weight saved = weightRepository.save(weight);
        return weightMapper.toDTO(saved);
    }

    public boolean deleteWeight(Long weightId) {
        weightRepository.deleteById(weightId);
        return true;
    }

    public Page<WeightDTO> searchWeights(String serialNumber, Long contactId, Long lineId,
                                         LocalDateTime fromSellAt, LocalDateTime toSellAt, Pageable pageable) {
        if (Objects.isNull(toSellAt)) {
            toSellAt = LocalDateTime.now();
        }
        Specification<Weight> specification = WeightSpecification.search(serialNumber, contactId, lineId, fromSellAt, toSellAt);
        Page<Weight> weights = weightRepository.findAll(specification, pageable);
        return weights.map(weightMapper::toDTO);
    }
}