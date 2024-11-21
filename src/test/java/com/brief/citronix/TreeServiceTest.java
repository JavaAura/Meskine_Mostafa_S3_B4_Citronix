package com.brief.citronix;

import com.brief.citronix.model.DTO.request.TreeRequestDTO;
import com.brief.citronix.model.DTO.response.TreeResponseDTO;
import com.brief.citronix.model.entity.Field;
import com.brief.citronix.model.entity.Tree;
import com.brief.citronix.model.mapper.TreeMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.repository.TreeRepository;
import com.brief.citronix.service.Impl.TreeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TreeServiceTest {

    @Mock
    private TreeRepository treeRepository;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private TreeMapper treeMapper;

    @InjectMocks
    private TreeServiceImpl treeService;

    private Field testField;
    private Tree testTree;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testField = new Field();
        testField.setId(1L);

        testTree = new Tree();
        testTree.setId(1L);
        testTree.setPlantingDate(LocalDate.of(2020, 4, 15));
        testTree.setField(testField);
    }

    @Test
    void createTree_ShouldReturnTreeResponseDTO_WhenValidRequest() {
        TreeRequestDTO treeRequestDTO = new TreeRequestDTO(LocalDate.of(2021, 5, 10), testField.getId());

        when(fieldRepository.findById(treeRequestDTO.fieldId())).thenReturn(Optional.of(testField));
        when(treeMapper.toEntity(treeRequestDTO)).thenReturn(testTree);
        when(treeRepository.save(testTree)).thenReturn(testTree);
        when(treeMapper.toDto(testTree)).thenReturn(new TreeResponseDTO(1L, LocalDate.of(2021, 5, 10), 3, 12, 1L, null));

        TreeResponseDTO responseDTO = treeService.createTree(treeRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(3, responseDTO.age());
        assertEquals(12, responseDTO.productivity());
        verify(fieldRepository, times(1)).findById(treeRequestDTO.fieldId());
        verify(treeRepository, times(1)).save(testTree);
    }

    @Test
    void createTree_ShouldThrowException_WhenFieldNotFound() {
        TreeRequestDTO treeRequestDTO = new TreeRequestDTO(LocalDate.of(2021, 5, 10), 999L);

        when(fieldRepository.findById(treeRequestDTO.fieldId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            treeService.createTree(treeRequestDTO);
        });

        assertEquals("Field with id 999 not found.", exception.getMessage());
        verify(fieldRepository, times(1)).findById(treeRequestDTO.fieldId());
        verifyNoInteractions(treeMapper);
        verifyNoInteractions(treeRepository);
    }

    @Test
    void getTreeById_ShouldReturnTreeResponseDTO_WhenTreeExists() {
        when(treeRepository.findById(1L)).thenReturn(Optional.of(testTree));
        when(treeMapper.toDto(testTree)).thenReturn(new TreeResponseDTO(1L, LocalDate.of(2020, 4, 15), 4, 12, 1L, null));

        TreeResponseDTO responseDTO = treeService.getTreeById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(4, responseDTO.age());
        assertEquals(12, responseDTO.productivity());
        verify(treeRepository, times(1)).findById(1L);
    }

    @Test
    void getTreeById_ShouldThrowException_WhenTreeDoesNotExist() {
        when(treeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            treeService.getTreeById(1L);
        });

        assertEquals("Tree with id 1 not found.", exception.getMessage());
        verify(treeRepository, times(1)).findById(1L);
    }

    @Test
    void updateTree_ShouldReturnUpdatedTreeResponseDTO_WhenTreeExists() {
        TreeRequestDTO treeRequestDTO = new TreeRequestDTO(LocalDate.of(2022, 3, 20), 1L);

        when(treeRepository.findById(1L)).thenReturn(Optional.of(testTree));
        when(fieldRepository.findById(1L)).thenReturn(Optional.of(testField));
        when(treeRepository.save(any(Tree.class))).thenReturn(testTree);
        when(treeMapper.toDto(testTree)).thenReturn(new TreeResponseDTO(1L, LocalDate.of(2022, 3, 20), 2, 2, 1L, null));

        TreeResponseDTO responseDTO = treeService.updateTree(1L, treeRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(2, responseDTO.age());
        verify(treeRepository, times(1)).findById(1L);
        verify(treeRepository, times(1)).save(testTree);
    }

    @Test
    void deleteTree_ShouldCallRepositoryDelete_WhenTreeExists() {
        when(treeRepository.existsById(1L)).thenReturn(true);

        treeService.deleteTree(1L);

        verify(treeRepository, times(1)).existsById(1L);
        verify(treeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTree_ShouldThrowException_WhenTreeDoesNotExist() {
        when(treeRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            treeService.deleteTree(1L);
        });

        assertEquals("Tree with id 1 not found.", exception.getMessage());
        verify(treeRepository, times(1)).existsById(1L);
    }
}
