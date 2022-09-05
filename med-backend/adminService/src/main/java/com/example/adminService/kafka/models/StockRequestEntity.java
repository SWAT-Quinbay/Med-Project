package com.example.adminService.kafka.models;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class StockRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int requestedQuantity;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "senderId",referencedColumnName = "id",nullable = false)
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "receiverId",referencedColumnName = "id",nullable = false)
    private AppUser receiver;
}
