package com.example.banktransaction.service;

import com.example.banktransaction.model.Transaction;
import com.example.banktransaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/10
 */

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);

        assertNotNull(createdTransaction);
        assertEquals(transaction, createdTransaction);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testGetAllTransactions() {
        Transaction transaction1 = new Transaction("Transaction 1", new BigDecimal(100.0));
        Transaction transaction2 = new Transaction("Transaction 2", new BigDecimal(200.0));
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAll(0,10)).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions(0,10);

        assertEquals(transactions.size(), result.size());
        verify(transactionRepository, times(1)).findAll(0,10);
    }

    @Test
    public void testGetTransactionById() {
        String id = "123";
        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(id);

        assertEquals(Optional.of(transaction), result);
        verify(transactionRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        when(transactionRepository.update(transaction)).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        assertEquals(transaction, updatedTransaction);
        verify(transactionRepository, times(1)).update(transaction);
    }

    @Test
    public void testDeleteTransaction() {
        String id = "123";
        transactionService.deleteTransaction(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }
}