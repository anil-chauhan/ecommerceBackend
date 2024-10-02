package org.ecommerce.shared_database_api.dto;

import lombok.Data;
import lombok.Value;
import org.ecommerce.shared_database_api.models.Category;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.ecommerce.shared_database_api.models.Category}
 */

@Data
public class CategoryDto implements Serializable {
    String categoryName;
    String urlSlug;
    int parentCatCategoriesId;
    String parentCatCategoriesName;
    String status;
    List<CategoryDto> subCategories;
    private boolean isExpanded; // New property for expand/collapse state
}
