package com.example.book_store_presentation.controller.webModel.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookDetailResponse(
        Long id,
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        BigDecimal price,
        String cover,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        PublisherSummaryResponse publisher,
        List<AuthorSummaryResponse> authors
)
{ }
