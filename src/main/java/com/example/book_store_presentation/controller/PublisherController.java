package com.example.book_store_presentation.controller;

import es.cesguiro.domain.service.PublisherService;
import es.cesguiro.domain.service.dto.PublisherDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{slug}")
    public PublisherDto findBySlug(@PathVariable String slug) {
        try {
            return publisherService.getBySlug(slug);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
