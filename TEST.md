# 🧪 Exemplo Didático de Teste Unitário em Java com JUnit, Mockito e Modelo AAA

Este exemplo demonstra como criar um **teste unitário simples**, bem estruturado e seguindo o padrão **AAA (Arrange, Act, Assert)**. Ele utiliza **JUnit 5** e **Mockito**, e está formatado de maneira ideal para que uma LLM possa compreender e reproduzir a estrutura em outros contextos.

---

## ✅ Objetivo do Teste

Validar o comportamento de um método chamado `getUserById` que retorna um usuário com base no ID informado. Ele depende de um repositório (`UserRepository`) para realizar essa busca.

---

## 🧠 Conceitos Utilizados

- **JUnit 5**: framework de testes para Java.
- **Mockito**: biblioteca para criar objetos simulados (mocks).
- **Modelo AAA**: 
  - **Arrange**: Preparar os dados/mocks.
  - **Act**: Executar o método sob teste.
  - **Assert**: Verificar o resultado.

---

## 📦 Estrutura do Teste

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // Mocks
    @Mock
    private UserRepository userRepository;

    // Classe sob teste
    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenIdExists() {
        // ARRANGE
        Long userId = 1L;
        User expectedUser = new User(userId, "Lucas");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // ACT
        User actualUser = userService.getUserById(userId);

        // ASSERT
        assertNotNull(actualUser);
        assertEquals("Lucas", actualUser.getName());
        assertEquals(userId, actualUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // ARRANGE
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
}
