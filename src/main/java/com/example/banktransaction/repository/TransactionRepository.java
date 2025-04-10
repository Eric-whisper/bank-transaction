package com.example.banktransaction.repository;

import com.example.banktransaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/10
 */
@Repository
public class TransactionRepository {
    private final ConcurrentHashMap<String, Transaction> transactions = new ConcurrentHashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public List<Transaction> findAll(int page, int size) {
        return transactions.values().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        transactions.remove(id);
    }

    public Transaction update(Transaction transaction) {
        if (transactions.containsKey(transaction.getId())) {
            transactions.put(transaction.getId(), transaction);
            return transaction;
        }
        return null;
    }
}
