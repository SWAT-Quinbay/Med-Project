package com.example.adminService.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestResponse {
    private int id;
    private Date createdAt;
    private String status;
    private String remark;
    private ProductDetailsResponse product;
    private int requestedQuantity;

    private int senderId;
    private String senderName;
    private String senderRole;

    private int receiverId;
    private String receiverName;
    private String receiverRole;
}
