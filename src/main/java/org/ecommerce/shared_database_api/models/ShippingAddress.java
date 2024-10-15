/*
package org.ecommerce.shared_database_api.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "shipping_addresses")
@Data
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_addresses_id", nullable = false)
    private Integer shippingAddressesId;


    @Column(name="house_no")
    private String houseNo;


    @Column(name="street")
    private String street;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "city", nullable = false, length = 100)
    private String city;


    @Column(name="country")
    private String country;



    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;


    */
/*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*//*



    */
/*@Column(name = "full_address", nullable = false, length = Integer.MAX_VALUE)
    private String fullAddress;*//*



}
*/
