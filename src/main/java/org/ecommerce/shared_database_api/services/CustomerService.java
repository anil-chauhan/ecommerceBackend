package org.ecommerce.shared_database_api.services;


import org.ecommerce.shared_database_api.dto.RozerPayPaymentRequestModelDTO;
import org.ecommerce.shared_database_api.models.Customer;
import org.ecommerce.shared_database_api.repo.CustomerRepository;
import org.ecommerce.shared_database_api.repo.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerByEmailId(String email) {
        try {
            Customer customerByEmailId = customerRepository.findCustomerByEmailId(email);
            return customerByEmailId;
        } catch (Exception e) {
            return null;
        }



    }

    public Customer saveCustomer(Customer customer) {
        Customer save = customerRepository.save(customer);
        return save;
    }

}
