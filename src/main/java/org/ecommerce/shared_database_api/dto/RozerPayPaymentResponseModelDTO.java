package org.ecommerce.shared_database_api.dto;


import lombok.Data;

@Data
public class RozerPayPaymentResponseModelDTO {
    public String razorpay_payment_id;
    public String razorpay_order_id;
    public String razorpay_signature;
}
