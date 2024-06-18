package com.example.receiptprocessor.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "retailer")
    private String retailer;
    @Column(name = "points")
    private long points;
}
