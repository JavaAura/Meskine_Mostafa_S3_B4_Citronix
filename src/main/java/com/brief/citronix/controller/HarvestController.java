package com.brief.citronix.controller;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;
import com.brief.citronix.service.Interface.HarvestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;

    @PostMapping
    public ResponseEntity<HarvestResponseDTO> createHarvest(@Valid @RequestBody HarvestRequestDTO harvestRequestDTO) {
        HarvestResponseDTO createdHarvest = harvestService.createHarvest(harvestRequestDTO);
        return new ResponseEntity<>(createdHarvest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> getHarvestById(@PathVariable Long id) {
        HarvestResponseDTO harvest = harvestService.getHarvestById(id);
        return ResponseEntity.ok(harvest);
    }

    @GetMapping
    public ResponseEntity<List<HarvestResponseDTO>> getAllHarvests() {
        List<HarvestResponseDTO> harvests = harvestService.getAllHarvests();
        return ResponseEntity.ok(harvests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestResponseDTO> updateHarvest(@PathVariable Long id, @Valid @RequestBody HarvestRequestDTO harvestRequestDTO) {
        HarvestResponseDTO updatedHarvest = harvestService.updateHarvest(id, harvestRequestDTO);
        return ResponseEntity.ok(updatedHarvest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}