package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.SaleRequestDTO;
import com.brief.citronix.model.DTO.response.SaleResponseDTO;

import java.util.List;

public interface SaleService {
    SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO);
    SaleResponseDTO getSaleById(Long id);
    List<SaleResponseDTO> getAllSales();
    SaleResponseDTO updateSale(Long id, SaleRequestDTO saleRequestDTO);
    void deleteSale(Long id);
}