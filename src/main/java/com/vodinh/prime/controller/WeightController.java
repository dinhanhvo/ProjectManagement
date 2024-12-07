package com.vodinh.prime.controller;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.enums.WeightStatus;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.requests.WeightRequest;
import com.vodinh.prime.service.WeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name = "Weight Controller", description = "APIs for managing weight")
public class WeightController {

    private final WeightService weightService;

    public WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    @GetMapping("/weight/{id}")
    public ResponseEntity<Weight>  getWeightsById(@PathVariable Long id) {
        Weight result = weightService.getWeightsById(id);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weight/search")
    public ResponseEntity<Page<WeightDTO>> searchWeights(
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) Long contactId,
            @RequestParam(required = false) Long lineId,
            @RequestParam(required = false) WeightStatus status,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromSellAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toSellAt,
            Pageable pageable) {

        Page<WeightDTO> weightDTOs = weightService.searchWeights(serialNumber, contactId, lineId, status,
                name, model,
                fromSellAt, toSellAt, pageable);
        return ResponseEntity.ok(weightDTOs);
    }

    @GetMapping("/weights/user/{userId}")
    public ResponseEntity<Page<WeightDTO>>  getWeightsByUserId(
            @PathVariable Long userId,
            Pageable pageable,
            HttpServletResponse httpServletResponse
            ) {
        Page<WeightDTO> result = weightService.getWeightsByUserId(pageable, userId);
        httpServletResponse.setHeader("X-Total-Count", String.valueOf(result.getTotalElements()));
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/serial/{serialNumber}")
    public ResponseEntity<WeightDTO> getWeightsBySerialNumber(@PathVariable String serialNumber) {
        WeightDTO result = weightService.getWeightsBySeriNumber(serialNumber);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/model/{model}")
    public ResponseEntity<WeightDTO> getWeightsByModel(@PathVariable String model) {
        WeightDTO result = weightService.getWeightsByModel(model);
        return  ResponseEntity.ok(result);
    }

    @PostMapping("/weight")
    public ResponseEntity<WeightDTO> createWeight(@RequestBody WeightRequest weight) {
        return new ResponseEntity<>(weightService.upsertWeight(weight), HttpStatus.OK);
    }

    @PutMapping("/weight")
    public ResponseEntity<WeightDTO> updateWeight(@RequestBody WeightRequest weightRequest) {
        return new ResponseEntity<>(weightService.upsertWeight(weightRequest), HttpStatus.OK);
    }

    @DeleteMapping("/weight/{weightId}")
    public ResponseEntity<Boolean> deleteWeight(@PathVariable Long weightId) {
        boolean deleted = weightService.deleteWeight(weightId);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}

