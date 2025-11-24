package com.example.book_store_presentation.controller.mapper;

import com.example.book_store_presentation.controller.webModel.response.AuthorDetailResponse;
import com.example.book_store_presentation.controller.webModel.response.AuthorSummaryResponse;
import es.cesguiro.domain.service.dto.AuthorDto;

public class AuthorMapper {

    public static AuthorSummaryResponse fromAuthorDtoToAuthorSummaryResponse(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new AuthorSummaryResponse(
            authorDto.name(),
            authorDto.slug()
        );
    }

    public static AuthorDetailResponse fromAuthorDtoToAuthorDetailResponse(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new AuthorDetailResponse(
            authorDto.name(),
            authorDto.nationality(),
            authorDto.biographyEs(),
            authorDto.biographyEn(),
            authorDto.birthYear(),
            authorDto.deathYear(),
            authorDto.slug()
        );
    }
}
