package com.vodinh.prime.controller;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.service.WeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/weights/user/{userId}")
    public ResponseEntity<Page<Weight>>  getWeightsByUserId(
            @PathVariable Long userId,
            Pageable pageable,
            HttpServletResponse httpServletResponse
            ) {
        Page<Weight> result = weightService.getWeightsByUserId(pageable, userId);
        httpServletResponse.setHeader("X-Total-Count", String.valueOf(result.getTotalElements()));
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/seri/{seriNumber}")
    public ResponseEntity<Weight> getWeightsBySerialNumber(@PathVariable String serialNumber) {
        Weight result = weightService.getWeightsBySeriNumber(serialNumber);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/model/{model}")
    public ResponseEntity<Weight> getWeightsByModel(@PathVariable String model) {
        Weight result = weightService.getWeightsByModel(model);
        return  ResponseEntity.ok(result);
    }

    @PostMapping("/weight")
    public ResponseEntity<WeightDTO> createWeight(@RequestBody WeightDTO weight) {
        return new ResponseEntity<>(weightService.createWeight(weight), HttpStatus.OK);
    }

    @PutMapping("/weight")
    public ResponseEntity<WeightDTO> updateWeight(@RequestBody WeightDTO weight) {
        return new ResponseEntity<>(weightService.updateWeight(weight), HttpStatus.OK);
    }

    @DeleteMapping("/weight/{weightId}")
    public ResponseEntity<Boolean> deleteWeight(@PathVariable Long weightId) {
        boolean deleted = weightService.deleteWeight(weightId);
        return deleted ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}

