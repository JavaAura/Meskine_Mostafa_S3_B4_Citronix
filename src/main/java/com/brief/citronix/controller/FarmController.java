package com.brief.citronix.controller;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.service.Interface.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping
    public ResponseEntity<FarmResponseDTO> createFarm(@Valid @RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO createdFarm = farmService.createFarm(farmRequestDTO);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable Long id) {
        FarmResponseDTO farm = farmService.getFarmById(id);
        return ResponseEntity.ok(farm);
    }

    @GetMapping
    public ResponseEntity<List<FarmResponseDTO>> getAllFarms() {
        List<FarmResponseDTO> farms = farmService.getAllFarms();
        return ResponseEntity.ok(farms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> updateFarm(@Valid @PathVariable Long id, @RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO updatedFarm = farmService.updateFarm(id, farmRequestDTO);
        return ResponseEntity.ok(updatedFarm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Farm> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double area
    ) {
        return farmService.searchFarms(name, location, area);
    }
}