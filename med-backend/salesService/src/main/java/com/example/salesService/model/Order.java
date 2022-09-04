package com.example.salesService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
@Data
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    @Indexed
    private long id;
    private int retailerId;
    private float subTotal;
    private String status;
    private String paymentMethod;
    @CreatedDate
    private Date createdTime;
    private List<OrderItem> orderItems;


}
