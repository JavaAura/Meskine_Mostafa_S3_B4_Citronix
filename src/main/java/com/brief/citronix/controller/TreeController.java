package com.brief.citronix.controller;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;
import com.brief.citronix.service.Interface.TreeService;
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
@RequestMapping("/api/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @PostMapping
    public ResponseEntity<TreeResponseDTO> createTree(@Valid @RequestBody TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO createdTree = treeService.createTree(treeRequestDTO);
        return new ResponseEntity<>(createdTree, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeResponseDTO> getTreeById(@PathVariable Long id) {
        TreeResponseDTO tree = treeService.getTreeById(id);
        return ResponseEntity.ok(tree);
    }

    @GetMapping
    public ResponseEntity<List<TreeResponseDTO>> getAllTrees() {
        List<TreeResponseDTO> trees = treeService.getAllTrees();
        return ResponseEntity.ok(trees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreeResponseDTO> updateTree(@PathVariable Long id, @Valid @RequestBody TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO updatedTree = treeService.updateTree(id, treeRequestDTO);
        return ResponseEntity.ok(updatedTree);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
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

