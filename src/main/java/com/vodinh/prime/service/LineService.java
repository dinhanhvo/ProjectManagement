package com.vodinh.prime.service;


import com.vodinh.prime.entities.Line;
import com.vodinh.prime.repositories.LineRepository;
import com.vodinh.prime.util.LineSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    private final LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public List<Line> getAllLines() {
        return lineRepository.findAll();
    }

    public Line getLineById(Long id) {
        return lineRepository.findById(id).orElse(null);
    }

    public Line getLineByLineId(String lineId) {
        return lineRepository.findByLineId(lineId).orElse(null);
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

    public List<Line> searchLinesAll(String status, String lineId, String name) {
        Boolean bstatus = status.toLowerCase().equals("active");
        Specification<Line> spec = Specification.where(LineSpecification.hasStatus(bstatus))
                .and(LineSpecification.hasLineId(lineId))
                .and(LineSpecification.hasName(name));

        return lineRepository.findAll(spec);
    }

    public Line createOrUpdateLine(Line line) {
        return lineRepository.save(line);
    }

    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }
}

