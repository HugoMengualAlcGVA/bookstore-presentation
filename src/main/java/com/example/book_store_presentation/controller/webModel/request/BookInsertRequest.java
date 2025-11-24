package com.example.book_store_presentation.controller.webModel.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookInsertRequest(
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        String cover,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        Long publisherId,
        Long[] authorIds
) {
}
