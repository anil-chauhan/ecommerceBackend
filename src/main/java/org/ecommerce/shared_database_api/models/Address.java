package org.ecommerce.shared_database_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Entity
@Table(name="address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="house_no")
    private String houseNo;

    @Column(name="street")
    private String street;

    @Column(name="state")
    private String state;


    @Column(name="city")
    private String city;



    @Column(name="country")
    private String country;

    @Column(name="zip_code")
    private String zipCode;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Column(name="address_hash",unique=true)
    private String addressHash;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(houseNo, address.houseNo) && Objects.equals(street, address.street) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && Objects.equals(country, address.country) && Objects.equals(zipCode, address.zipCode) && Objects.equals(order, address.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNo, street, state, city, country, zipCode, order, customer);
    }

    public String getHashCode() {

        String input=houseNo+ street+ state+ city+ country+ zipCode;

        return Hashing.sha256()
                .hashString(input, Charsets.UTF_8)
                .toString();
    }

}






