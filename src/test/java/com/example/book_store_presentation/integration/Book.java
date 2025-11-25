package com.example.book_store_presentation.integration;

import com.example.book_store_presentation.config.SpringConfig;
import com.example.book_store_presentation.controller.BookController;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = SpringConfig.class)
public class Book {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllBooks() throws Exception {
        mockMvc.perform(get("/api/books?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.length()").value(10))
                .andExpect(jsonPath("$.data[0].isbn").value("9780134686097"))
                .andExpect(jsonPath("$.data[1].isbn").value("9780142424179"))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(25))
                .andExpect(jsonPath("$.totalPages").value(3));
    }
}
