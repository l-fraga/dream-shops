package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.request.CreateUserRequest;
import com.dailycodework.dreamshops.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para UserService seguindo o padrão AAA (Arrange, Act, Assert)
 * e as melhores práticas de testes com JUnit 5 e Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Testes Unitários")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private CreateUserRequest createUserRequest;
    private UserUpdateRequest updateUserRequest;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        // ARRANGE - Preparar dados de teste
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("João");
        testUser.setLastName("Silva");
        testUser.setEmail("joao@email.com");
        testUser.setPassword("encodedPassword123");

        createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("João");
        createUserRequest.setLastName("Silva");
        createUserRequest.setEmail("joao@email.com");
        createUserRequest.setPassword("password123");

        updateUserRequest = new UserUpdateRequest();
        updateUserRequest.setFirstName("João Atualizado");
        updateUserRequest.setLastName("Silva Atualizado");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("João");
        userDto.setLastName("Silva");
        userDto.setEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve retornar usuário quando ID existe")
    void shouldReturnUserWhenIdExists() {
        // ARRANGE
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // ACT
        User actualUser = userService.getUserById(userId);

        // ASSERT
        assertNotNull(actualUser);
        assertEquals(testUser.getId(), actualUser.getId());
        assertEquals(testUser.getFirstName(), actualUser.getFirstName());
        assertEquals(testUser.getEmail(), actualUser.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        // ARRANGE
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso quando email não existe")
    void shouldCreateUserSuccessfullyWhenEmailDoesNotExist() {
        // ARRANGE
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // ACT
        User createdUser = userService.createUser(createUserRequest);

        // ASSERT
        assertNotNull(createdUser);
        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).existsByEmail(createUserRequest.getEmail());
        verify(passwordEncoder, times(1)).encode(createUserRequest.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar usuário com email existente")
    void shouldThrowExceptionWhenCreatingUserWithExistingEmail() {
        // ARRANGE
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);

        // ACT & ASSERT
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.createUser(createUserRequest);
        });

        assertEquals("joao@email.com already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(createUserRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso quando ID existe")
    void shouldUpdateUserSuccessfullyWhenIdExists() {
        // ARRANGE
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("João Atualizado");
        updatedUser.setLastName("Silva Atualizado");
        updatedUser.setEmail("joao@email.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // ACT
        User result = userService.updateUser(updateUserRequest, userId);

        // ASSERT
        assertNotNull(result);
        assertEquals(updateUserRequest.getFirstName(), result.getFirstName());
        assertEquals(updateUserRequest.getLastName(), result.getLastName());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar atualizar usuário inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // ARRANGE
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(updateUserRequest, userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso quando ID existe")
    void shouldDeleteUserSuccessfullyWhenIdExists() {
        // ARRANGE
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // ACT
        userService.deleteUser(userId);

        // ASSERT
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar usuário inexistente")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // ARRANGE
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found!", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("Deve converter usuário para DTO com sucesso")
    void shouldConvertUserToDtoSuccessfully() {
        // ARRANGE
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(userDto);

        // ACT
        UserDto result = userService.convertUserToDto(testUser);

        // ASSERT
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(modelMapper, times(1)).map(testUser, UserDto.class);
    }

    @Test
    @DisplayName("Deve retornar usuário autenticado com sucesso")
    void shouldReturnAuthenticatedUserSuccessfully() {
        // ARRANGE
        String userEmail = "joao@email.com";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userEmail);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(userEmail)).thenReturn(testUser);

        // ACT
        User authenticatedUser = userService.getAuthenticatedUser();

        // ASSERT
        assertNotNull(authenticatedUser);
        assertEquals(testUser.getId(), authenticatedUser.getId());
        assertEquals(testUser.getEmail(), authenticatedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    @DisplayName("Deve retornar null quando usuário autenticado não encontrado")
    void shouldReturnNullWhenAuthenticatedUserNotFound() {
        // ARRANGE
        String userEmail = "inexistente@email.com";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userEmail);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        // ACT
        User authenticatedUser = userService.getAuthenticatedUser();

        // ASSERT
        assertNull(authenticatedUser);
        verify(userRepository, times(1)).findByEmail(userEmail);
    }
} 