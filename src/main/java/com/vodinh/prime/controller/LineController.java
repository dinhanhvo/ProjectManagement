package com.vodinh.prime.controller;


import com.vodinh.prime.entities.Line;
import com.vodinh.prime.service.LineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Line Controller", description = "APIs for managing Line, status = active/inactive")
public class LineController {


    private  final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    // Lấy tất cả các Line
    @GetMapping("/lines")
    public List<Line> getAllLines() {
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
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String lineId,
            @RequestParam(required = false) String name) {

        List<Line> lines = lineService.searchLinesAll(status, lineId, name);
        return ResponseEntity.ok(lines);
    }

    // Tạo mới hoặc cập nhật Line
    @PostMapping
    public ResponseEntity<Line> createOrUpdateLine(@RequestBody Line line) {
        Line savedLine = lineService.createOrUpdateLine(line);
        return new ResponseEntity<>(savedLine, HttpStatus.CREATED);
    }

    // Cập nhật Line theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Line> updateLine(@PathVariable Long id, @RequestBody Line line) {
        Line existingLine = lineService.getLineById(id);
        if (existingLine == null) {
            return ResponseEntity.notFound().build();
        }
        line.setId(id);  // Đảm bảo ID không bị thay đổi
        Line updatedLine = lineService.createOrUpdateLine(line);
        return ResponseEntity.ok(updatedLine);
    }

    // Xóa Line theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        lineService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }
}
