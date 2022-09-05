package com.example.adminService.kafka.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username","role"})})
public class AppUser {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String username;

    @JsonIgnore
    private String password;
    private String role;

    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;

    @JsonIgnore
    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    private List<StockRequestEntity> senders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL)
    private List<StockRequestEntity> receivers = new ArrayList<>();
}
