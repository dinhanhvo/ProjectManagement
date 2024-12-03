package com.vodinh.prime.controller;


import com.vodinh.prime.entities.Line;
import com.vodinh.prime.model.LineDTO;
import com.vodinh.prime.requests.LineRequest;
import com.vodinh.prime.service.LineService;
import com.vodinh.prime.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Line Controller", description = "APIs for managing Line, status = active/inactive")
public class LineController {

    private static final Logger log = LoggerFactory.getLogger(LineController.class);

    private  final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    // Lấy tất cả các Line
    @GetMapping("/lines")
    public List<LineDTO> getAllLines() {
        return lineService.getAllLines();
    }

    // Lấy Line theo ID
    @GetMapping("/line/{lineId}")
    public ResponseEntity<Line> getLineById(@PathVariable String lineId) {
        Optional<Line> line = Optional.ofNullable(lineService.getLineByLineId(lineId));
        return line.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/line/{id}")
    public ResponseEntity<Line> getLineById(@PathVariable Long id) {
        Optional<Line> line = Optional.ofNullable(lineService.getLineById(id));
        return line.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/line/search")
    public ResponseEntity<List<Line>> searchLines(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String lineId,
            @RequestParam(required = false) String name) {

        log.info("---------------- Searching for lines with status " + status + " and lineId " + lineId);
        List<Line> lines = lineService.searchLinesAll(status, lineId, name);
        log.info("------------ response: " + lines);
        return ResponseEntity.ok(lines);
    }

    // Tạo mới hoặc cập nhật Line
    @PostMapping("/line")
    public ResponseEntity<Line> createOrUpdateLine(@RequestBody LineRequest line) {
        Line savedLine = lineService.createLine(line);
        return new ResponseEntity<>(savedLine, HttpStatus.CREATED);
    }

    // Cập nhật Line theo ID
    @PutMapping("/line/{id}")
    public ResponseEntity<Line> updateLine(@PathVariable Long id, @RequestBody LineRequest line) {
        Line existingLine = lineService.getLineById(id);
        if (existingLine == null) {
            return ResponseEntity.notFound().build();
        }
//        line.setId(id);  // Đảm bảo ID không bị thay đổi
        Line updatedLine = lineService.updateLine(line);
        return ResponseEntity.ok(updatedLine);
    }

    // Xóa Line theo ID
    @DeleteMapping("/line/{id}")
    public ResponseEntity<Boolean> deleteLine(@PathVariable Long id) {
        lineService.deleteLine(id);
//        return ResponseEntity.noContent().build();
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
