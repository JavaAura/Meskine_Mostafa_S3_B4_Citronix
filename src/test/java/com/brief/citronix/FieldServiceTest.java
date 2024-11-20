package com.brief.citronix;

import com.brief.citronix.model.DTO.request.FieldRequestDTO;
import com.brief.citronix.model.DTO.response.FieldResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.mapper.FieldMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.Interface.FieldService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldService fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateField_Success() {
        // Arrange
        FieldRequestDTO requestDTO = new FieldRequestDTO(10.0, 1L);
        Farm farm = new Farm();
        farm.setId(1L);
        farm.setArea(100.0);

        Field field = new Field();
        Field savedField = new Field();
        FieldResponseDTO responseDTO = new FieldResponseDTO(1L, 10.0, 1L, Collections.emptyList());

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(fieldMapper.toEntity(requestDTO)).thenReturn(field);
        when(fieldRepository.save(field)).thenReturn(savedField);
        when(fieldMapper.toDto(savedField)).thenReturn(responseDTO);

        // Act
        FieldResponseDTO result = fieldService.createField(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(farmRepository).findById(1L);
        verify(fieldRepository).save(field);
    }

    @Test
    void testCreateField_FarmNotFound() {
        // Arrange
        FieldRequestDTO requestDTO = new FieldRequestDTO(10.0, 1L);
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> fieldService.createField(requestDTO));
        verify(farmRepository).findById(1L);
        verifyNoInteractions(fieldRepository, fieldMapper);
    }

    @Test
    void testGetFieldById_Success() {
        // Arrange
        Field field = new Field();
        FieldResponseDTO responseDTO = new FieldResponseDTO(1L, 10.0, 1L, Collections.emptyList());

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(field));
        when(fieldMapper.toDto(field)).thenReturn(responseDTO);

        // Act
        FieldResponseDTO result = fieldService.getFieldById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(fieldRepository).findById(1L);
    }

    @Test
    void testGetFieldById_NotFound() {
        // Arrange
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> fieldService.getFieldById(1L));
        verify(fieldRepository).findById(1L);
    }

    @Test
    void testGetAllFields_Success() {
        // Arrange
        Field field = new Field();
        FieldResponseDTO responseDTO = new FieldResponseDTO(1L, 10.0, 1L, Collections.emptyList());

        when(fieldRepository.findAll()).thenReturn(Collections.singletonList(field));
        when(fieldMapper.toDto(field)).thenReturn(responseDTO);

        // Act
        List<FieldResponseDTO> result = fieldService.getAllFields();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(fieldRepository).findAll();
    }

    @Test
    void testUpdateField_Success() {
        // Arrange
        FieldRequestDTO requestDTO = new FieldRequestDTO(15.0, 1L);
        Field existingField = new Field();
        Farm farm = new Farm();
        Field updatedField = new Field();
        FieldResponseDTO responseDTO = new FieldResponseDTO(1L, 15.0, 1L, Collections.emptyList());

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(existingField));
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(fieldRepository.save(existingField)).thenReturn(updatedField);
        when(fieldMapper.toDto(updatedField)).thenReturn(responseDTO);

        // Act
        FieldResponseDTO result = fieldService.updateField(1L, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(fieldRepository).findById(1L);
        verify(farmRepository).findById(1L);
        verify(fieldRepository).save(existingField);
    }

    @Test
    void testUpdateField_FieldNotFound() {
        // Arrange
        FieldRequestDTO requestDTO = new FieldRequestDTO(15.0, 1L);
        when(fieldRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> fieldService.updateField(1L, requestDTO));
        verify(fieldRepository).findById(1L);
        verifyNoInteractions(farmRepository, fieldMapper);
    }

    @Test
    void testDeleteField_Success() {
        // Arrange
        when(fieldRepository.existsById(1L)).thenReturn(true);

        // Act
        fieldService.deleteField(1L);

        // Assert
        verify(fieldRepository).existsById(1L);
        verify(fieldRepository).deleteById(1L);
    }

    @Test
    void testDeleteField_NotFound() {
        // Arrange
        when(fieldRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> fieldService.deleteField(1L));
        verify(fieldRepository).existsById(1L);
        verify(fieldRepository, never()).deleteById(anyLong());
    }
}

