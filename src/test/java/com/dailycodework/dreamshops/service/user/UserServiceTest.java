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
 * Testes unitários para UserService
 * 
 * Cobertura de cenários:
 * - CRUD completo de usuários
 * - Validações de negócio
 * - Tratamento de exceções
 * - Conversão de DTOs
 * - Autenticação
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private CreateUserRequest createUserRequest;
    private UserUpdateRequest updateUserRequest;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPassword("encodedPassword");

        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");
        createUserRequest.setFirstName("John");
        createUserRequest.setLastName("Doe");
        createUserRequest.setPassword("plainPassword");

        updateUserRequest = new UserUpdateRequest();
        updateUserRequest.setFirstName("Jane");
        updateUserRequest.setLastName("Smith");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void shouldGetUserByIdSuccessfully() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // When
        User result = userService.getUserById(userId);

        // Then
        assertNotNull(result, "Usuário não deve ser null");
        assertEquals(testUser.getId(), result.getId(), "ID deve corresponder");
        assertEquals(testUser.getEmail(), result.getEmail(), "Email deve corresponder");
        assertEquals(testUser.getFirstName(), result.getFirstName(), "Nome deve corresponder");
        assertEquals(testUser.getLastName(), result.getLastName(), "Sobrenome deve corresponder");

        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado por ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getUserById(userId),
            "Deve lançar RuntimeException quando usuário não encontrado");

        assertEquals("User not found", exception.getMessage(), 
            "Mensagem de erro deve estar correta");

        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        // Given
        String encodedPassword = "encodedPassword123";
        
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.createUser(createUserRequest);

        // Then
        assertNotNull(result, "Usuário criado não deve ser null");
        assertEquals(testUser.getId(), result.getId(), "ID deve corresponder");
        assertEquals(testUser.getEmail(), result.getEmail(), "Email deve corresponder");

        verify(userRepository).existsByEmail(createUserRequest.getEmail());
        verify(passwordEncoder).encode(createUserRequest.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);

        // When & Then
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
            () -> userService.createUser(createUserRequest),
            "Deve lançar AlreadyExistsException quando email já existe");

        assertEquals(createUserRequest.getEmail() + " already exists", exception.getMessage(),
            "Mensagem de erro deve estar correta");

        verify(userRepository).existsByEmail(createUserRequest.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void shouldUpdateUserSuccessfully() {
        // Given
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail(testUser.getEmail());
        updatedUser.setFirstName(updateUserRequest.getFirstName());
        updatedUser.setLastName(updateUserRequest.getLastName());
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        User result = userService.updateUser(updateUserRequest, userId);

        // Then
        assertNotNull(result, "Usuário atualizado não deve ser null");
        assertEquals(updateUserRequest.getFirstName(), result.getFirstName(), 
            "Nome deve ser atualizado");
        assertEquals(updateUserRequest.getLastName(), result.getLastName(), 
            "Sobrenome deve ser atualizado");

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.updateUser(updateUserRequest, userId),
            "Deve lançar RuntimeException quando usuário não encontrado para atualização");

        assertEquals("User not found", exception.getMessage(),
            "Mensagem de erro deve estar correta");

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void shouldDeleteUserSuccessfully() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // When
        assertDoesNotThrow(() -> userService.deleteUser(userId),
            "Não deve lançar exceção ao deletar usuário existente");

        // Then
        verify(userRepository).findById(userId);
        verify(userRepository).delete(testUser);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.deleteUser(userId),
            "Deve lançar RuntimeException quando usuário não encontrado para deleção");

        assertEquals("User not found!", exception.getMessage(),
            "Mensagem de erro deve estar correta");

        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve converter User para UserDto corretamente")
    void shouldConvertUserToDtoCorrectly() {
        // Given
        when(modelMapper.map(testUser, UserDto.class)).thenReturn(userDto);

        // When
        UserDto result = userService.convertUserToDto(testUser);

        // Then
        assertNotNull(result, "UserDto não deve ser null");
        assertEquals(userDto.getId(), result.getId(), "ID deve corresponder");
        assertEquals(userDto.getEmail(), result.getEmail(), "Email deve corresponder");
        assertEquals(userDto.getFirstName(), result.getFirstName(), "Nome deve corresponder");
        assertEquals(userDto.getLastName(), result.getLastName(), "Sobrenome deve corresponder");

        verify(modelMapper).map(testUser, UserDto.class);
    }

    @Test
    @DisplayName("Deve obter usuário autenticado com sucesso")
    void shouldGetAuthenticatedUserSuccessfully() {
        // Given
        String userEmail = "authenticated@example.com";
        
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(testUser);

        // When
        User result = userService.getAuthenticatedUser();

        // Then
        assertNotNull(result, "Usuário autenticado não deve ser null");
        assertEquals(testUser.getId(), result.getId(), "ID deve corresponder");
        assertEquals(testUser.getEmail(), result.getEmail(), "Email deve corresponder");

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        verify(userRepository).findByEmail(userEmail);
    }

    @Test
    @DisplayName("Deve tratar caso quando não há autenticação no SecurityContext")
    void shouldHandleNoAuthenticationInSecurityContext() {
        // Given
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class,
            () -> userService.getAuthenticatedUser(),
            "Deve lançar NullPointerException quando não há autenticação");

        verify(securityContext).getAuthentication();
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    @DisplayName("Deve validar criação de usuário com dados completos")
    void shouldValidateUserCreationWithCompleteData() {
        // Given
        CreateUserRequest completeRequest = new CreateUserRequest();
        completeRequest.setEmail("complete@example.com");
        completeRequest.setFirstName("Complete");
        completeRequest.setLastName("User");
        completeRequest.setPassword("securePassword123");

        when(userRepository.existsByEmail(completeRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(completeRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setId(1L);
            return userToSave;
        });

        // When
        User result = userService.createUser(completeRequest);

        // Then
        assertNotNull(result, "Usuário criado não deve ser null");
        assertEquals(completeRequest.getEmail(), result.getEmail(), "Email deve corresponder");
        assertEquals(completeRequest.getFirstName(), result.getFirstName(), "Nome deve corresponder");
        assertEquals(completeRequest.getLastName(), result.getLastName(), "Sobrenome deve corresponder");
        assertEquals("encodedPassword", result.getPassword(), "Senha deve estar codificada");

        verify(userRepository).existsByEmail(completeRequest.getEmail());
        verify(passwordEncoder).encode(completeRequest.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve manter dados não atualizáveis durante update")
    void shouldKeepNonUpdatableFieldsDuringUpdate() {
        // Given
        Long userId = 1L;
        String originalEmail = testUser.getEmail();
        String originalPassword = testUser.getPassword();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User result = userService.updateUser(updateUserRequest, userId);

        // Then
        assertEquals(originalEmail, result.getEmail(), "Email não deve ser alterado");
        assertEquals(originalPassword, result.getPassword(), "Senha não deve ser alterada");
        assertEquals(updateUserRequest.getFirstName(), result.getFirstName(), "Nome deve ser atualizado");
        assertEquals(updateUserRequest.getLastName(), result.getLastName(), "Sobrenome deve ser atualizado");

        verify(userRepository).findById(userId);
        verify(userRepository).save(testUser);
    }
}