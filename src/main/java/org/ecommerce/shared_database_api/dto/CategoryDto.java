package org.ecommerce.shared_database_api.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.ecommerce.shared_database_api.models.Category}
 */
@Value
public class CategoryDto implements Serializable {
    String categoryName;
    String urlSlug;
    int parentCatCategoriesId;
    String status;
}
