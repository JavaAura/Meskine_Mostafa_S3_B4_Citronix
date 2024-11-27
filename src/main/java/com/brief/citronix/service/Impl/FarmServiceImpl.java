package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.FarmRequestDTO;
import com.brief.citronix.model.DTO.response.FarmResponseDTO;
import com.brief.citronix.model.entity.Farm;
import com.brief.citronix.model.mapper.FarmMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.Interface.FarmService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @Override
    public FarmResponseDTO createFarm(FarmRequestDTO farmRequestDTO) {
        Farm farm = farmMapper.toEntity(farmRequestDTO);
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDto(savedFarm);
    }

    public List<Farm> searchFarms(String name, String location, Double area) {
        return farmRepository.searchFarms(name, location, area);
    }

    @Override
    public FarmResponseDTO getFarmById(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id " + id + " not found."));
        return farmMapper.toDto(farm);
    }

    @Override
    public List<FarmResponseDTO> getAllFarms() {
        List<Farm> farms = farmRepository.findAll();
        return farms.stream()
                .map(farmMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FarmResponseDTO updateFarm(Long id, FarmRequestDTO farmRequestDTO) {
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id " + id + " not found."));
        // Update the existing farm
        existingFarm.setName(farmRequestDTO.name());
        existingFarm.setLocation(farmRequestDTO.location());
        existingFarm.setArea(farmRequestDTO.area());
        existingFarm.setCreationDate(farmRequestDTO.creationDate());
        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toDto(updatedFarm);
    }

    @Override
    public void deleteFarm(Long id) {
        if (!farmRepository.existsById(id)) {
            throw new EntityNotFoundException("Farm with id " + id + " not found.");
        }
        farmRepository.deleteById(id);
    }
}