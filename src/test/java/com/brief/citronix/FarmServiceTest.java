package com.brief.citronix;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Tree;
import com.brief.citronix.model.mapper.FarmMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.Impl.FarmServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    private FarmRequestDTO farmRequestDTO;
    private FarmResponseDTO farmResponseDTO;
    private Farm farm;
    private Field field;

    @BeforeEach
    public void setUp() {
        // Create a mock field and add it to the farm
        field = new Field(1L, 50.5, farm, List.of(new Tree()));
        farmRequestDTO = new FarmRequestDTO("Farm1", "Location1", 100.0, LocalDate.now());
        farmResponseDTO = new FarmResponseDTO(1L, "Farm1", "Location1", 100.0, LocalDate.now(), List.of(1L));

        // Create the farm entity with the field
        farm = new Farm(1L, "Farm1", "Location1", 100.0, LocalDate.now(), List.of(field));
    }

    @Test
    public void createFarm_ShouldReturnFarmResponseDTO_WithFieldIds() {
        // Arrange
        when(farmMapper.toEntity(any(FarmRequestDTO.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.toDto(any(Farm.class))).thenReturn(farmResponseDTO);

        // Act
        FarmResponseDTO result = farmService.createFarm(farmRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(farmResponseDTO.id(), result.id());  // Correct access for the record field
        assertTrue(result.fieldIds().contains(1L));  // Check if fieldId is returned
        verify(farmRepository, times(1)).save(any(Farm.class));
        verify(farmMapper, times(1)).toEntity(farmRequestDTO);
    }


    @Test
    public void getFarmById_ShouldReturnFarmResponseDTO_WithFields() {
        // Arrange
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.toDto(farm)).thenReturn(farmResponseDTO);

        // Act
        FarmResponseDTO result = farmService.getFarmById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(farmResponseDTO.id(), result.id());  // Correct access for the record field
        assertTrue(result.fieldIds().contains(1L));  // Check that fieldIds are mapped properly
    }

    @Test
    public void getFarmById_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> farmService.getFarmById(1L));
    }

    @Test
    public void updateFarm_ShouldReturnUpdatedFarmResponseDTO_WithUpdatedFields() {
        // Arrange
        FarmRequestDTO updatedFarmRequestDTO = new FarmRequestDTO("UpdatedFarm", "UpdatedLocation", 200.0, LocalDate.now());
        Farm updatedFarm = new Farm(1L, "UpdatedFarm", "UpdatedLocation", 200.0, LocalDate.now(), List.of(field));
        FarmResponseDTO updatedFarmResponseDTO = new FarmResponseDTO(1L, "UpdatedFarm", "UpdatedLocation", 200.0, LocalDate.now(), List.of(1L));

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmRepository.save(any(Farm.class))).thenReturn(updatedFarm);
        when(farmMapper.toDto(updatedFarm)).thenReturn(updatedFarmResponseDTO);

        // Act
        FarmResponseDTO result = farmService.updateFarm(1L, updatedFarmRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedFarm", result.name());
        assertEquals("UpdatedLocation", result.location());
        assertTrue(result.fieldIds().contains(1L));  // Ensure the fieldId is returned in the response
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    public void deleteFarm_ShouldDeleteFarm_WithFields() {
        // Arrange
        when(farmRepository.existsById(1L)).thenReturn(true);

        // Act
        farmService.deleteFarm(1L);

        // Assert
        verify(farmRepository, times(1)).deleteById(1L);
    }

    @Test
    public void searchFarms_ShouldReturnFarmsList_WithFields() {
        // Arrange
        when(farmRepository.searchFarms("Farm1", "Location1", 100.0)).thenReturn(List.of(farm));

        // Act
        List<Farm> result = farmService.searchFarms("Farm1", "Location1", 100.0);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getFields().size() > 0);  // Check if fields are included
    }
}

