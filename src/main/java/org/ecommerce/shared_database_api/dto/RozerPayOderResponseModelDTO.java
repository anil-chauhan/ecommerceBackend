package org.ecommerce.shared_database_api.dto;

import lombok.Data;

@Data
public class RozerPayOderResponseModelDTO {

    public String id;
    public String entity;
    public int amount;
    public int amount_paid;
    public int amount_due;
    public String currency;
    public String receipt;
    public Object offer_id;
    public String status;
    public int attempts;
    public String notes;
    public int created_at;


    public String secretKey;
    public String razorpayOrderId;
    public String applicationFee;
    public String secretId;
    public String pgName;

}
