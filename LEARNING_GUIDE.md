# Complete Learning Guide: Spring Boot Testing, JUnit, and Mockito

## 🎯 Learning Path Overview

This guide will take you from **zero knowledge** to **testing expert** in Spring Boot applications. Follow this structured path to master testing concepts.

## 📚 Table of Contents

1. [Understanding Testing Fundamentals](#understanding-testing-fundamentals)
2. [JUnit 5 Basics](#junit-5-basics)
3. [Mockito Fundamentals](#mockito-fundamentals)
4. [Spring Boot Testing](#spring-boot-testing)
5. [Testing Patterns and Best Practices](#testing-patterns-and-best-practices)
6. [Practical Exercises](#practical-exercises)
7. [Advanced Concepts](#advanced-concepts)

---

## 🎓 Understanding Testing Fundamentals

### What is Testing?

Testing is like having a **robot assistant** that automatically checks if your code works correctly. Instead of manually testing your application, you write code that tests your code!

### Why Testing is Important?

- **Catch Bugs Early**: Find problems before they reach production
- **Confidence**: Know your code works as expected
- **Refactoring Safety**: Change code without breaking functionality
- **Documentation**: Tests show how your code should be used
- **Team Collaboration**: Other developers understand your code better

### Types of Testing

1. **Unit Testing**: Test individual methods/functions in isolation
2. **Integration Testing**: Test how different parts work together
3. **End-to-End Testing**: Test the complete application flow

---

## 🧪 JUnit 5 Basics

### What is JUnit?

JUnit is the **standard testing framework** for Java. It provides annotations and assertions to write tests.

### Key JUnit 5 Annotations

```java
@Test                    // Marks a method as a test
@BeforeEach             // Runs before each test method
@AfterEach              // Runs after each test method
@BeforeAll              // Runs once before all tests
@AfterAll               // Runs once after all tests
@DisplayName            // Provides a readable test name
@Disabled               // Skips a test
```

### Basic Assertions

```java
// Equality
assertEquals(expected, actual, "message");
assertNotEquals(expected, actual, "message");

// Boolean
assertTrue(condition, "message");
assertFalse(condition, "message");

// Null
assertNull(object, "message");
assertNotNull(object, "message");

// Exceptions
assertThrows(ExceptionClass.class, () -> {
    // code that should throw exception
});

// Collections
assertArrayEquals(expectedArray, actualArray, "message");
assertIterableEquals(expectedList, actualList, "message");
```

### Example: Basic JUnit Test

```java
@Test
@DisplayName("Should add two numbers correctly")
void shouldAddTwoNumbers() {
    // Arrange
    int a = 5;
    int b = 3;
    
    // Act
    int result = a + b;
    
    // Assert
    assertEquals(8, result, "5 + 3 should equal 8");
}
```

---

## 🎭 Mockito Fundamentals

### What is Mockito?

Mockito is a **mocking framework** that creates fake objects (mocks) to test your code without real dependencies.

### Why Use Mocks?

- **Isolation**: Test your code without external dependencies
- **Speed**: Tests run faster without real databases/APIs
- **Control**: You control what the mock returns
- **Predictability**: Tests are consistent and repeatable

### Key Mockito Concepts

#### 1. Creating Mocks

```java
// Manual mock creation
ProductRepository mockRepository = mock(ProductRepository.class);

// Using annotations
@Mock
private ProductRepository productRepository;

@InjectMocks
private ProductService productService;
```

#### 2. Setting Up Mocks

```java
// Basic setup
when(repository.findById(1L)).thenReturn(Optional.of(product));

// With argument matchers
when(repository.findById(anyLong())).thenReturn(Optional.of(product));
when(repository.save(any(Product.class))).thenReturn(product);

// Exception throwing
when(repository.findById(999L)).thenThrow(new RuntimeException("Not found"));
```

#### 3. Verifying Mocks

```java
// Basic verification
verify(repository).findById(1L);

// Verification with times
verify(repository, times(1)).findById(1L);
verify(repository, never()).deleteById(anyLong());

// Verification with argument matchers
verify(repository).save(any(Product.class));
```

### Example: Complete Mockito Test

```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductService productService;
    
    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("iPhone 13");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        // Act
        Product result = productService.getProductById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("iPhone 13", result.getName());
        
        // Verify
        verify(productRepository).findById(1L);
    }
}
```

---

## 🌱 Spring Boot Testing

### Spring Boot Test Annotations

```java
@SpringBootTest           // Loads the full application context
@WebMvcTest               // Loads only the web layer
@DataJpaTest              // Loads only the data layer
@AutoConfigureTestDatabase // Configure test database
@Transactional            // Makes test methods transactional
@ActiveProfiles("test")   // Use test profile
```

### Testing Different Layers

#### 1. Controller Testing (Web Layer)

```java
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private IProductService productService;
    
    @Test
    void getAllProducts_ShouldReturnProducts() throws Exception {
        // Arrange
        List<Product> products = Arrays.asList(createTestProduct());
        when(productService.getAllProducts()).thenReturn(products);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }
}
```

#### 2. Repository Testing (Data Layer)

```java
@DataJpaTest
class ProductRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void save_ShouldPersistNewProduct() {
        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        
        // Act
        Product saved = productRepository.save(product);
        
        // Assert
        assertNotNull(saved.getId());
        Optional<Product> found = productRepository.findById(saved.getId());
        assertTrue(found.isPresent());
    }
}
```

#### 3. Integration Testing

```java
@SpringBootTest
@AutoConfigureWebMvc
@Transactional
class ProductIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void addProduct_ShouldCreateAndReturnProduct() throws Exception {
        // Arrange
        AddProductRequest request = createTestRequest();
        int initialCount = productRepository.count();
        
        // Act
        mockMvc.perform(post("/api/v1/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        
        // Assert
        assertEquals(initialCount + 1, productRepository.count());
    }
}
```

---

## 📋 Testing Patterns and Best Practices

### The AAA Pattern

Every test should follow the **Arrange, Act, Assert** pattern:

```java
@Test
void methodName_WhenCondition_ShouldExpectedResult() {
    // ARRANGE - Set up test data and mocks
    when(repository.findById(1L)).thenReturn(Optional.of(product));
    
    // ACT - Execute the method under test
    Product result = service.getProductById(1L);
    
    // ASSERT - Verify the results
    assertNotNull(result);
    assertEquals("iPhone 13", result.getName());
    verify(repository).findById(1L);
}
```

### Naming Conventions

```java
// Test class names
ProductServiceTest
ProductControllerTest
ProductRepositoryTest

// Test method names
getProductById_WhenProductExists_ShouldReturnProduct()
addProduct_WhenInvalidData_ShouldThrowException()
updateProduct_WhenProductNotFound_ShouldReturnNotFound()
```

### Test Data Management

#### 1. Builder Pattern

```java
public class ProductTestBuilder {
    private Product product = new Product();
    
    public ProductTestBuilder withName(String name) {
        product.setName(name);
        return this;
    }
    
    public ProductTestBuilder withPrice(BigDecimal price) {
        product.setPrice(price);
        return this;
    }
    
    public Product build() {
        return product;
    }
}

// Usage
Product product = new ProductTestBuilder()
    .withName("iPhone 13")
    .withPrice(new BigDecimal("999.99"))
    .build();
```

#### 2. Factory Pattern

```java
public class TestDataFactory {
    public static Product createTestProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new BigDecimal("99.99"));
        return product;
    }
}
```

### Common Testing Patterns

#### 1. Testing Exceptions

```java
@Test
void methodName_WhenExceptionOccurs_ShouldThrowException() {
    // Arrange
    when(repository.findById(999L)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(ResourceNotFoundException.class, () -> {
        service.getProductById(999L);
    });
}
```

#### 2. Testing Collections

```java
@Test
void getAllProducts_ShouldReturnAllProducts() {
    // Arrange
    List<Product> expectedProducts = Arrays.asList(product1, product2);
    when(repository.findAll()).thenReturn(expectedProducts);
    
    // Act
    List<Product> result = service.getAllProducts();
    
    // Assert
    assertEquals(2, result.size());
    assertTrue(result.containsAll(expectedProducts));
}
```

---

## 🏋️ Practical Exercises

### Exercise 1: Basic Assertions
```java
@Test
void exercise1_BasicAssertions() {
    Product product = new Product();
    product.setName("iPhone 13");
    product.setPrice(new BigDecimal("999.99"));
    
    // TODO: Write assertions to check:
    // 1. Product is not null
    // 2. Product name is "iPhone 13"
    // 3. Product price is 999.99
}
```

### Exercise 2: Basic Mockito
```java
@Test
void exercise2_BasicMockito() {
    // TODO: 
    // 1. Set up mock for productRepository.findById(1L)
    // 2. Call productService.getProductById(1L)
    // 3. Assert the result
    // 4. Verify the mock was called
}
```

### Exercise 3: Testing Exceptions
```java
@Test
void exercise3_TestingExceptions() {
    // TODO:
    // 1. Set up mock to return empty for ID 999L
    // 2. Test that exception is thrown
    // 3. Verify mock was called
}
```

### Exercise 4: Complete CRUD Test
```java
@Test
void exercise4_CompleteCRUDTest() {
    // TODO: Write a comprehensive test that covers:
    // 1. Product creation
    // 2. Product retrieval
    // 3. Product update
    // 4. Product deletion
    // 5. Error scenarios
}
```

---

## 🚀 Advanced Concepts

### Test Configuration

```java
@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

### Test Properties

```properties
# application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
```

### Test Coverage

```bash
# Run tests with coverage
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

### Performance Testing

```java
@Test
void performanceTest() {
    assertTimeout(Duration.ofSeconds(1), () -> {
        // Code that should complete within 1 second
        service.expensiveOperation();
    });
}
```

---

## 📖 Learning Resources

### Official Documentation
- [Spring Boot Testing Guide](https://spring.io/guides/gs/testing-web/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

### Books
- "Effective Unit Testing" by Lasse Koskela
- "Test Driven Development" by Kent Beck
- "Growing Object-Oriented Software, Guided by Tests" by Steve Freeman

### Online Courses
- Spring Boot Testing on Udemy
- JUnit 5 Testing on Pluralsight
- Mockito Tutorial on YouTube

---

## 🎯 Next Steps

1. **Complete the exercises** in the `PracticalExercises.java` file
2. **Practice with your own code** - write tests for existing methods
3. **Learn integration testing** - test your REST APIs
4. **Set up test coverage** - aim for 80%+ coverage
5. **Learn advanced Mockito** - argument captors, spy objects
6. **Practice test-driven development** - write tests before code

---

## 💡 Tips for Success

1. **Start Small**: Begin with simple unit tests
2. **Practice Daily**: Write tests for every new feature
3. **Read Test Code**: Study well-written tests in open-source projects
4. **Use Test Coverage**: But don't obsess over 100% coverage
5. **Keep Tests Simple**: One test, one assertion
6. **Test Behavior, Not Implementation**: Focus on what, not how
7. **Refactor Tests**: Keep test code clean and maintainable

---

## 🎉 Congratulations!

You now have a comprehensive understanding of Spring Boot testing, JUnit 5, and Mockito. Remember:

- **Testing is a skill** that improves with practice
- **Good tests** make your code more reliable and maintainable
- **Testing saves time** in the long run by catching bugs early
- **Testing is fun** once you get the hang of it!

Happy testing! 🧪✨ 