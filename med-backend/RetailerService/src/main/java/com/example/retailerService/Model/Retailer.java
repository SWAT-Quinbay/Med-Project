package com.example.retailerService.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="retailer",uniqueConstraints = @UniqueConstraint(columnNames = {"retailerId","productId"}))
public class Retailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int retailerId;
    @Column(nullable = false)
    private int stockInHand;
}


