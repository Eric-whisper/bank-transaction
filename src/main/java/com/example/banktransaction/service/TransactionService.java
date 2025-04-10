package com.example.banktransaction.service;

import com.example.banktransaction.model.Transaction;
import com.example.banktransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/10
 */

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Cacheable(value = "transactions", key = "#page + '-' + #size")
    public List<Transaction> getAllTransactions(int page, int size) {
        return transactionRepository.findAll(page, size);
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.update(transaction);
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
}
