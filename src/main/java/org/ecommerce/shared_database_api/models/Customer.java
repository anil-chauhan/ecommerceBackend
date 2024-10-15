package org.ecommerce.shared_database_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email",unique=true)
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<Address> addressSet = new HashSet<Address>();


    @OneToMany(mappedBy = "customer")
    private Set<Order> orderOlds = new HashSet<Order>();

    public void add(Order orderOld) {

        if (orderOld != null) {

            if (orderOlds == null) {
                orderOlds = new HashSet<Order>();
            }

            orderOlds.add(orderOld);
            orderOld.setCustomer(this);
        }
    }

}









