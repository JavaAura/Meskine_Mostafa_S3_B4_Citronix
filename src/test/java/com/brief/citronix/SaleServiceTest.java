package com.brief.citronix;

import com.brief.citronix.model.DTO.request.SaleRequestDTO;
import com.brief.citronix.model.DTO.response.SaleResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.Sale;
import com.brief.citronix.model.mapper.SaleMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.repository.SaleRepository;
import com.brief.citronix.service.Impl.SaleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private SaleMapper saleMapper;

    @InjectMocks
    private SaleServiceImpl saleService;

    @Test
    void testCreateSale_Success() {
        // Arrange
        Harvest harvest = new Harvest();
        harvest.setId(1L);
        harvest.setTotalQuantity(100.0);

        SaleRequestDTO requestDTO = new SaleRequestDTO(
                LocalDate.now(),
                10.0,
                "Client A",
                50.0,
                harvest.getId()
        );

        Sale sale = new Sale();
        sale.setId(1L);
        sale.setQuantity(50.0);
        sale.setHarvest(harvest);

        when(harvestRepository.findById(harvest.getId())).thenReturn(Optional.of(harvest));
        when(saleRepository.findAll()).thenReturn(List.of());
        when(saleMapper.toEntity(requestDTO)).thenReturn(sale);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(saleMapper.toDto(any(Sale.class))).thenReturn(new SaleResponseDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getUnitPrice(),
                sale.getClient(),
                sale.getQuantity(),
                sale.getQuantity() * sale.getUnitPrice(),
                sale.getHarvest().getId()
        ));

        // Act
        SaleResponseDTO responseDTO = saleService.createSale(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(50.0, responseDTO.quantity());
        assertEquals(500.0, responseDTO.revenue());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void testCreateSale_QuantityExceedsRemaining() {
        // Arrange
        Harvest harvest = new Harvest();
        harvest.setId(1L);
        harvest.setTotalQuantity(100.0);

        Sale existingSale = new Sale();
        existingSale.setQuantity(60.0);
        existingSale.setHarvest(harvest);

        SaleRequestDTO requestDTO = new SaleRequestDTO(
                LocalDate.now(),
                10.0,
                "Client A",
                50.0,
                harvest.getId()
        );

        when(harvestRepository.findById(harvest.getId())).thenReturn(Optional.of(harvest));
        when(saleRepository.findAll()).thenReturn(List.of(existingSale));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> saleService.createSale(requestDTO));
        assertEquals("Sale quantity exceeds the remaining harvest quantity. Remaining quantity: 40.0", exception.getMessage());
    }

    @Test
    void testGetSaleById_Success() {
        // Arrange
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setQuantity(50.0);
        sale.setUnitPrice(10.0);

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(saleMapper.toDto(any(Sale.class))).thenReturn(new SaleResponseDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getUnitPrice(),
                sale.getClient(),
                sale.getQuantity(),
                sale.getRevenue(),
                null
        ));

        // Act
        SaleResponseDTO responseDTO = saleService.getSaleById(1L);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(500.0, responseDTO.revenue());
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllSales() {
        // Arrange
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setQuantity(50.0);
        sale1.setUnitPrice(10.0);

        Sale sale2 = new Sale();
        sale2.setId(2L);
        sale2.setQuantity(30.0);
        sale2.setUnitPrice(20.0);

        when(saleRepository.findAll()).thenReturn(Arrays.asList(sale1, sale2));
        when(saleMapper.toDto(any(Sale.class)))
                .thenReturn(
                        new SaleResponseDTO(sale1.getId(), sale1.getSaleDate(), sale1.getUnitPrice(), sale1.getClient(), sale1.getQuantity(), sale1.getRevenue(), null),
                        new SaleResponseDTO(sale2.getId(), sale2.getSaleDate(), sale2.getUnitPrice(), sale2.getClient(), sale2.getQuantity(), sale2.getRevenue(), null)
                );

        // Act
        List<SaleResponseDTO> responseDTOs = saleService.getAllSales();

        // Assert
        assertEquals(2, responseDTOs.size());
        assertEquals(500.0, responseDTOs.get(0).revenue());
        assertEquals(600.0, responseDTOs.get(1).revenue());
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    void testDeleteSale_Success() {
        // Arrange
        when(saleRepository.existsById(1L)).thenReturn(true);

        // Act
        saleService.deleteSale(1L);

        // Assert
        verify(saleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSale_NotFound() {
        // Arrange
        when(saleRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> saleService.deleteSale(1L));
        assertEquals("Sale with id 1 not found.", exception.getMessage());
    }
}