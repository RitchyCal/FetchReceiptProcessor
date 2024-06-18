package com.example.receiptprocessor.model.response;

import com.example.receiptprocessor.model.Product;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class ReceiptRequest {
    private String retailer;
    private Date purchaseDate;
    private LocalTime purchaseTime;
    private List<Product> items;
    private Double total;
}
