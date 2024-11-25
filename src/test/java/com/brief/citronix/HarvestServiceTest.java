package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.enums.Season;
import com.brief.citronix.model.mapper.HarvestMapper;
import com.brief.citronix.repository.HarvestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HarvestServiceTest {

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private HarvestMapper harvestMapper;

    @InjectMocks
    private HarvestServiceImpl harvestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHarvest_Success() {
        HarvestRequestDTO requestDTO = new HarvestRequestDTO(LocalDate.of(2024, 4, 10));
        Harvest harvest = new Harvest(null, requestDTO.date(), Season.SPRING, 0.0, null, null);
        Harvest savedHarvest = new Harvest(1L, requestDTO.date(), Season.SPRING, 0.0, null, null);
        HarvestResponseDTO responseDTO = new HarvestResponseDTO(1L, requestDTO.date(), Season.SPRING, 0.0, null, null);

        when(harvestMapper.toEntity(requestDTO)).thenReturn(harvest);
        when(harvestRepository.save(harvest)).thenReturn(savedHarvest);
        when(harvestMapper.toDto(savedHarvest)).thenReturn(responseDTO);

        HarvestResponseDTO result = harvestService.createHarvest(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.id(), result.id());
        verify(harvestRepository, times(1)).save(harvest);
    }

    @Test
    void testGetHarvestById_Success() {
        Harvest harvest = new Harvest(1L, LocalDate.of(2024, 4, 10), Season.SPRING, 0.0, null, null);
        HarvestResponseDTO responseDTO = new HarvestResponseDTO(1L, harvest.getDate(), harvest.getSeason(), 0.0, null, null);

        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(harvestMapper.toDto(harvest)).thenReturn(responseDTO);

        HarvestResponseDTO result = harvestService.getHarvestById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(harvestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetHarvestById_NotFound() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            harvestService.getHarvestById(1L);
        });

        assertEquals("Harvest with id 1 not found.", exception.getMessage());
        verify(harvestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllHarvests_Success() {
        Harvest harvest1 = new Harvest(1L, LocalDate.of(2024, 4, 10), Season.SPRING, 0.0, null, null);
        Harvest harvest2 = new Harvest(2L, LocalDate.of(2024, 6, 15), Season.SUMMER, 0.0, null, null);
        HarvestResponseDTO responseDTO1 = new HarvestResponseDTO(1L, harvest1.getDate(), harvest1.getSeason(), 0.0, null, null);
        HarvestResponseDTO responseDTO2 = new HarvestResponseDTO(2L, harvest2.getDate(), harvest2.getSeason(), 0.0, null, null);

        when(harvestRepository.findAll()).thenReturn(Arrays.asList(harvest1, harvest2));
        when(harvestMapper.toDto(harvest1)).thenReturn(responseDTO1);
        when(harvestMapper.toDto(harvest2)).thenReturn(responseDTO2);

        List<HarvestResponseDTO> result = harvestService.getAllHarvests();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(harvestRepository, times(1)).findAll();
    }

    @Test
    void testUpdateHarvest_Success() {
        HarvestRequestDTO requestDTO = new HarvestRequestDTO(LocalDate.of(2024, 6, 15));
        Harvest existingHarvest = new Harvest(1L, LocalDate.of(2024, 4, 10), Season.SPRING, 0.0, null, null);
        Harvest updatedHarvest = new Harvest(1L, requestDTO.date(), Season.SUMMER, 0.0, null, null);
        HarvestResponseDTO responseDTO = new HarvestResponseDTO(1L, updatedHarvest.getDate(), updatedHarvest.getSeason(), 0.0, null, null);

        when(harvestRepository.findById(1L)).thenReturn(Optional.of(existingHarvest));
        when(harvestRepository.save(existingHarvest)).thenReturn(updatedHarvest);
        when(harvestMapper.toDto(updatedHarvest)).thenReturn(responseDTO);

        HarvestResponseDTO result = harvestService.updateHarvest(1L, requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(requestDTO.date(), result.date());
        verify(harvestRepository, times(1)).findById(1L);
        verify(harvestRepository, times(1)).save(existingHarvest);
    }

    @Test
    void testDeleteHarvest_Success() {
        when(harvestRepository.existsById(1L)).thenReturn(true);

        harvestService.deleteHarvest(1L);

        verify(harvestRepository, times(1)).existsById(1L);
        verify(harvestRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteHarvest_NotFound() {
        when(harvestRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            harvestService.deleteHarvest(1L);
        });

        assertEquals("Harvest with id 1 not found.", exception.getMessage());
        verify(harvestRepository, times(1)).existsById(1L);
        verify(harvestRepository, never()).deleteById(any());
    }
}
