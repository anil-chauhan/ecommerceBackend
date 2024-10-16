package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.dto.OrderItemsResponseDto;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query(value = "select  ordIt from OrderItem  as  ordIt where ordIt.order.id=:orderId")
    ArrayList<OrderItem> getOrderItemsByOrderID(@Param("orderId") Long orderId);


    @Query("SELECT  new org.ecommerce.shared_database_api.dto.OrderItemsResponseDto(p.productId,p.productName, p.productImageUrl,p.price,oi.quantity,p.brand) " +
            "FROM OrderItem oi inner JOIN Product p on oi.productId=p.productId" +
            " WHERE oi.order.id=:orderId")
    ArrayList<OrderItemsResponseDto> findOrderItemsByOrderId(@Param("orderId") Long orderId);

}
