package org.ecommerce.shared_database_api.dto;


import lombok.Data;

@Data
public class RozerPayPaymentRequestModelDTO {
    public String razorpayPaymentId;
    public String razorpayOrderId;
    public String razorpaySignature;
}
