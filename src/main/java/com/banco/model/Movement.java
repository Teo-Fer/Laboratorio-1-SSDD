package com.banco.model;

import com.banco.service.OperationType;

import javax.persistence.*;

@Entity
public class Movement {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "GenMovement"
    )
    @SequenceGenerator(
            name = "GenMovement",
            sequenceName = "movement_sq",
            allocationSize = 5
    )
    private long id;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private double amount;

    public Movement(OperationType type, double amount) {
        this.amount = amount;
        this.type = type;
    }

    public Movement() {  }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
