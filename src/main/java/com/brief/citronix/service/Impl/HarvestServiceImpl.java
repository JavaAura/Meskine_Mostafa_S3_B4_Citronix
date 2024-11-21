package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.HarvestRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.enums.Season;
import com.brief.citronix.model.mapper.HarvestMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.service.Interface.HarvestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;

    @Override
    public HarvestResponseDTO createHarvest(HarvestRequestDTO harvestRequestDTO) {
        Harvest harvest = harvestMapper.toEntity(harvestRequestDTO);

        harvest.setSeason(calculateSeason(harvestRequestDTO.date()));

        harvest.setTotalQuantity(0.0);

        Harvest savedHarvest = harvestRepository.save(harvest);
        return harvestMapper.toDto(savedHarvest);
    }

    @Override
    public HarvestResponseDTO getHarvestById(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + id + " not found."));
        return harvestMapper.toDto(harvest);
    }

    @Override
    public List<HarvestResponseDTO> getAllHarvests() {
        return harvestRepository.findAll().stream()
                .map(harvestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public HarvestResponseDTO updateHarvest(Long id, HarvestRequestDTO harvestRequestDTO) {
        Harvest existingHarvest = harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + id + " not found."));

        existingHarvest.setDate(harvestRequestDTO.date());

        existingHarvest.setSeason(calculateSeason(harvestRequestDTO.date()));

        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toDto(updatedHarvest);
    }

    @Override
    public void deleteHarvest(Long id) {
        if (!harvestRepository.existsById(id)) {
            throw new EntityNotFoundException("Harvest with id " + id + " not found.");
        }
        harvestRepository.deleteById(id);
    }

    //calculate the season based on the date.
    private Season calculateSeason(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) {
            return Season.SPRING;
        } else if (month >= 6 && month <= 8) {
            return Season.SUMMER;
        } else if (month >= 9 && month <= 11) {
            return Season.FALL;
        } else {
            return Season.WINTER;
        }
    }
}
