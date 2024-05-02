package com.example.datecalculator.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.datecalculator.dto.DateDto;
import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.ServerException;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.model.User;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.TagRepository;
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

@ContextConfiguration(classes = {DateService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DateServiceDiffblueTest {
    @MockBean
    private DateRepository dateRepository;

    @Autowired
    private DateService dateService;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link DateService#addDate(DateDto)}
     */
    @Test
    void testAddDate() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        when(dateRepository.save(Mockito.<Date>any())).thenReturn(date);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act
        Date actualAddDateResult = dateService.addDate(dateDto);

        // Assert
        verify(userRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
        assertSame(date, actualAddDateResult);
    }

    /**
     * Method under test: {@link DateService#addDate(DateDto)}
     */
    @Test
    void testAddDate2() {
        // Arrange
        when(dateRepository.save(Mockito.<Date>any())).thenThrow(new ServerException("An error occurred"));

        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.addDate(dateDto));
        verify(userRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
    }

    /**
     * Method under test: {@link DateService#addDate(DateDto)}
     */
    @Test
    void testAddDate3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.addDate(dateDto));
        verify(userRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#addTagToDate(Long, DateDto)}
     */
    @Test
    void testAddTagToDate() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));

        Date date2 = new Date();
        date2.setCreatedAt(mock(Timestamp.class));
        date2.setDate(mock(Timestamp.class));
        date2.setUpdatedAt(mock(Timestamp.class));
        date2.setUser(user2);
        when(dateRepository.save(Mockito.<Date>any())).thenReturn(date2);
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult2 = Optional.of(tag);

        Tag tag2 = new Tag();
        tag2.setCreatedAt(mock(Timestamp.class));
        tag2.setName("Name");
        tag2.setUpdatedAt(mock(Timestamp.class));
        when(tagRepository.save(Mockito.<Tag>any())).thenReturn(tag2);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act
        Date actualAddTagToDateResult = dateService.addTagToDate(1L, dateDto);

        // Assert
        verify(dateRepository, atLeast(1)).findById(1L);
        verify(tagRepository, atLeast(1)).findById(1L);
        verify(dateRepository).save(isA(Date.class));
        verify(tagRepository).save(isA(Tag.class));
        assertSame(date2, actualAddTagToDateResult);
    }

    /**
     * Method under test: {@link DateService#addTagToDate(Long, DateDto)}
     */
    @Test
    void testAddTagToDate2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));

        Date date2 = new Date();
        date2.setCreatedAt(mock(Timestamp.class));
        date2.setDate(mock(Timestamp.class));
        date2.setUpdatedAt(mock(Timestamp.class));
        date2.setUser(user2);
        when(dateRepository.save(Mockito.<Date>any())).thenReturn(date2);
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult2 = Optional.of(tag);
        when(tagRepository.save(Mockito.<Tag>any())).thenThrow(new ServerException("An error occurred"));
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.addTagToDate(1L, dateDto));
        verify(dateRepository, atLeast(1)).findById(1L);
        verify(tagRepository, atLeast(1)).findById(1L);
        verify(tagRepository).save(isA(Tag.class));
    }

    /**
     * Method under test: {@link DateService#addTagToDate(Long, DateDto)}
     */
    @Test
    void testAddTagToDate3() {
        // Arrange
        Optional<Date> emptyResult = Optional.empty();
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> dateService.addTagToDate(1L, dateDto));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#addTagToDate(Long, DateDto)}
     */
    @Test
    void testAddTagToDate4() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Tag> emptyResult = Optional.empty();
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> dateService.addTagToDate(1L, dateDto));
        verify(dateRepository).findById(1L);
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#updateDate(Long, DateDto)}
     */
    @Test
    void testUpdateDate() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));

        Date date2 = new Date();
        date2.setCreatedAt(mock(Timestamp.class));
        date2.setDate(mock(Timestamp.class));
        date2.setUpdatedAt(mock(Timestamp.class));
        date2.setUser(user2);
        when(dateRepository.save(Mockito.<Date>any())).thenReturn(date2);
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act
        Date actualUpdateDateResult = dateService.updateDate(1L, dateDto);

        // Assert
        verify(dateRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
        assertSame(date, actualUpdateDateResult);
    }

    /**
     * Method under test: {@link DateService#updateDate(Long, DateDto)}
     */
    @Test
    void testUpdateDate2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);
        when(dateRepository.save(Mockito.<Date>any())).thenThrow(new ServerException("An error occurred"));
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.updateDate(1L, dateDto));
        verify(dateRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
    }

    /**
     * Method under test: {@link DateService#updateDate(Long, DateDto)}
     */
    @Test
    void testUpdateDate3() {
        // Arrange
        Optional<Date> emptyResult = Optional.empty();
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        DateDto dateDto = new DateDto();
        dateDto.setDate(mock(Timestamp.class));
        dateDto.setTagId(1L);
        dateDto.setUserId(1L);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> dateService.updateDate(1L, dateDto));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#deleteDate(Long)}
     */
    @Test
    void testDeleteDate() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);
        doNothing().when(dateRepository).delete(Mockito.<Date>any());
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        dateService.deleteDate(1L);

        // Assert that nothing has changed
        verify(dateRepository).delete(isA(Date.class));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#deleteDate(Long)}
     */
    @Test
    void testDeleteDate2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);
        doThrow(new ServerException("An error occurred")).when(dateRepository).delete(Mockito.<Date>any());
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.deleteDate(1L));
        verify(dateRepository).delete(isA(Date.class));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#deleteDate(Long)}
     */
    @Test
    void testDeleteDate3() {
        // Arrange
        Optional<Date> emptyResult = Optional.empty();
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> dateService.deleteDate(1L));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#getAllDates()}
     */
    @Test
    void testGetAllDates() {
        // Arrange
        ArrayList<Date> dateList = new ArrayList<>();
        when(dateRepository.findAll()).thenReturn(dateList);

        // Act
        List<Date> actualAllDates = dateService.getAllDates();

        // Assert
        verify(dateRepository).findAll();
        assertTrue(actualAllDates.isEmpty());
        assertSame(dateList, actualAllDates);
    }

    /**
     * Method under test: {@link DateService#getAllDates()}
     */
    @Test
    void testGetAllDates2() {
        // Arrange
        when(dateRepository.findAll()).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.getAllDates());
        verify(dateRepository).findAll();
    }

    /**
     * Method under test: {@link DateService#findById(Long)}
     */
    @Test
    void testFindById() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);
        Optional<Date> ofResult = Optional.of(date);
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Date actualFindByIdResult = dateService.findById(1L);

        // Assert
        verify(dateRepository, atLeast(1)).findById(1L);
        assertSame(date, actualFindByIdResult);
    }

    /**
     * Method under test: {@link DateService#findById(Long)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Date> emptyResult = Optional.empty();
        when(dateRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> dateService.findById(1L));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#findById(Long)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(dateRepository.findById(Mockito.<Long>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.findById(1L));
        verify(dateRepository).findById(1L);
    }

    /**
     * Method under test: {@link DateService#getTagsByDate(Long)}
     */
    @Test
    void testGetTagsByDate() {
        // Arrange
        ArrayList<Tag> tagList = new ArrayList<>();
        when(tagRepository.findByDateId(Mockito.<Long>any())).thenReturn(tagList);

        // Act
        List<Tag> actualTagsByDate = dateService.getTagsByDate(1L);

        // Assert
        verify(tagRepository).findByDateId(1L);
        assertTrue(actualTagsByDate.isEmpty());
        assertSame(tagList, actualTagsByDate);
    }

    /**
     * Method under test: {@link DateService#getTagsByDate(Long)}
     */
    @Test
    void testGetTagsByDate2() {
        // Arrange
        when(tagRepository.findByDateId(Mockito.<Long>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> dateService.getTagsByDate(1L));
        verify(tagRepository).findByDateId(1L);
    }
}
