package com.vodinh.prime.controller;

import com.vodinh.prime.entities.Weight;
import com.vodinh.prime.model.WeightDTO;
import com.vodinh.prime.service.WeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<Weight>>  getWeightsByUserId(@PathVariable Long userId) {
        List<Weight> result = weightService.getWeightsByUserId(userId);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/seri/{seriNumber}")
    public ResponseEntity<List<Weight>> getWeightsBySeriNumber(@PathVariable String seriNumber) {
        List<Weight> result = weightService.getWeightsBySeriNumber(seriNumber);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/weights/model/{model}")
    public ResponseEntity<List<Weight>> getWeightsByModel(@PathVariable String model) {
        List<Weight> result = weightService.getWeightsByModel(model);
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
