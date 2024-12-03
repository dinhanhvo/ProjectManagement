package com.vodinh.prime.service;


import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.mappers.LineMapper;
import com.vodinh.prime.repositories.LineRepository;
import com.vodinh.prime.requests.LineRequest;
import com.vodinh.prime.util.LineSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
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

    public List<Line> getAllLines() {
        return lineRepository.findAll().stream().map(lineMapper::toDTO).collect(Collectors.toList());
    }

    public Line getLineById(Long id) {
        return lineRepository.findById(id).orElse(null);
    }

    public Line getLineByLineId(String lineId) {
        return lineRepository.findByLineId(lineId).orElseThrow(
                () -> new EntityNotFoundException("Line with id " + lineId + " not found")
        );
    }

    public List<Line> searchLines(String status, String lineId, String name) {
        Boolean bstatus = status.toLowerCase().equals("active");
        if (bstatus != null && lineId != null && name != null) {
            return lineRepository.findByStatusAndLineIdAndName(bstatus, lineId, name);
        } else if (bstatus != null && lineId != null) {
            return lineRepository.findByStatusAndLineId(bstatus, lineId);
        } else if (bstatus != null && name != null) {
            return lineRepository.findByStatusAndName(bstatus, name);
        } else if (lineId != null && name != null) {
            return lineRepository.findByLineIdAndName(lineId, name);
        } else if (bstatus != null) {
            return lineRepository.findByStatus(bstatus);
        } else if (lineId != null) {
            return List.of(lineRepository.findByLineId(lineId).orElse(null));
        } else if (name != null) {
            return lineRepository.findByName(name);
        } else {
            return lineRepository.findAll();  // Trả về tất cả nếu không có tham số nào
        }
    }

    public List<Line> searchLinesAll(Boolean status, String lineId, String name) {
//        Boolean bstatus = status.toLowerCase().equals("active");
        Specification<Line> spec = Specification.where(LineSpecification.hasStatus(status))
                .and(LineSpecification.hasLineId(lineId))
                .and(LineSpecification.hasName(name));

        return lineRepository.findAll(spec);
    }

    public Line createLine(LineRequest lineRequest) {
        User user = userService.getUserById(lineRequest.getClientId());
//        Line line = getLineByLineId(lineRequest.getLineId());
        Line line = new Line();
        line.setClient(user);
        BeanUtils.copyProperties(lineRequest, line);
        return lineRepository.save(line);
    }

    public Line updateLine(LineRequest lineRequest) {
        User user = userService.getUserById(lineRequest.getClientId());
        Line line = getLineByLineId(lineRequest.getLineId());
        line.setClient(user);
        BeanUtils.copyProperties(lineRequest, line);
        return lineRepository.save(line);
    }

    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }
}

