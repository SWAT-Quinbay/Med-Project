package com.example.dealerService.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dealers",uniqueConstraints = @UniqueConstraint(columnNames = {"productId","dealerId"}))
@NoArgsConstructor
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int dealerId;
    @Column(nullable = false)
    private int stockInHand;
}
