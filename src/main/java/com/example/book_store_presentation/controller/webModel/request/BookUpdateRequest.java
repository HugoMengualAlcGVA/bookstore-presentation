package com.example.book_store_presentation.controller.webModel.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookUpdateRequest(
        Long id,
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        String cover,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate publicationDate,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        Long publisherId,
        Long[] authorIds
) {
}
