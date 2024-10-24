package org.ecommerce.shared_database_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ecommerce.shared_database_api.models.Customer;
import org.ecommerce.shared_database_api.models.Order;

import java.util.Objects;

@Data
public class AddressDTO {


    private Long id;
    private String houseNo;
    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;


}






