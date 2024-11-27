package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.HarvestDetailRequestDTO;
import com.brief.citronix.model.DTO.response.HarvestDetailResponseDTO;
import com.brief.citronix.model.entity.Harvest;
import com.brief.citronix.model.entity.HarvestDetail;
import com.brief.citronix.model.entity.Tree;
import com.brief.citronix.model.enums.Season;
import com.brief.citronix.model.mapper.HarvestDetailMapper;
import com.brief.citronix.repository.HarvestDetailRepository;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.Interface.HarvestDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestDetailServiceImpl implements HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final HarvestDetailMapper harvestDetailMapper;

    @Override
    public HarvestDetailResponseDTO createHarvestDetail(HarvestDetailRequestDTO harvestDetailRequestDTO) {
        Tree tree = treeRepository.findById(harvestDetailRequestDTO.treeId())
                .orElseThrow(() -> new EntityNotFoundException("Tree with id " + harvestDetailRequestDTO.treeId() + " not found."));

        Harvest harvest = harvestRepository.findById(harvestDetailRequestDTO.harvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + harvestDetailRequestDTO.harvestId() + " not found."));

        // Ensure the tree is harvested only once per season and year
        Season season = harvest.getSeason();
        int year = harvest.getDate().getYear();
        boolean isTreeAlreadyHarvested = harvestDetailRepository.isTreeHarvestedInSeason(tree.getId(), season, year);
        if (isTreeAlreadyHarvested) {
            throw new IllegalStateException("Tree with id " + tree.getId() + " has already been harvested for season " + season + " in year " + year + ".");
        }

        // Validate unitQuantity does not exceed tree productivity
        if (harvestDetailRequestDTO.unitQuantity() > tree.getProductivity()) {
            throw new IllegalArgumentException("Unit quantity " + harvestDetailRequestDTO.unitQuantity() +
                    " exceeds the productivity of tree with id " + tree.getId() + ". (productivity is " + tree.getProductivity() + ")");
        }

        HarvestDetail harvestDetail = harvestDetailMapper.toEntity(harvestDetailRequestDTO);
        harvestDetail.setTree(tree);
        harvestDetail.setHarvest(harvest);

        HarvestDetail savedHarvestDetail = harvestDetailRepository.save(harvestDetail);

        updateTotalQuantity(harvest);

        return harvestDetailMapper.toDto(savedHarvestDetail);
    }

    @Override
    public HarvestDetailResponseDTO getHarvestDetailById(Long id) {
        HarvestDetail harvestDetail = harvestDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HarvestDetail with id " + id + " not found."));
        return harvestDetailMapper.toDto(harvestDetail);
    }

    @Override
    public List<HarvestDetailResponseDTO> getAllHarvestDetails() {
        return harvestDetailRepository.findAll().stream()
                .map(harvestDetailMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public HarvestDetailResponseDTO updateHarvestDetail(Long id, HarvestDetailRequestDTO harvestDetailRequestDTO) {
        HarvestDetail existingHarvestDetail = harvestDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HarvestDetail with id " + id + " not found."));

        Tree tree = treeRepository.findById(harvestDetailRequestDTO.treeId())
                .orElseThrow(() -> new EntityNotFoundException("Tree with id " + harvestDetailRequestDTO.treeId() + " not found."));

        Harvest harvest = harvestRepository.findById(harvestDetailRequestDTO.harvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest with id " + harvestDetailRequestDTO.harvestId() + " not found."));

        // Validate unitQuantity does not exceed tree productivity
        if (harvestDetailRequestDTO.unitQuantity() > tree.getProductivity()) {
            throw new IllegalArgumentException("Unit quantity " + harvestDetailRequestDTO.unitQuantity() +
                    " exceeds the productivity of tree with id " + tree.getId() + ". (productivity is " + tree.getProductivity() + ")");
        }

        existingHarvestDetail.setUnitQuantity(harvestDetailRequestDTO.unitQuantity());
        existingHarvestDetail.setTree(tree);
        existingHarvestDetail.setHarvest(harvest);

        HarvestDetail updatedHarvestDetail = harvestDetailRepository.save(existingHarvestDetail);

        updateTotalQuantity(harvest);

        return harvestDetailMapper.toDto(updatedHarvestDetail);
    }

    @Override
    public void deleteHarvestDetail(Long id) {
        if (!harvestDetailRepository.existsById(id)) {
            throw new EntityNotFoundException("HarvestDetail with id " + id + " not found.");
        }
        harvestDetailRepository.deleteById(id);
    }

    // Validate unitQuantity does not exceed tree productivity
    public void updateTotalQuantity(Harvest harvest) {
        double totalHarvestQuantity = harvestDetailRepository.sumUnitQuantityByHarvest(harvest.getId());
        harvest.setTotalQuantity(totalHarvestQuantity);
        harvestRepository.save(harvest);
    }
}