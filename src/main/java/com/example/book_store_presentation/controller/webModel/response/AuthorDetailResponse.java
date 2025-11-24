package com.example.book_store_presentation.controller.webModel.response;

public record AuthorDetailResponse(
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        Integer birthYear,
        Integer deathYear,
        String slug
) {
}
