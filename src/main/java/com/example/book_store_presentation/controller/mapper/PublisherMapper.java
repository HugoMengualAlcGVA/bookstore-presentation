package com.example.book_store_presentation.controller.mapper;

import es.cesguiro.daw2_bookstore.controller.webModel.response.PublisherDetailResponse;
import es.cesguiro.daw2_bookstore.controller.webModel.response.PublisherSummaryResponse;
import es.cesguiro.domain.service.dto.PublisherDto;

public class PublisherMapper {

    public static PublisherSummaryResponse fromPublisherDtoToPublisherSummaryResponse(PublisherDto publisherDto) {
        if (publisherDto == null) {
            return null;
        }
        return new PublisherSummaryResponse(
            publisherDto.name(),
            publisherDto.slug()
        );
    }

    public static PublisherDetailResponse fromPublisherDtoToPublisherDetailResponse(PublisherDto publisherDto) {
        if (publisherDto == null) {
            return null;
        }
        return new PublisherDetailResponse(
            publisherDto.name(),
            publisherDto.slug()
        );
    }
}
