package com.vodinh.prime.service;


import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.mappers.LineMapper;
import com.vodinh.prime.model.LineDTO;
import com.vodinh.prime.repositories.LineRepository;
import com.vodinh.prime.requests.LineRequest;
import com.vodinh.prime.util.LineSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LineService {

    private final LineRepository lineRepository;
    private final UserService userService;
    private final LineMapper lineMapper;

    public LineService(LineRepository lineRepository, UserService userService, LineMapper lineMapper) {
        this.lineRepository = lineRepository;
        this.userService = userService;
        this.lineMapper = lineMapper;
    }

    public List<LineDTO> getAllLines() {
        return lineRepository.findAll().stream().map(lineMapper::toDTO).collect(Collectors.toList());
    }

    public LineDTO getLineById(Long id) {
        return lineMapper.toDTO(lineRepository.findById(id).get());
    }

    public LineDTO getLineByLineId(String lineId) {
        Line line = lineRepository.findByLineId(lineId).orElseThrow(
                () -> new EntityNotFoundException(STR."Line with id \{lineId} not found")
        );
        return lineMapper.toDTO(line);
    }

    public Page<LineDTO> searchLinesAll(Pageable pageable, Boolean status, String lineId, String name) {
        Specification<Line> spec = Specification.where(LineSpecification.hasStatus(status))
                .and(LineSpecification.hasLineId(lineId))
                .and(LineSpecification.hasName(name));

        Page<Line> lines = lineRepository.findAll(spec, pageable);

        return lines.map(lineMapper::toDTO);
    }

    public LineDTO createLine(LineRequest lineRequest) {
        User user = userService.getUserById(lineRequest.getClientId());
        Line line = new Line();
        line.setClient(user);
        BeanUtils.copyProperties(lineRequest, line);
        return lineMapper.toDTO(lineRepository.save(line));
    }

    public LineDTO updateLine(LineRequest lineRequest) {
        User user = userService.getUserById(lineRequest.getClientId());
        Line line = lineRepository.findByLineId(lineRequest.getLineId()).orElseThrow(
                () -> new EntityNotFoundException("Line with id " + lineRequest.getLineId() + " not found")
        );
        line.setClient(user);
        BeanUtils.copyProperties(lineRequest, line);
        return lineMapper.toDTO(lineRepository.save(line));
    }

    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }
}

