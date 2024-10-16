package org.ecommerce.shared_database_api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryDetailsDto implements Serializable {

    String categoryName;
    int categoryId;
    String productRandomImageUrl;
    int productCountInCategory;
}
