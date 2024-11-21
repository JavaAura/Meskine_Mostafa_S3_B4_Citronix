package com.brief.citronix.service.Interface;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;

import java.util.List;

public interface TreeService {
    TreeResponseDTO createTree(TreeRequestDTO treeRequestDTO);
    TreeResponseDTO getTreeById(Long id);
    List<TreeResponseDTO> getAllTrees();
    TreeResponseDTO updateTree(Long id, TreeRequestDTO treeRequestDTO);
    void deleteTree(Long id);
}

