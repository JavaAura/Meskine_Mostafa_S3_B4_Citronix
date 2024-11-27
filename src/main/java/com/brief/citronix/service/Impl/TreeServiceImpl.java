package com.brief.citronix.service.Impl;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Tree;
import com.brief.citronix.model.mapper.TreeMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.Interface.TreeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;
    private final TreeMapper treeMapper;

    @Override
    public TreeResponseDTO createTree(TreeRequestDTO treeRequestDTO) {
        Field field = fieldRepository.findById(treeRequestDTO.fieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field with id " + treeRequestDTO.fieldId() + " not found."));

        Tree tree = treeMapper.toEntity(treeRequestDTO);
        tree.setField(field);

        Tree savedTree = treeRepository.save(tree);
        return treeMapper.toDto(savedTree);
    }

    @Override
    public TreeResponseDTO getTreeById(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with id " + id + " not found."));
        return treeMapper.toDto(tree);
    }

    @Override
    public List<TreeResponseDTO> getAllTrees() {
        return treeRepository.findAll().stream()
                .map(treeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TreeResponseDTO updateTree(Long id, TreeRequestDTO treeRequestDTO) {
        Tree existingTree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree with id " + id + " not found."));

        Field field = fieldRepository.findById(treeRequestDTO.fieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field with id " + treeRequestDTO.fieldId() + " not found."));

        existingTree.setPlantingDate(treeRequestDTO.plantingDate());
        existingTree.setField(field);

        Tree updatedTree = treeRepository.save(existingTree);
        return treeMapper.toDto(updatedTree);
    }

    @Override
    public void deleteTree(Long id) {
        if (!treeRepository.existsById(id)) {
            throw new EntityNotFoundException("Tree with id " + id + " not found.");
        }
        treeRepository.deleteById(id);
    }
}