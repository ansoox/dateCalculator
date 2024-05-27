package com.example.datecalculator.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.datecalculator.dto.TagDto;
import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.ServerException;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.Tag;
import com.example.datecalculator.model.User;
import com.example.datecalculator.repository.DateRepository;
import com.example.datecalculator.repository.TagRepository;

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


@ContextConfiguration(classes = {TagService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TagServiceDiffblueTest {
    @MockBean
    private DateRepository dateRepository;

    @MockBean
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    /**
     * Method under test: {@link TagService#addTag(TagDto)}
     */
    @Test
    void testAddTag() {
        // Arrange
        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        when(tagRepository.searchByTagName(Mockito.<String>any())).thenReturn(tag);

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setTagName("Tag Name");

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.addTag(tagDto));
        verify(tagRepository).searchByTagName("Tag Name");
    }

    /**
     * Method under test: {@link TagService#addTag(TagDto)}
     */
    @Test
    void testAddTag2() {
        // Arrange
        when(tagRepository.searchByTagName(Mockito.<String>any())).thenThrow(new NotFoundException("An error occurred"));

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setTagName("Tag Name");

        // Act and Assert
        assertThrows(NotFoundException.class, () -> tagService.addTag(tagDto));
        verify(tagRepository).searchByTagName("Tag Name");
    }

    /**
     * Method under test: {@link TagService#findById(Long)}
     */
    @Test
    void testFindById() {
        // Arrange
        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Tag actualFindByIdResult = tagService.findById(1L);

        // Assert
        verify(tagRepository, atLeast(1)).findById(1L);
        assertSame(tag, actualFindByIdResult);
    }

    /**
     * Method under test: {@link TagService#findById(Long)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Optional<Tag> emptyResult = Optional.empty();
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> tagService.findById(1L));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#findById(Long)}
     */
    @Test
    void testFindById3() {
        // Arrange
        when(tagRepository.findById(Mockito.<Long>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.findById(1L));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#updateTag(Long, String)}
     */
    @Test
    void testUpdateTag() {
        // Arrange
        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.updateTag(1L, "Name"));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#updateTag(Long, String)}
     */
    @Test
    void testUpdateTag2() {
        // Arrange
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(new ArrayList<>());
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);

        Tag tag2 = new Tag();
        tag2.setCreatedAt(mock(Timestamp.class));
        tag2.setName("Name");
        tag2.setUpdatedAt(mock(Timestamp.class));
        when(tagRepository.save(Mockito.<Tag>any())).thenReturn(tag2);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        tagService.updateTag(1L, "Name");

        // Assert
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag, atLeast(1)).setName("Name");
        verify(tag, atLeast(1)).setUpdatedAt(Mockito.<Timestamp>any());
        verify(tagRepository).findById(1L);
        verify(tagRepository).save(isA(Tag.class));
    }

    /**
     * Method under test: {@link TagService#updateTag(Long, String)}
     */
    @Test
    void testUpdateTag3() {
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

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(dateList);
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);

        Tag tag2 = new Tag();
        tag2.setCreatedAt(mock(Timestamp.class));
        tag2.setName("Name");
        tag2.setUpdatedAt(mock(Timestamp.class));
        when(tagRepository.save(Mockito.<Tag>any())).thenReturn(tag2);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        // Act
        tagService.updateTag(1L, "Name");

        // Assert
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag, atLeast(1)).setName("Name");
        verify(tag, atLeast(1)).setUpdatedAt(Mockito.<Timestamp>any());
        verify(tagRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
        verify(tagRepository).save(isA(Tag.class));
    }

    /**
     * Method under test: {@link TagService#updateTag(Long, String)}
     */
    @Test
    void testUpdateTag4() {
        // Arrange
        Optional<Tag> emptyResult = Optional.empty();
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> tagService.updateTag(1L, "Name"));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#updateTag(Long, String)}
     */
    @Test
    void testUpdateTag5() {
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

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(dateList);
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);

        Tag tag2 = new Tag();
        tag2.setCreatedAt(mock(Timestamp.class));
        tag2.setName("Name");
        tag2.setUpdatedAt(mock(Timestamp.class));
        when(tagRepository.save(Mockito.<Tag>any())).thenReturn(tag2);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dateRepository.save(Mockito.<Date>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.updateTag(1L, "Name"));
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag, atLeast(1)).setName("Name");
        verify(tag, atLeast(1)).setUpdatedAt(Mockito.<Timestamp>any());
        verify(tagRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
    }

    /**
     * Method under test: {@link TagService#deleteTag(Long)}
     */
    @Test
    void testDeleteTag() {
        // Arrange
        Tag tag = new Tag();
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.deleteTag(1L));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#deleteTag(Long)}
     */
    @Test
    void testDeleteTag2() {
        // Arrange
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(new ArrayList<>());
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        doNothing().when(tagRepository).delete(Mockito.<Tag>any());
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        tagService.deleteTag(1L);

        // Assert that nothing has changed
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag).setName("Name");
        verify(tag).setUpdatedAt(isA(Timestamp.class));
        verify(tagRepository).delete(isA(Tag.class));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#deleteTag(Long)}
     */
    @Test
    void testDeleteTag3() {
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

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(dateList);
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        doNothing().when(tagRepository).delete(Mockito.<Tag>any());
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        // Act
        tagService.deleteTag(1L);

        // Assert
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag).setName("Name");
        verify(tag).setUpdatedAt(isA(Timestamp.class));
        verify(tagRepository).delete(isA(Tag.class));
        verify(tagRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
    }

    /**
     * Method under test: {@link TagService#deleteTag(Long)}
     */
    @Test
    void testDeleteTag4() {
        // Arrange
        Optional<Tag> emptyResult = Optional.empty();
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> tagService.deleteTag(1L));
        verify(tagRepository).findById(1L);
    }

    /**
     * Method under test: {@link TagService#deleteTag(Long)}
     */
    @Test
    void testDeleteTag5() {
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

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        Tag tag = mock(Tag.class);
        when(tag.getDates()).thenReturn(dateList);
        doNothing().when(tag).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(tag).setName(Mockito.<String>any());
        doNothing().when(tag).setUpdatedAt(Mockito.<Timestamp>any());
        tag.setCreatedAt(mock(Timestamp.class));
        tag.setName("Name");
        tag.setUpdatedAt(mock(Timestamp.class));
        Optional<Tag> ofResult = Optional.of(tag);
        when(tagRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dateRepository.save(Mockito.<Date>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.deleteTag(1L));
        verify(tag).getDates();
        verify(tag).setCreatedAt(isA(Timestamp.class));
        verify(tag).setName("Name");
        verify(tag).setUpdatedAt(isA(Timestamp.class));
        verify(tagRepository).findById(1L);
        verify(dateRepository).save(isA(Date.class));
    }

    /**
     * Method under test: {@link TagService#getAllTags()}
     */
    @Test
    void testGetAllTags() {
        // Arrange
        ArrayList<Tag> tagList = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(tagList);

        // Act
        List<Tag> actualAllTags = tagService.getAllTags();

        // Assert
        verify(tagRepository).findAll();
        assertTrue(actualAllTags.isEmpty());
        assertSame(tagList, actualAllTags);
    }

    /**
     * Method under test: {@link TagService#getAllTags()}
     */
    @Test
    void testGetAllTags2() {
        // Arrange
        when(tagRepository.findAll()).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.getAllTags());
        verify(tagRepository).findAll();
    }

    /**
     * Method under test: {@link TagService#getDatesByTag(Long)}
     */
    @Test
    void testGetDatesByTag() {
        // Arrange
        ArrayList<Date> dateList = new ArrayList<>();
        when(dateRepository.findByTagId(Mockito.<Long>any())).thenReturn(dateList);

        // Act
        List<Date> actualDatesByTag = tagService.getDatesByTag(1L);

        // Assert
        verify(dateRepository).findByTagId(1L);
        assertTrue(actualDatesByTag.isEmpty());
        assertSame(dateList, actualDatesByTag);
    }

    /**
     * Method under test: {@link TagService#getDatesByTag(Long)}
     */
    @Test
    void testGetDatesByTag2() {
        // Arrange
        when(dateRepository.findByTagId(Mockito.<Long>any())).thenThrow(new ServerException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> tagService.getDatesByTag(1L));
        verify(dateRepository).findByTagId(1L);
    }
}
