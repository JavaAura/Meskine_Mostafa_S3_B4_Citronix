package com.brief.citronix.controller;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;
import com.brief.citronix.service.Interface.FieldService;
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
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<FieldResponseDTO> createField(@Valid @RequestBody FieldRequestDTO fieldRequestDTO) {
        FieldResponseDTO createdField = fieldService.createField(fieldRequestDTO);
        return new ResponseEntity<>(createdField, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseDTO> getFieldById(@PathVariable Long id) {
        FieldResponseDTO field = fieldService.getFieldById(id);
        return ResponseEntity.ok(field);
    }

    @GetMapping
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        List<FieldResponseDTO> fields = fieldService.getAllFields();
        return ResponseEntity.ok(fields);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldResponseDTO> updateField(@Valid @PathVariable Long id, @RequestBody FieldRequestDTO fieldRequestDTO) {
        FieldResponseDTO updatedField = fieldService.updateField(id, fieldRequestDTO);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
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