package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.dto.AddressDTO;
import org.ecommerce.shared_database_api.dto.Purchase;
import org.ecommerce.shared_database_api.dto.PurchaseResponse;
import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.services.CheckoutService;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }



    @Data
    public static class AddressByCustomerEmailDTO{

        String customerEmail;
    }



    @PostMapping("/get_address_by_customer_email")
    public AddressDTO getAddressByCustomerEmail(@RequestBody AddressByCustomerEmailDTO addressByCustomerEmailDTO) {

        String customerEmail = addressByCustomerEmailDTO.getCustomerEmail();

        AddressDTO addressByCustomerEmail = checkoutService.getAddressByCustomerEmail(customerEmail);

        return addressByCustomerEmail;
    }

}









