package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.mapper.FieldMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.Interface.FieldService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    public FieldResponseDTO createField(FieldRequestDTO fieldRequestDTO) {
        Farm farm = farmRepository.findById(fieldRequestDTO.farmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm with id " + fieldRequestDTO.farmId() + " not found."));

        Field field = new Field();
        field.setArea(fieldRequestDTO.area());
        field.setFarm(farm);

        Field savedField = fieldRepository.save(field);
        return fieldMapper.toDto(savedField);
    }

    @Override
    public FieldResponseDTO getFieldById(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with id " + id + " not found."));
        return fieldMapper.toDto(field);
    }

    @Override
    public List<FieldResponseDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        return fields.stream()
                .map(fieldMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FieldResponseDTO updateField(Long id, FieldRequestDTO fieldRequestDTO) {
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with id " + id + " not found."));

        Farm farm = farmRepository.findById(fieldRequestDTO.farmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm with id " + fieldRequestDTO.farmId() + " not found."));

        existingField.setArea(fieldRequestDTO.area());
        existingField.setFarm(farm);

        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toDto(updatedField);
    }

    @Override
    public void deleteField(Long id) {
        if (!fieldRepository.existsById(id)) {
            throw new EntityNotFoundException("Field with id " + id + " not found.");
        }
        fieldRepository.deleteById(id);
    }
}