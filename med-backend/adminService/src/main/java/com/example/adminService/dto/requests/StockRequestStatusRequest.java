package com.example.adminService.dto.requests;

import lombok.Data;

@Data
public class StockRequestStatusRequest {
    private int requestId;
    private boolean isApproved;
    private String remark;
}
