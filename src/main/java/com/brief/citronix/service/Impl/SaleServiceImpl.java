package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.SaleRequestDTO;
import com.brief.citronix.model.DTO.response.SaleResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.Sale;
import com.brief.citronix.model.mapper.SaleMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.repository.SaleRepository;
import com.brief.citronix.service.Interface.SaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    @Override
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {
        Harvest harvest = harvestRepository.findById(saleRequestDTO.harvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + saleRequestDTO.harvestId() + " not found."));

        double remainingQuantity = calculateRemainingQuantity(harvest);

        if (saleRequestDTO.quantity() > remainingQuantity) {
            throw new IllegalStateException("Sale quantity exceeds the remaining harvest quantity. Remaining quantity: " + remainingQuantity);
        }

        Sale sale = saleMapper.toEntity(saleRequestDTO);
        sale.setHarvest(harvest);

        Sale savedSale = saleRepository.save(sale);
        return saleMapper.toDto(savedSale);
    }

    @Override
    public SaleResponseDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale with id " + id + " not found."));
        return saleMapper.toDto(sale);
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO saleRequestDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale with id " + id + " not found."));

        Harvest harvest = harvestRepository.findById(saleRequestDTO.harvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + saleRequestDTO.harvestId() + " not found."));

        double remainingQuantity = calculateRemainingQuantity(harvest);

        if (saleRequestDTO.quantity() > remainingQuantity) {
            throw new IllegalStateException("Sale quantity exceeds the remaining harvest quantity. Remaining quantity: " + remainingQuantity);
        }

        existingSale.setSaleDate(saleRequestDTO.saleDate());
        existingSale.setUnitPrice(saleRequestDTO.unitPrice());
        existingSale.setClient(saleRequestDTO.client());
        existingSale.setQuantity(saleRequestDTO.quantity());
        existingSale.setHarvest(harvest);

        Sale updatedSale = saleRepository.save(existingSale);
        return saleMapper.toDto(updatedSale);
    }

    @Override
    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new EntityNotFoundException("Sale with id " + id + " not found.");
        }
        saleRepository.deleteById(id);
    }

    public double calculateRemainingQuantity(Harvest harvest){
        double totalSalesQuantity = saleRepository.findAll().stream()
                .filter(sale -> sale.getHarvest().getId().equals(harvest.getId()))
                .mapToDouble(Sale::getQuantity)
                .sum();

        return harvest.getTotalQuantity() - totalSalesQuantity;
    }
}