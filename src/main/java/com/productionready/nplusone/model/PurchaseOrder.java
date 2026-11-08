package com.productionready.nplusone.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 50)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderLine> lines = new ArrayList<>();

    public PurchaseOrder() {
    }

    public PurchaseOrder(String customerName, Instant createdAt) {
        this.customerName = customerName;
        this.createdAt = createdAt;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
        line.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public String getCustomerName() {
        return customerName;
    }
}
