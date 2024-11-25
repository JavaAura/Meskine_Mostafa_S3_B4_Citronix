package com.brief.citronix.controller;

import com.brief.citronix.model.DTO.request.HarvestDetailRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestDetailResponseDTO;
import com.brief.citronix.service.Interface.HarvestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harvest-details")
@RequiredArgsConstructor
public class HarvestDetailController {

    private final HarvestDetailService harvestDetailService;

    @PostMapping
    public ResponseEntity<HarvestDetailResponseDTO> createHarvestDetail(@RequestBody HarvestDetailRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(harvestDetailService.createHarvestDetail(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarvestDetailResponseDTO> getHarvestDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(harvestDetailService.getHarvestDetailById(id));
    }

    @GetMapping
    public ResponseEntity<List<HarvestDetailResponseDTO>> getAllHarvestDetails() {
        return ResponseEntity.ok(harvestDetailService.getAllHarvestDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HarvestDetailResponseDTO> updateHarvestDetail(@PathVariable Long id, @RequestBody HarvestDetailRequestDTO requestDTO) {
        return ResponseEntity.ok(harvestDetailService.updateHarvestDetail(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvestDetail(@PathVariable Long id) {
        harvestDetailService.deleteHarvestDetail(id);
        return ResponseEntity.noContent().build();
    }
}
