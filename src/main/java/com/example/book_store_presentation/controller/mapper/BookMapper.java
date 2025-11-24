package com.example.book_store_presentation.controller.mapper;

import es.cesguiro.daw2_bookstore.controller.webModel.request.BookInsertRequest;
import es.cesguiro.daw2_bookstore.controller.webModel.request.BookUpdateRequest;
import es.cesguiro.daw2_bookstore.controller.webModel.response.BookDetailResponse;
import es.cesguiro.daw2_bookstore.controller.webModel.response.BookSummaryResponse;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;

import java.util.Arrays;
import java.util.Collections;

public class BookMapper {

    public static BookSummaryResponse fromBookDtoToBookSummaryResponse(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        return new BookSummaryResponse(
            bookDto.isbn(),
            bookDto.titleEs(),
            bookDto.titleEn(),
            bookDto.basePrice(),
            bookDto.discountPercentage(),
            bookDto.price(),
            bookDto.cover()
        );
    }

    public static BookDetailResponse fromBookDtoToBookDetailResponse(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        return new BookDetailResponse(
            bookDto.id(),
            bookDto.isbn(),
            bookDto.titleEs(),
            bookDto.titleEn(),
            bookDto.synopsisEs(),
            bookDto.synopsisEn(),
            bookDto.basePrice(),
            bookDto.discountPercentage(),
            bookDto.price(),
            bookDto.cover(),
            bookDto.publicationDate(),
            PublisherMapper.fromPublisherDtoToPublisherSummaryResponse(bookDto.publisher()),
            bookDto.authors().stream()
                .map(AuthorMapper::fromAuthorDtoToAuthorSummaryResponse)
                .toList()
        );
    }

    public static BookDto fromBookInsertRequestToBookDto(BookInsertRequest bookInsertRequest) {
        if (bookInsertRequest == null) {
            return null;
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new BookDto(
            null,
            bookInsertRequest.isbn(),
            bookInsertRequest.titleEs(),
            bookInsertRequest.titleEn(),
            bookInsertRequest.synopsisEs(),
            bookInsertRequest.synopsisEn(),
            bookInsertRequest.basePrice(),
            bookInsertRequest.discountPercentage(),
            null,
            bookInsertRequest.cover(),
            bookInsertRequest.publicationDate(),
            mapPublisher(bookInsertRequest.publisherId()),
            bookInsertRequest.authorIds() != null ?
                Arrays.stream(bookInsertRequest.authorIds())
                    .map(BookMapper::mapAuthor)
                    .toList()
                : Collections.emptyList()
        );
    }

    public static BookDto fromBookUpdateRequestToBookDto(BookUpdateRequest bookUpdateRequest) {
        if (bookUpdateRequest == null) {
            return null;
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new BookDto(
                bookUpdateRequest.id(),
                bookUpdateRequest.isbn(),
                bookUpdateRequest.titleEs(),
                bookUpdateRequest.titleEn(),
                bookUpdateRequest.synopsisEs(),
                bookUpdateRequest.synopsisEn(),
                bookUpdateRequest.basePrice(),
                bookUpdateRequest.discountPercentage(),
                null,
                bookUpdateRequest.cover(),
                bookUpdateRequest.publicationDate(),
                mapPublisher(bookUpdateRequest.publisherId()),
                bookUpdateRequest.authorIds() != null ?
                        Arrays.stream(bookUpdateRequest.authorIds())
                                .map(BookMapper::mapAuthor)
                                .toList()
                        : Collections.emptyList()
        );
    }

    private static PublisherDto mapPublisher(Long publisherId) {
        if (publisherId == null) {
            return null;
        }
        return new PublisherDto(publisherId, null, null);
    }

    private static AuthorDto mapAuthor(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return new AuthorDto(authorId, null, null, null, null, null, null, null);
    }
}
