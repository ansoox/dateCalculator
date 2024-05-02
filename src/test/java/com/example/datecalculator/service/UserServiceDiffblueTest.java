package com.example.datecalculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.datecalculator.cache.EntityCache;
import com.example.datecalculator.dto.UserDto;
import com.example.datecalculator.dto.responsedto.UserResponseDto;
import com.example.datecalculator.exception.BadRequestException;
import com.example.datecalculator.exception.NotFoundException;
import com.example.datecalculator.exception.ServerException;
import com.example.datecalculator.model.Date;
import com.example.datecalculator.model.User;
import com.example.datecalculator.repository.DateRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceDiffblueTest {
    @MockBean
    private DateRepository dateRepository;

    @MockBean
    private EntityCache<Long, Object> entityCache;

    @MockBean
    private HistoryRepository historyRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById() {
        // Arrange
        when(entityCache.get(Mockito.<Long>any())).thenReturn("Get");

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findById(1L));
        verify(entityCache).get(eq(1984L));
    }

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById2() {
        // Arrange
        when(entityCache.get(Mockito.<Long>any())).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findById(1L));
        verify(entityCache).get(eq(1984L));
    }

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById3() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Some error on server occurred");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        when(entityCache.get(Mockito.<Long>any())).thenReturn(user);

        // Act
        User actualFindByIdResult = userService.findById(1L);

        // Assert
        verify(entityCache).get(eq(1984L));
        assertSame(user, actualFindByIdResult);
    }

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById4() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(entityCache.get(Mockito.<Long>any())).thenReturn(null);
        doNothing().when(entityCache).put(Mockito.<Long>any(), Mockito.<Object>any());

        // Act
        User actualFindByIdResult = userService.findById(1L);

        // Assert
        verify(entityCache).get(eq(1984L));
        verify(entityCache).put(eq(1984L), isA(Object.class));
        verify(userRepository).findById(eq(1L));
        assertSame(user, actualFindByIdResult);
    }

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById5() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(null);
        when(entityCache.get(Mockito.<Long>any())).thenReturn(null);

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findById(1L));
        verify(entityCache).get(eq(1984L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#findById(Long)}
     */
    @Test
    void testFindById6() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        when(entityCache.get(Mockito.<Long>any())).thenReturn(null);

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findById(1L));
        verify(entityCache).get(eq(1984L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Optional<List<User>> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertFalse(actualAllUsers.isPresent());
    }

    /**
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        doNothing().when(entityCache).put(Mockito.<Long>any(), Mockito.<Object>any());

        // Act
        Optional<List<User>> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(entityCache).put(isNull(), isA(Object.class));
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isPresent());
    }

    /**
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("42");
        user2.setPassword("Password");
        user2.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        doNothing().when(entityCache).put(Mockito.<Long>any(), Mockito.<Object>any());

        // Act
        Optional<List<User>> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(entityCache, atLeast(1)).put(isNull(), Mockito.<Object>any());
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isPresent());
    }

    /**
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers4() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        doThrow(new BadRequestException("An error occurred")).when(entityCache)
                .put(Mockito.<Long>any(), Mockito.<Object>any());

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.getAllUsers());
        verify(entityCache).put(isNull(), isA(Object.class));
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName() {
        // Arrange
        when(userRepository.searchByName(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<UserResponseDto> actualFindUsersByNameResult = userService.findUsersByName("Name");

        // Assert
        verify(userRepository).searchByName(eq("Name"));
        assertTrue(actualFindUsersByNameResult.isEmpty());
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName(" ");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.searchByName(Mockito.<String>any())).thenReturn(userList);

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findUsersByName("Name"));
        verify(userRepository).searchByName(eq("Name"));
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName3() {
        // Arrange, Act and Assert
        assertThrows(BadRequestException.class, () -> userService.findUsersByName(" "));
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName4() {
        // Arrange, Act and Assert
        assertThrows(BadRequestException.class, () -> userService.findUsersByName(null));
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName5() {
        // Arrange
        when(userRepository.searchByName(Mockito.<String>any())).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findUsersByName("Name"));
        verify(userRepository).searchByName(eq("Name"));
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName6() {
        // Arrange
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn("Name");
        when(user.getCreatedAt()).thenReturn(mock(Timestamp.class));
        when(user.getUpdatedAt()).thenReturn(mock(Timestamp.class));
        ArrayList<Date> dateList = new ArrayList<>();
        when(user.getDates()).thenReturn(dateList);
        doNothing().when(user).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(user).setName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUpdatedAt(Mockito.<Timestamp>any());
        user.setCreatedAt(mock(Timestamp.class));
        user.setName(" ");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.searchByName(Mockito.<String>any())).thenReturn(userList);

        // Act
        List<UserResponseDto> actualFindUsersByNameResult = userService.findUsersByName("Name");

        // Assert
        verify(user).getCreatedAt();
        verify(user).getDates();
        verify(user).getId();
        verify(user).getName();
        verify(user).getUpdatedAt();
        verify(user).setCreatedAt(isA(Timestamp.class));
        verify(user).setName(eq(" "));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setUpdatedAt(isA(Timestamp.class));
        verify(userRepository).searchByName(eq("Name"));
        assertEquals(1, actualFindUsersByNameResult.size());
        UserResponseDto getResult = actualFindUsersByNameResult.get(0);
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(dateList, getResult.getDates());
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName7() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName(" ");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        Date date = new Date();
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getName()).thenReturn("Name");
        when(user2.getCreatedAt()).thenReturn(mock(Timestamp.class));
        when(user2.getUpdatedAt()).thenReturn(mock(Timestamp.class));
        when(user2.getDates()).thenReturn(dateList);
        doNothing().when(user2).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(user2).setName(Mockito.<String>any());
        doNothing().when(user2).setPassword(Mockito.<String>any());
        doNothing().when(user2).setUpdatedAt(Mockito.<Timestamp>any());
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName(" ");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(userRepository.searchByName(Mockito.<String>any())).thenReturn(userList);

        // Act
        List<UserResponseDto> actualFindUsersByNameResult = userService.findUsersByName("Name");

        // Assert
        verify(user2).getCreatedAt();
        verify(user2).getDates();
        verify(user2).getId();
        verify(user2).getName();
        verify(user2).getUpdatedAt();
        verify(user2).setCreatedAt(isA(Timestamp.class));
        verify(user2).setName(eq(" "));
        verify(user2).setPassword(eq("iloveyou"));
        verify(user2).setUpdatedAt(isA(Timestamp.class));
        verify(userRepository).searchByName(eq("Name"));
        assertEquals(1, actualFindUsersByNameResult.size());
        UserResponseDto getResult = actualFindUsersByNameResult.get(0);
        assertEquals("Name", getResult.getName());
        assertEquals(1, getResult.getDates().size());
        assertEquals(1L, getResult.getId().longValue());
    }

    /**
     * Method under test: {@link UserService#findUsersByName(String)}
     */
    @Test
    void testFindUsersByName8() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName(" ");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));
        Date date = mock(Date.class);
        when(date.getTags()).thenThrow(new BadRequestException("An error occurred"));
        when(date.getUser()).thenReturn(user2);
        when(date.getId()).thenReturn(1L);
        when(date.getCreatedAt()).thenReturn(mock(Timestamp.class));
        when(date.getDate()).thenReturn(mock(Timestamp.class));
        when(date.getUpdatedAt()).thenReturn(mock(Timestamp.class));
        doNothing().when(date).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(date).setDate(Mockito.<Timestamp>any());
        doNothing().when(date).setUpdatedAt(Mockito.<Timestamp>any());
        doNothing().when(date).setUser(Mockito.<User>any());
        date.setCreatedAt(mock(Timestamp.class));
        date.setDate(mock(Timestamp.class));
        date.setUpdatedAt(mock(Timestamp.class));
        date.setUser(user);

        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(date);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn(1L);
        when(user3.getName()).thenReturn("Name");
        when(user3.getCreatedAt()).thenReturn(mock(Timestamp.class));
        when(user3.getUpdatedAt()).thenReturn(mock(Timestamp.class));
        when(user3.getDates()).thenReturn(dateList);
        doNothing().when(user3).setCreatedAt(Mockito.<Timestamp>any());
        doNothing().when(user3).setName(Mockito.<String>any());
        doNothing().when(user3).setPassword(Mockito.<String>any());
        doNothing().when(user3).setUpdatedAt(Mockito.<Timestamp>any());
        user3.setCreatedAt(mock(Timestamp.class));
        user3.setName(" ");
        user3.setPassword("iloveyou");
        user3.setUpdatedAt(mock(Timestamp.class));

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user3);
        when(userRepository.searchByName(Mockito.<String>any())).thenReturn(userList);

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.findUsersByName("Name"));
        verify(date).getCreatedAt();
        verify(date).getDate();
        verify(date).getId();
        verify(date).getTags();
        verify(date).getUpdatedAt();
        verify(date).getUser();
        verify(date).setCreatedAt(isA(Timestamp.class));
        verify(date).setDate(isA(Timestamp.class));
        verify(date).setUpdatedAt(isA(Timestamp.class));
        verify(date).setUser(isA(User.class));
        verify(user3).getCreatedAt();
        verify(user3).getDates();
        verify(user3).getId();
        verify(user3).getName();
        verify(user3).getUpdatedAt();
        verify(user3).setCreatedAt(isA(Timestamp.class));
        verify(user3).setName(eq(" "));
        verify(user3).setPassword(eq("iloveyou"));
        verify(user3).setUpdatedAt(isA(Timestamp.class));
        verify(userRepository).searchByName(eq("Name"));
    }

    /**
     * Method under test: {@link UserService#addUsers(List)}
     */
    @Test
    void testAddUsers() {
        // Arrange, Act and Assert
        assertThrows(NotFoundException.class, () -> userService.addUsers(new ArrayList<>()));
    }

    /**
     * Method under test: {@link UserService#addUsers(List)}
     */
    @Test
    void testAddUsers2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        doNothing().when(entityCache).clear();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Required resources are not found");
        userDto.setPassword("iloveyou");

        ArrayList<UserDto> users = new ArrayList<>();
        users.add(userDto);

        // Act
        ResponseEntity<Object> actualAddUsersResult = userService.addUsers(users);

        // Assert
        verify(entityCache, atLeast(1)).clear();
        verify(userRepository).save(isA(User.class));
        assertEquals(1, ((List<User>) actualAddUsersResult.getBody()).size());
        assertEquals(200, actualAddUsersResult.getStatusCodeValue());
        assertTrue(actualAddUsersResult.hasBody());
        assertTrue(actualAddUsersResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link UserService#addUsers(List)}
     */
    @Test
    void testAddUsers3() {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(entityCache).clear();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Required resources are not found");
        userDto.setPassword("iloveyou");

        ArrayList<UserDto> users = new ArrayList<>();
        users.add(userDto);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.addUsers(users));
        verify(entityCache, atLeast(1)).clear();
    }

    /**
     * Method under test: {@link UserService#addUsers(List)}
     */
    @Test
    void testAddUsers4() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        doNothing().when(entityCache).clear();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Required resources are not found");
        userDto.setPassword("iloveyou");

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setName("42");
        userDto2.setPassword("Password");

        ArrayList<UserDto> users = new ArrayList<>();
        users.add(userDto2);
        users.add(userDto);

        // Act
        ResponseEntity<Object> actualAddUsersResult = userService.addUsers(users);

        // Assert
        verify(entityCache, atLeast(1)).clear();
        verify(userRepository, atLeast(1)).save(Mockito.<User>any());
        assertEquals(2, ((List<User>) actualAddUsersResult.getBody()).size());
        assertEquals(200, actualAddUsersResult.getStatusCodeValue());
        assertTrue(actualAddUsersResult.hasBody());
        assertTrue(actualAddUsersResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link UserService#addUser(UserDto)}
     */
    @Test
    void testAddUser() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        doNothing().when(entityCache).clear();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setPassword("iloveyou");

        // Act
        User actualAddUserResult = userService.addUser(userDto);

        // Assert
        verify(entityCache).clear();
        verify(userRepository).save(isA(User.class));
        assertSame(user, actualAddUserResult);
    }

    /**
     * Method under test: {@link UserService#addUser(UserDto)}
     */
    @Test
    void testAddUser2() {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(entityCache).clear();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setPassword("iloveyou");

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.addUser(userDto));
        verify(entityCache).clear();
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setCreatedAt(mock(Timestamp.class));
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setUpdatedAt(mock(Timestamp.class));
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(entityCache).clear();

        // Act
        User actualUpdateUserResult = userService.updateUser(1L, "Name", "iloveyou");

        // Assert
        verify(entityCache).clear();
        verify(userRepository).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
        assertEquals("Name", actualUpdateUserResult.getName());
        assertEquals("iloveyou", actualUpdateUserResult.getPassword());
        assertSame(user, actualUpdateUserResult);
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser2() {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(entityCache).clear();

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.updateUser(1L, "Name", "iloveyou"));
        verify(entityCache).clear();
    }

    /**
     * Method under test: {@link UserService#updateUser(Long, String, String)}
     */
    @Test
    void testUpdateUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        doNothing().when(entityCache).clear();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, "Name", "iloveyou"));
        verify(entityCache).clear();
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(entityCache).clear();

        // Act
        userService.deleteUser(1L);

        // Assert that nothing has changed
        verify(entityCache).clear();
        verify(userRepository).delete(isA(User.class));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() {
        // Arrange
        doThrow(new BadRequestException("An error occurred")).when(entityCache).clear();

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.deleteUser(1L));
        verify(entityCache).clear();
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        doNothing().when(entityCache).clear();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));
        verify(entityCache).clear();
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#getDatesByUser(Long)}
     */
    @Test
    void testGetDatesByUser() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ArrayList<Date> dateList = new ArrayList<>();
        when(dateRepository.findByUserId(Mockito.<Long>any())).thenReturn(dateList);

        // Act
        List<Date> actualDatesByUser = userService.getDatesByUser(1L);

        // Assert
        verify(dateRepository).findByUserId(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertTrue(actualDatesByUser.isEmpty());
        assertSame(dateList, actualDatesByUser);
    }

    /**
     * Method under test: {@link UserService#getDatesByUser(Long)}
     */
    @Test
    void testGetDatesByUser2() {
        // Arrange
        User user = new User();
        user.setCreatedAt(mock(Timestamp.class));
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setUpdatedAt(mock(Timestamp.class));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(dateRepository.findByUserId(Mockito.<Long>any())).thenThrow(new BadRequestException("An error occurred"));

        // Act and Assert
        assertThrows(ServerException.class, () -> userService.getDatesByUser(1L));
        verify(dateRepository).findByUserId(eq(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link UserService#getDatesByUser(Long)}
     */
    @Test
    void testGetDatesByUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.getDatesByUser(1L));
        verify(userRepository).findById(eq(1L));
    }
}
