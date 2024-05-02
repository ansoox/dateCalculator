package com.example.datecalculator.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.datecalculator.dto.HistoryDto;
import com.example.datecalculator.model.History;
import com.example.datecalculator.model.User;
import com.example.datecalculator.repository.HistoryRepository;
import com.example.datecalculator.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HistoryService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class HistoryServiceDiffblueTest {
    @MockBean
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryService historyService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link HistoryService#addHistory(HistoryDto)}
     */
    @Test
    void testAddHistory() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        History history = new History();
        history.setCreatedAt(mock(Timestamp.class));
        history.setDiffInDays(mock(Timestamp.class));
        history.setFirstDate(mock(Timestamp.class));
        history.setSecondDate(mock(Timestamp.class));
        history.setUpdatedAt(mock(Timestamp.class));
        history.setUser(user);
        when(historyRepository.save(Mockito.<History>any())).thenReturn(history);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act
        History actualAddHistoryResult = historyService.addHistory(historyDto);

        // Assert
        verify(userRepository).findById(1L);
        verify(historyRepository).save(isA(History.class));
        assertSame(history, actualAddHistoryResult);
    }

    /**
     * Method under test: {@link HistoryService#addHistory(HistoryDto)}
     */
    @Test
    void testAddHistory2() {
        // Arrange
        when(historyRepository.save(Mockito.<History>any())).thenThrow(new RuntimeException("foo"));

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.addHistory(historyDto));
        verify(userRepository).findById(1L);
        verify(historyRepository).save(isA(History.class));
    }

    /**
     * Method under test: {@link HistoryService#addHistory(HistoryDto)}
     */
    @Test
    void testAddHistory3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.addHistory(historyDto));
        verify(userRepository).findById(1L);
    }

    /**
     * Method under test: {@link HistoryService#getHistoryByUserId(Long)}
     */
    @Test
    void testGetHistoryByUserId() {
        // Arrange
        ArrayList<History> historyList = new ArrayList<>();
        when(historyRepository.findByUserId(Mockito.<Long>any())).thenReturn(historyList);

        // Act
        List<History> actualHistoryByUserId = historyService.getHistoryByUserId(1L);

        // Assert
        verify(historyRepository).findByUserId(1L);
        assertTrue(actualHistoryByUserId.isEmpty());
        assertSame(historyList, actualHistoryByUserId);
    }

    /**
     * Method under test: {@link HistoryService#getHistoryByUserId(Long)}
     */
    @Test
    void testGetHistoryByUserId2() {
        // Arrange
        when(historyRepository.findByUserId(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.getHistoryByUserId(1L));
        verify(historyRepository).findByUserId(1L);
    }

    /**
     * Method under test: {@link HistoryService#updateHistory(Long, HistoryDto)}
     */
    @Test
    void testUpdateHistory() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        History history = new History();
        history.setCreatedAt(mock(Timestamp.class));
        history.setDiffInDays(mock(Timestamp.class));
        history.setFirstDate(mock(Timestamp.class));
        history.setSecondDate(mock(Timestamp.class));
        history.setUpdatedAt(mock(Timestamp.class));
        history.setUser(user);
        Optional<History> ofResult = Optional.of(history);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));

        History history2 = new History();
        history2.setCreatedAt(mock(Timestamp.class));
        history2.setDiffInDays(mock(Timestamp.class));
        history2.setFirstDate(mock(Timestamp.class));
        history2.setSecondDate(mock(Timestamp.class));
        history2.setUpdatedAt(mock(Timestamp.class));
        history2.setUser(user2);
        when(historyRepository.save(Mockito.<History>any())).thenReturn(history2);
        when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act
        History actualUpdateHistoryResult = historyService.updateHistory(1L, historyDto);

        // Assert
        verify(historyRepository).findById(1L);
        verify(historyRepository).save(isA(History.class));
        assertSame(history2, actualUpdateHistoryResult);
    }

    /**
     * Method under test: {@link HistoryService#updateHistory(Long, HistoryDto)}
     */
    @Test
    void testUpdateHistory2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        History history = new History();
        history.setCreatedAt(mock(Timestamp.class));
        history.setDiffInDays(mock(Timestamp.class));
        history.setFirstDate(mock(Timestamp.class));
        history.setSecondDate(mock(Timestamp.class));
        history.setUpdatedAt(mock(Timestamp.class));
        history.setUser(user);
        Optional<History> ofResult = Optional.of(history);
        when(historyRepository.save(Mockito.<History>any())).thenThrow(new RuntimeException("foo"));
        when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.updateHistory(1L, historyDto));
        verify(historyRepository).findById(1L);
        verify(historyRepository).save(isA(History.class));
    }

    /**
     * Method under test: {@link HistoryService#updateHistory(Long, HistoryDto)}
     */
    @Test
    void testUpdateHistory3() {
        // Arrange
        Optional<History> emptyResult = Optional.empty();
        when(historyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setFirstDate(mock(Timestamp.class));
        historyDto.setSecondDate(mock(Timestamp.class));
        historyDto.setUserId(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.updateHistory(1L, historyDto));
        verify(historyRepository).findById(1L);
    }

    /**
     * Method under test: {@link HistoryService#deleteHistory(Long)}
     */
    @Test
    void testDeleteHistory() {
        // Arrange
        doNothing().when(historyRepository).deleteById(Mockito.<Long>any());
        when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        boolean actualDeleteHistoryResult = historyService.deleteHistory(1L);

        // Assert
        verify(historyRepository).deleteById(1L);
        verify(historyRepository).existsById(1L);
        assertTrue(actualDeleteHistoryResult);
    }

    /**
     * Method under test: {@link HistoryService#deleteHistory(Long)}
     */
    @Test
    void testDeleteHistory2() {
        // Arrange
        doThrow(new RuntimeException("foo")).when(historyRepository).deleteById(Mockito.<Long>any());
        when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.deleteHistory(1L));
        verify(historyRepository).deleteById(1L);
        verify(historyRepository).existsById(1L);
    }

    /**
     * Method under test: {@link HistoryService#deleteHistory(Long)}
     */
    @Test
    void testDeleteHistory3() {
        // Arrange
        when(historyRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act
        boolean actualDeleteHistoryResult = historyService.deleteHistory(1L);

        // Assert
        verify(historyRepository).existsById(1L);
        assertFalse(actualDeleteHistoryResult);
    }

    /**
     * Method under test: {@link HistoryService#getAllHistories()}
     */
    @Test
    void testGetAllHistories() {
        // Arrange
        ArrayList<History> historyList = new ArrayList<>();
        when(historyRepository.findAll()).thenReturn(historyList);

        // Act
        List<History> actualAllHistories = historyService.getAllHistories();

        // Assert
        verify(historyRepository).findAll();
        assertTrue(actualAllHistories.isEmpty());
        assertSame(historyList, actualAllHistories);
    }

    /**
     * Method under test: {@link HistoryService#getAllHistories()}
     */
    @Test
    void testGetAllHistories2() {
        // Arrange
        when(historyRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.getAllHistories());
        verify(historyRepository).findAll();
    }

    /**
     * Method under test: {@link HistoryService#findById(Long)}
     */
    @Test
    void testFindById() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        History history = new History();
        history.setCreatedAt(mock(Timestamp.class));
        history.setDiffInDays(mock(Timestamp.class));
        history.setFirstDate(mock(Timestamp.class));
        history.setSecondDate(mock(Timestamp.class));
        history.setUpdatedAt(mock(Timestamp.class));
        history.setUser(user);
        Optional<History> ofResult = Optional.of(history);
        when(historyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        History actualFindByIdResult = historyService.findById(1L);

        // Assert
        verify(historyRepository).findById(1L);
        assertSame(history, actualFindByIdResult);
    }

    /**
     * Method under test: {@link HistoryService#findById(Long)}
     */
    @Test
    void testFindById2() {
        // Arrange
        when(historyRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> historyService.findById(1L));
        verify(historyRepository).findById(1L);
    }
}
