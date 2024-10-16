package org.ecommerce.shared_database_api.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.ecommerce.shared_database_api.dto.OrderItemsResponseDto;
import org.ecommerce.shared_database_api.dto.RozerPayPaymentRequestModelDTO;
import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.OrderItem;
import org.ecommerce.shared_database_api.repo.AddressRepository;
import org.ecommerce.shared_database_api.repo.OrderItemRepository;
import org.ecommerce.shared_database_api.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;

    }


    public ArrayList<OrderItemsResponseDto> getOrderItemsByOrderId(Long orderId) {

        ArrayList<OrderItemsResponseDto> ordersByCustomerEmail = orderItemRepository.findOrderItemsByOrderId(orderId);


        for (OrderItemsResponseDto orderItem : ordersByCustomerEmail) {
            String productImageUrl = orderItem.getProductImageUrl();
            String s = convertImageToBase64(productImageUrl);
            orderItem.setProductImageUrl(s);
        }

        return ordersByCustomerEmail;
    }


    public String convertImageToBase64(String imagePath) {
        try {



            // Read the image file into a byte array
            File imageFile = new File(imagePath);
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

            // Encode the byte array to a Base64 string
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Return the Base64 string prefixed with the appropriate data type
            return "data:image/png;base64," + base64Image; // Adjust the MIME type if necessary
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error appropriately
        }
    }




}
