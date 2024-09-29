package org.ecommerce.shared_database_api.dto;

import lombok.Data;
import org.ecommerce.shared_database_api.models.Category;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Category}
 */

@Data
public class CategoryTreeDto implements Serializable {
    String categoryName;
    int categoryId;
    List<CategoryTreeDto> subCategories;

}
