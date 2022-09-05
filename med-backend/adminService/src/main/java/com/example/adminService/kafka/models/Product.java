package com.example.adminService.kafka.models;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;

@Entity
@Table(name = "inventory")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition="TEXT")
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private float tax;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int netQuantity;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @CreationTimestamp
    private Date createdAt;
}
