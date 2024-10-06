package org.ecommerce.shared_database_api.services;

import org.ecommerce.shared_database_api.dto.Purchase;
import org.ecommerce.shared_database_api.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
