package org.ecommerce.shared_database_api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Data
@Getter
@Setter
public class ProductRequestParamDto implements Serializable {
    String productName;
    int pageSize;
    int pageNumber;
}
