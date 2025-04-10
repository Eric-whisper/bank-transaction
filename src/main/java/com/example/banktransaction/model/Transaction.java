package com.example.banktransaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/10
 */
public class Transaction {
    @NotBlank
    private String id;

    @NotNull
    private String description;

    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

    public Transaction(String description, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount.doubleValue(), amount.doubleValue()) == 0 && Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount);
    }
}
