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
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        when(saleMapper.toDto(any(Sale.class))).thenAnswer(invocation -> {
            Sale savedSale = invocation.getArgument(0);
            return new SaleResponseDTO(
                    savedSale.getId(),
                    savedSale.getSaleDate(),
                    savedSale.getUnitPrice(),
                    savedSale.getClient(),
                    savedSale.getQuantity(),
                    savedSale.getQuantity() * savedSale.getUnitPrice(),
                    savedSale.getHarvest().getId()
            );
        });

        SaleResponseDTO responseDTO = saleService.createSale(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(50.0, responseDTO.quantity());
        assertEquals(500.0, responseDTO.revenue());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void testCreateSale_HarvestNotFound() {
        SaleRequestDTO requestDTO = new SaleRequestDTO(
                LocalDate.now(),
                10.0,
                "Client A",
                50.0,
                1L
        );

        when(harvestRepository.findById(requestDTO.harvestId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> saleService.createSale(requestDTO));
        assertEquals("Harvest with id 1 not found.", exception.getMessage());
    }

    @Test
    void testGetSaleById_Success() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setQuantity(50.0);
        sale.setUnitPrice(10.0);

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(saleMapper.toDto(any(Sale.class))).thenAnswer(invocation -> {
            Sale savedSale = invocation.getArgument(0);
            return new SaleResponseDTO(
                    savedSale.getId(),
                    savedSale.getSaleDate(),
                    savedSale.getUnitPrice(),
                    savedSale.getClient(),
                    savedSale.getQuantity(),
                    savedSale.getQuantity() * savedSale.getUnitPrice(),
                    null
            );
        });

        SaleResponseDTO responseDTO = saleService.getSaleById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(500.0, responseDTO.revenue());
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSaleById_NotFound() {
        when(saleRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> saleService.getSaleById(1L));
        assertEquals("Sale with id 1 not found.", exception.getMessage());
    }

    @Test
    void testGetAllSales() {
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setQuantity(50.0);
        sale1.setUnitPrice(10.0);

        Sale sale2 = new Sale();
        sale2.setId(2L);
        sale2.setQuantity(30.0);
        sale2.setUnitPrice(20.0);

        when(saleRepository.findAll()).thenReturn(List.of(sale1, sale2));
        when(saleMapper.toDto(any(Sale.class)))
                .thenAnswer(invocation -> {
                    Sale saleArg = invocation.getArgument(0);
                    return new SaleResponseDTO(
                            saleArg.getId(),
                            saleArg.getSaleDate(),
                            saleArg.getUnitPrice(),
                            saleArg.getClient(),
                            saleArg.getQuantity(),
                            saleArg.getQuantity() * saleArg.getUnitPrice(),
                            null
                    );
                });

        List<SaleResponseDTO> responseDTOs = saleService.getAllSales();

        assertEquals(2, responseDTOs.size());
        assertEquals(500.0, responseDTOs.get(0).revenue());
        assertEquals(600.0, responseDTOs.get(1).revenue());
    }

    @Test
    void testDeleteSale_Success() {
        when(saleRepository.existsById(1L)).thenReturn(true);

        saleService.deleteSale(1L);

        verify(saleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSale_NotFound() {
        when(saleRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> saleService.deleteSale(1L));
        assertEquals("Sale with id 1 not found.", exception.getMessage());
    }

    @Test
    void testCalculateRemainingQuantity() {
        Harvest harvest = new Harvest();
        harvest.setId(1L);
        harvest.setTotalQuantity(100.0);

        Sale sale1 = new Sale();
        sale1.setHarvest(harvest);
        sale1.setQuantity(40.0);

        Sale sale2 = new Sale();
        sale2.setHarvest(harvest);
        sale2.setQuantity(30.0);

        when(saleRepository.findAll()).thenReturn(List.of(sale1, sale2));

        double remainingQuantity = saleService.calculateRemainingQuantity(harvest);

        assertEquals(30.0, remainingQuantity);
    }
}
