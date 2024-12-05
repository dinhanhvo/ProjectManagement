package com.vodinh.prime.controller;

import com.vodinh.prime.model.LineDTO;
import com.vodinh.prime.model.RegionDTO;
import com.vodinh.prime.service.RegionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Region Controller", description = "APIs for managing Region")
@RestController
@RequestMapping("/api")
public class RegionController {

    private  final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions")
    public ResponseEntity<Page<RegionDTO>> getAllRegions(Pageable pageable,
                                                         HttpServletResponse response
        ) {
        Page<RegionDTO> regionDTOS =regionService.getAllRegions(pageable);
        response.setHeader("X-Total-Count", String.valueOf(regionDTOS.getTotalElements()));
        return new ResponseEntity<>(regionDTOS, HttpStatus.OK);
    }

    @GetMapping("/region/{id}")
    public ResponseEntity<RegionDTO>  getRegionById(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @PostMapping("/region")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO dto) {
        RegionDTO regionDTO = regionService.createRegion(dto);
        return new ResponseEntity<>(regionDTO, HttpStatus.CREATED);
    }

    @PutMapping("/region/{id}")
    public ResponseEntity<RegionDTO> updateRegion(@PathVariable Long id, @RequestBody RegionDTO dto) {
        RegionDTO regionDTO = regionService.updateRegion(id, dto);
        return ResponseEntity.ok(regionDTO);
    }

    @DeleteMapping("/region/{id}")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

