package org.ecommerce.shared_database_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("All tests in this class are disabled")

public class CategoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Optionally reset mock service behavior before each test
    }

    @Test
    public void testCreateCategory_WithParentCategory() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Electronics");
        categoryDto.setUrlSlug("electronics");
        categoryDto.setStatus("ACTIVE");
        categoryDto.setParentCatCategoriesId(1); // Assuming parent category ID 1

        when(categoryService.createCategory(any(Category.class))).thenReturn("Category created successfully");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategoryDto> request = new HttpEntity<>(categoryDto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.exchange("/create_category", HttpMethod.POST, request, String.class);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Category created successfully", response.getBody());
    }

    @Test
    public void testCreateCategory_WithoutParentCategory() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Books");
        categoryDto.setUrlSlug("books");
        categoryDto.setStatus("ACTIVE");
        categoryDto.setParentCatCategoriesId(0); // No parent category

        when(categoryService.createCategory(any(Category.class))).thenReturn("Category created successfully");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategoryDto> request = new HttpEntity<>(categoryDto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.exchange("/create_category", HttpMethod.POST, request, String.class);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Category created successfully", response.getBody());
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        List<CategoryDto> categories = new ArrayList<>();

        CategoryDto category1 = new CategoryDto();
        category1.setCategoryName("Electronics");
        category1.setUrlSlug("electronics");
        category1.setStatus("ACTIVE");

        CategoryDto category2 = new CategoryDto();
        category2.setCategoryName("Books");
        category2.setUrlSlug("books");
        category2.setStatus("ACTIVE");

        categories.add(category1);
        categories.add(category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act
        ResponseEntity<List> response = restTemplate.getForEntity("/get_all_category", List.class);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Electronics", ((List<CategoryDto>) response.getBody()).get(0).getCategoryName());
        assertEquals("Books", ((List<CategoryDto>) response.getBody()).get(1).getCategoryName());
    }
}
