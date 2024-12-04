package com.vodinh.prime.controller;


import com.vodinh.prime.model.LineDTO;
import com.vodinh.prime.requests.LineRequest;
import com.vodinh.prime.service.LineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/lineId/{lineId}")
    public ResponseEntity<LineDTO> getLineById(@PathVariable("lineId") String lineId) {
        LineDTO lineDTO = lineService.getLineByLineId(lineId);
        return ResponseEntity.ok(lineDTO);
    }

    @GetMapping("/line/{id}")
    public ResponseEntity<LineDTO> getLineById(@PathVariable("id") Long id) {
        LineDTO lineDTO = lineService.getLineById(id);
        return ResponseEntity.ok(lineDTO);
    }

    @GetMapping("/line/search")
    public ResponseEntity<Page<LineDTO>> searchLines(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String lineId,
            @RequestParam(required = false) String name,
            Pageable pageable,
            HttpServletResponse httpServletResponse
            ) {

        log.info("---------------- Searching for lines with status " + status + " and lineId " + lineId);
        Page<LineDTO> lines = lineService.searchLinesAll(pageable, status, lineId, name);
        httpServletResponse.setHeader("X-Total-Count", String.valueOf(lines.getTotalElements()));
        log.info("------------ response: " + lines);
        return ResponseEntity.ok(lines);
    }

    @PostMapping("/line")
    public ResponseEntity<LineDTO> createLine(@RequestBody LineRequest line) {
        LineDTO savedLine = lineService.createLine(line);
        return new ResponseEntity<>(savedLine, HttpStatus.CREATED);
    }

    // Cập nhật Line theo ID
    @PutMapping("/line/{id}")
    public ResponseEntity<LineDTO> updateLine(@PathVariable Long id, @RequestBody LineRequest line) {
        LineDTO updatedLine = lineService.updateLine(line);
        return ResponseEntity.ok(updatedLine);
    }

    // Xóa Line theo ID
    @DeleteMapping("/line/{id}")
    public ResponseEntity<Boolean> deleteLine(@PathVariable Long id) {
        lineService.deleteLine(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
