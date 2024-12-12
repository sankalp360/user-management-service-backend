package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.publicis.users.exception.UserNotFoundException;
import org.publicis.users.model.UserEntity;
import org.publicis.users.repository.UserRepository;
import org.publicis.users.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<UserEntity> mockUsers = Arrays.asList(
                new UserEntity(1L, "John", "Doe", "admin", 30),
                new UserEntity(2L, "Jane", "Doe", "user", 25)
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<UserEntity> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdOrSsn_UserFound() {
        // Arrange
        UserEntity mockUser = new UserEntity(1L, "John", "Doe", "admin", 30);
        when(userRepository.findByIdOrSsn(1L, null)).thenReturn(Optional.of(mockUser));

        // Act
        UserEntity user = userService.getUserByIdOrSsn(1L, null);

        // Assert
        assertNotNull(user);
        verify(userRepository, times(1)).findByIdOrSsn(1L, null);
    }

    @Test
    void testGetUserByIdOrSsn_UserNotFound() {
        // Arrange
        when(userRepository.findByIdOrSsn(anyLong(), anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByIdOrSsn(1L, null));
        verify(userRepository, times(1)).findByIdOrSsn(1L, null);
    }

    @Test
    void testGetUsersByRole() {
        // Arrange
        List<UserEntity> mockUsers = Arrays.asList(
                new UserEntity(1L, "John", "Doe", "admin", 30)
        );
        when(userRepository.findByRole("admin")).thenReturn(mockUsers);

        // Act
        List<UserEntity> users = userService.getUsersByRole("admin");

        // Assert
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findByRole("admin");
    }
}
