package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ProductNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CategoryRepository;
import com.dailycodework.dreamshops.repository.ImageRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService - Testes Unitários")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Category testCategory;
    private AddProductRequest addProductRequest;

    @BeforeEach
    void setUp() {
        // ARRANGE - Preparar dados de teste
        testCategory = new Category("Electronics");
        testCategory.setId(1L);

        testProduct = new Product(
            "iPhone 15",
            "Apple",
            new BigDecimal("999.99"),
            50,
            "Latest iPhone model",
            testCategory
        );
        testProduct.setId(1L);

        addProductRequest = new AddProductRequest();
        addProductRequest.setName("iPhone 15");
        addProductRequest.setBrand("Apple");
        addProductRequest.setPrice(new BigDecimal("999.99"));
        addProductRequest.setInventory(50);
        addProductRequest.setDescription("Latest iPhone model");
        addProductRequest.setCategory(testCategory);
    }

    @Test
    @DisplayName("Deve retornar produto quando ID existe")
    void shouldReturnProductWhenIdExists() {
        // ARRANGE
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        // ACT
        Product actualProduct = productService.getProductById(productId);

        // ASSERT
        assertThat(actualProduct).isNotNull();
        assertThat(actualProduct.getId()).isEqualTo(testProduct.getId());
        assertThat(actualProduct.getName()).isEqualTo(testProduct.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não encontrado")
    void shouldThrowExceptionWhenProductNotFound() {
        // ARRANGE
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.getProductById(productId))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage("Product not found!");
        
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Deve criar produto com sucesso quando dados válidos")
    void shouldCreateProductSuccessfullyWhenValidRequest() {
        // ARRANGE
        when(productRepository.existsByNameAndBrand(anyString(), anyString())).thenReturn(false);
        when(categoryRepository.findByName(anyString())).thenReturn(testCategory);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // ACT
        Product result = productService.addProduct(addProductRequest);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProduct.getId());
        assertThat(result.getName()).isEqualTo(testProduct.getName());
        verify(productRepository, times(1)).existsByNameAndBrand(anyString(), anyString());
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto já existe")
    void shouldThrowExceptionWhenProductAlreadyExists() {
        // ARRANGE
        when(productRepository.existsByNameAndBrand(anyString(), anyString())).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.addProduct(addProductRequest))
            .isInstanceOf(AlreadyExistsException.class)
            .hasMessageContaining("already exists");

        verify(productRepository, times(1)).existsByNameAndBrand(anyString(), anyString());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve criar nova categoria quando categoria não existe")
    void shouldCreateNewCategoryWhenCategoryDoesNotExist() {
        // ARRANGE
        Category newCategory = new Category("New Category");
        when(productRepository.existsByNameAndBrand(anyString(), anyString())).thenReturn(false);
        when(categoryRepository.findByName(anyString())).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // ACT
        Product result = productService.addProduct(addProductRequest);

        // ASSERT
        assertThat(result).isNotNull();
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve retornar todos os produtos")
    void shouldReturnAllProducts() {
        // ARRANGE
        List<Product> expectedProducts = Arrays.asList(testProduct);
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // ACT
        List<Product> actualProducts = productService.getAllProducts();

        // ASSERT
        assertThat(actualProducts).isNotNull();
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0)).isEqualTo(testProduct);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void shouldUpdateProductSuccessfully() {
        // ARRANGE
        Long productId = 1L;
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setName("iPhone 15 Pro");
        updateRequest.setPrice(new BigDecimal("1199.99"));
        updateRequest.setBrand("Apple");
        updateRequest.setInventory(50);
        updateRequest.setDescription("Updated description");
        
        // Criar categoria mock para evitar NullPointerException
        Category category = new Category();
        category.setName("Electronics");
        updateRequest.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findByName("Electronics")).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // ACT
        Product result = productService.updateProduct(updateRequest, productId);

        // ASSERT
        assertThat(result).isNotNull();
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findByName("Electronics");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        // ARRANGE
        Long productId = 99L;
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.updateProduct(updateRequest, productId))
            .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve deletar produto com sucesso")
    void shouldDeleteProductSuccessfully() {
        // ARRANGE
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        doNothing().when(productRepository).delete(any(Product.class));

        // ACT
        productService.deleteProductById(productId);

        // ASSERT
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class)); // Para setar categoria null
        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        // ARRANGE
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.deleteProductById(productId))
            .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve converter produto para DTO corretamente")
    void shouldConvertProductToDtoCorrectly() {
        // ARRANGE
        ProductDto expectedDto = new ProductDto();
        expectedDto.setId(testProduct.getId());
        expectedDto.setName(testProduct.getName());
        expectedDto.setPrice(testProduct.getPrice());
        
        when(modelMapper.map(testProduct, ProductDto.class)).thenReturn(expectedDto);
        when(imageRepository.findByProductId(testProduct.getId())).thenReturn(List.of());

        // ACT
        ProductDto result = productService.convertToDto(testProduct);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testProduct.getId());
        assertThat(result.getName()).isEqualTo(testProduct.getName());
        verify(modelMapper, times(1)).map(testProduct, ProductDto.class);
        verify(imageRepository, times(1)).findByProductId(testProduct.getId());
    }

    @Test
    @DisplayName("Deve converter lista de produtos para DTOs")
    void shouldConvertProductListToDtos() {
        // ARRANGE
        List<Product> products = Arrays.asList(testProduct);
        ProductDto expectedDto = new ProductDto();
        expectedDto.setId(testProduct.getId());
        expectedDto.setName(testProduct.getName());
        
        when(modelMapper.map(testProduct, ProductDto.class)).thenReturn(expectedDto);
        when(imageRepository.findByProductId(testProduct.getId())).thenReturn(List.of());

        // ACT
        List<ProductDto> result = productService.getConvertedProducts(products);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(testProduct.getId());
        verify(modelMapper, times(1)).map(testProduct, ProductDto.class);
        verify(imageRepository, times(1)).findByProductId(testProduct.getId());
    }
} 