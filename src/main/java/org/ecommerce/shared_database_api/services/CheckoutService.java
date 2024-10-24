package org.ecommerce.shared_database_api.services;

import org.ecommerce.shared_database_api.dto.AddressDTO;
import org.ecommerce.shared_database_api.dto.Purchase;
import org.ecommerce.shared_database_api.dto.PurchaseResponse;
import org.ecommerce.shared_database_api.models.Address;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    AddressDTO getAddressByCustomerEmail(String customerEmail);
}
