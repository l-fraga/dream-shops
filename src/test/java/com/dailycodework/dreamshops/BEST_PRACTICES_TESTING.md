# 🧪 Melhores Práticas de Testes - DreamShops

Este documento contém as melhores práticas para implementação de testes unitários no projeto DreamShops.

## 📋 Princípios Fundamentais

### 1. **Padrão AAA (Arrange, Act, Assert)**
```java
@Test
void shouldReturnUserWhenIdExists() {
    // ARRANGE - Preparar dados e mocks
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

    // ACT - Executar o método sob teste
    User actualUser = userService.getUserById(userId);

    // ASSERT - Verificar o resultado
    assertNotNull(actualUser);
    assertEquals(testUser.getId(), actualUser.getId());
    verify(userRepository, times(1)).findById(userId);
}
```

### 2. **Nomenclatura Descritiva**
- **Métodos de teste**: `should[Comportamento]When[Condição]`
- **Classes de teste**: `[ClasseSobTeste]Test`
- **Variáveis**: Nomes descritivos que explicam o propósito

### 3. **Isolamento de Testes**
- Cada teste deve ser independente
- Usar `@BeforeEach` para setup comum
- Evitar dependências entre testes

## 🏗️ Estrutura de Testes

### Organização de Pacotes
```
src/test/java/com/dailycodework/dreamshops/
├── service/
│   ├── user/
│   │   └── UserServiceTest.java
│   ├── product/
│   │   └── ProductServiceTest.java
│   └── order/
│       └── OrderServiceTest.java
├── controller/
│   ├── UserControllerTest.java
│   └── ProductControllerTest.java
└── repository/
    └── UserRepositoryTest.java
```

### Anotações Importantes
```java
@ExtendWith(MockitoExtension.class)  // Habilita Mockito
@DisplayName("UserService - Testes Unitários")  // Nome descritivo
class UserServiceTest {
    
    @Mock  // Cria mock da dependência
    private UserRepository userRepository;
    
    @InjectMocks  // Injeta mocks na classe sob teste
    private UserService userService;
    
    @BeforeEach  // Executa antes de cada teste
    void setUp() {
        // Setup comum
    }
}
```

## 🎯 Tipos de Testes

### 1. **Testes de Sucesso (Happy Path)**
```java
@Test
@DisplayName("Deve criar usuário com sucesso quando email não existe")
void shouldCreateUserSuccessfullyWhenEmailDoesNotExist() {
    // ARRANGE
    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(passwordEncoder.encode(anyString())).thenReturn("encoded");
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // ACT
    User result = userService.createUser(createUserRequest);

    // ASSERT
    assertNotNull(result);
    assertEquals(testUser.getId(), result.getId());
    verify(userRepository, times(1)).save(any(User.class));
}
```

### 2. **Testes de Exceção**
```java
@Test
@DisplayName("Deve lançar exceção quando usuário não encontrado")
void shouldThrowExceptionWhenUserNotFound() {
    // ARRANGE
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        userService.getUserById(99L);
    });

    assertEquals("User not found", exception.getMessage());
}
```

### 3. **Testes de Validação**
```java
@Test
@DisplayName("Deve validar campos obrigatórios")
void shouldValidateRequiredFields() {
    // ARRANGE
    CreateUserRequest invalidRequest = new CreateUserRequest();
    // Sem preencher campos obrigatórios

    // ACT & ASSERT
    assertThrows(ValidationException.class, () -> {
        userService.createUser(invalidRequest);
    });
}
```

## 🔧 Configuração de Mocks

### Mock Básico
```java
@Mock
private UserRepository userRepository;

// Configurar comportamento
when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
when(userRepository.findById(99L)).thenReturn(Optional.empty());
```

### Mock com Argument Matchers
```java
// Aceita qualquer Long
when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

// Aceita qualquer String
when(userRepository.findByEmail(anyString())).thenReturn(testUser);

// Aceita qualquer objeto da classe
when(userRepository.save(any(User.class))).thenReturn(testUser);
```

