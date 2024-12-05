package com.vodinh.prime.controller;

import com.vodinh.prime.model.FactoryDTO;
import com.vodinh.prime.service.FactoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Factory Controller", description = "APIs for managing Factory")
@RestController
@RequestMapping("/api")
public class FactoryController {

    private final FactoryService factoryService;

    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping("factories")
    public ResponseEntity<Page<FactoryDTO>> getAllFactories(Pageable pageable,
                                                            HttpServletResponse response
                                                            ) {
        Page<FactoryDTO> factoryDTOS = factoryService.getAllFactories(pageable);
        response.setHeader("X-Total-Count", String.valueOf(factoryDTOS.getTotalElements()));
        return new ResponseEntity<>(factoryDTOS, HttpStatus.OK);
    }

    @GetMapping("/factory/{id}")
    public ResponseEntity<FactoryDTO> getFactoryById(@PathVariable Long id) {
        return ResponseEntity.ok(factoryService.getFactoryById(id));
    }

    @PostMapping("/factory")
    public ResponseEntity<FactoryDTO> createFactory(@RequestBody FactoryDTO dto) {
        return ResponseEntity.ok(factoryService.createFactory(dto));
    }

    @PutMapping("/factory/{id}")
    public ResponseEntity<FactoryDTO> updateFactory(@PathVariable Long id, @RequestBody FactoryDTO dto) {
        return ResponseEntity.ok(factoryService.updateFactory(id, dto));
    }

    @DeleteMapping("/factory/{id}")
    public ResponseEntity<Boolean> deleteFactory(@PathVariable Long id) {
        factoryService.deleteFactory(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
