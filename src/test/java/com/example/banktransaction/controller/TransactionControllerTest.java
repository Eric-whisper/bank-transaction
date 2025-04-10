package com.example.banktransaction.controller;

import com.example.banktransaction.model.Transaction;
import com.example.banktransaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/10
 */
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        when(transactionService.createTransaction(transaction)).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Transaction"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction("Transaction 1",  new BigDecimal(100.0));
        Transaction transaction2 = new Transaction("Transaction 2",  new BigDecimal(200.0));
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionService.getAllTransactions(0, 10)).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Transaction 1"))
                .andExpect(jsonPath("$[1].description").value("Transaction 2"));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        String id = transaction.getId();
        when(transactionService.getTransactionById(id)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Transaction"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    public void testUpdateTransaction() throws Exception {

        Transaction transaction = new Transaction("Test Transaction", new BigDecimal(100.0));
        String id = transaction.getId();
        when(transactionService.updateTransaction(transaction)).thenReturn(transaction);

        mockMvc.perform(put("/api/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Transaction"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        String id = "123";
        mockMvc.perform(delete("/api/transactions/{id}", id))
                .andExpect(status().isNoContent());
        verify(transactionService, times(1)).deleteTransaction(id);
    }
}