### Verificação de Interações
```java
// Verificar se método foi chamado
verify(userRepository, times(1)).findById(1L);

// Verificar se método NÃO foi chamado
verify(userRepository, never()).save(any(User.class));

// Verificar se método foi chamado com argumento específico
verify(passwordEncoder, times(1)).encode("password123");
```

## 📊 Cobertura de Testes

### Métricas Importantes
- **Cobertura de Linhas**: > 80%
- **Cobertura de Branches**: > 70%
- **Cobertura de Métodos**: > 90%

### Cenários de Teste Essenciais
1. **Cenário de Sucesso**: Método funciona corretamente
2. **Cenário de Falha**: Método lida com erros
3. **Cenário de Validação**: Dados inválidos são rejeitados
4. **Cenário de Borda**: Valores limites e casos extremos

## 🚀 Execução de Testes

### Comandos Maven
```bash
# Executar todos os testes
mvn test

# Executar testes com cobertura
mvn clean test jacoco:report

# Executar testes específicos
mvn test -Dtest=UserServiceTest

# Executar teste específico
mvn test -Dtest=UserServiceTest#shouldCreateUserSuccessfullyWhenEmailDoesNotExist
```

### Configuração do JaCoCo
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <id>prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## 🎨 Padrões de Nomenclatura

### Classes de Teste
- `UserServiceTest` - Testes para UserService
- `ProductControllerTest` - Testes para ProductController
- `UserRepositoryTest` - Testes para UserRepository

### Métodos de Teste
- `shouldReturnUserWhenIdExists()` - Cenário de sucesso
- `shouldThrowExceptionWhenUserNotFound()` - Cenário de erro
- `shouldValidateRequiredFields()` - Cenário de validação
- `shouldHandleNullInput()` - Cenário de borda

### Variáveis de Teste
- `testUser` - Usuário de teste
- `createUserRequest` - Request para criar usuário
- `expectedUser` - Resultado esperado
- `actualUser` - Resultado real

## 🔍 Assertions Recomendadas

### Assertions Básicas
```java
// Verificar se não é null
assertNotNull(result);

// Verificar igualdade
assertEquals(expected, actual);

// Verificar se é true/false
assertTrue(condition);
assertFalse(condition);

// Verificar se é null
assertNull(result);
```

### Assertions de Exceção
```java
// Verificar se exceção é lançada
assertThrows(RuntimeException.class, () -> {
    userService.getUserById(99L);
});

// Verificar mensagem da exceção
RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    userService.getUserById(99L);
});
assertEquals("User not found", exception.getMessage());
```

### Assertions de Collections
```java
// Verificar tamanho da lista
assertEquals(3, userList.size());

// Verificar se lista contém elemento
assertTrue(userList.contains(expectedUser));

// Verificar se lista está vazia
assertTrue(userList.isEmpty());
```

## 📝 Checklist de Qualidade

### ✅ Antes de Commitar
- [ ] Todos os testes passam
- [ ] Cobertura de código > 80%
- [ ] Nomes descritivos para métodos de teste
- [ ] Documentação clara com @DisplayName
- [ ] Testes isolados e independentes
- [ ] Mocks configurados corretamente
- [ ] Verificações de interação implementadas

### ✅ Boas Práticas
- [ ] Usar @BeforeEach para setup comum
- [ ] Evitar lógica complexa nos testes
- [ ] Um assert por teste (quando possível)
- [ ] Testes rápidos (< 1 segundo cada)
- [ ] Testes determinísticos (mesmo resultado sempre)
- [ ] Mocks apenas das dependências necessárias

## 🎯 Próximos Passos

1. **Implementar testes para outros serviços**:
   - ProductService
   - OrderService
   - CartService

2. **Implementar testes de integração**:
   - Controller tests
   - Repository tests

3. **Configurar CI/CD**:
   - Execução automática de testes
   - Relatórios de cobertura
   - Qualidade de código

4. **Implementar testes de performance**:
   - Testes de carga
   - Testes de stress

---

**Lembre-se**: Testes são investimento, não custo. Eles garantem qualidade, facilitam manutenção e aumentam a confiança no código. 