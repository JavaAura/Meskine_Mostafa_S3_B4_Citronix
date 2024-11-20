package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;

import java.util.List;

public interface FieldService {

    FieldResponseDTO createField(FieldRequestDTO fieldRequestDTO);

    FieldResponseDTO getFieldById(Long id);

    List<FieldResponseDTO> getAllFields();

    FieldResponseDTO updateField(Long id, FieldRequestDTO fieldRequestDTO);

    void deleteField(Long id);
}

