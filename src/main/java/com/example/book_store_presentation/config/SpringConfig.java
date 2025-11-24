package com.example.book_store_presentation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.service.AuthorService;
import es.cesguiro.domain.service.PublisherService;
import es.cesguiro.domain.service.impl.AuthorServiceImpl;
import es.cesguiro.domain.service.impl.BookServiceImpl;
import es.cesguiro.domain.service.BookService;
import es.cesguiro.domain.service.impl.PublisherServiceImpl;
import es.cesguiro.persistence.PersistenceConfig;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.repository.AuthorRepositoryImpl;
import es.cesguiro.persistence.repository.BookRepositoryImpl;
import es.cesguiro.persistence.repository.PublisherRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
public class SpringConfig {

    /************* BOOK *************/

    /*@Bean
    public BookRepository bookRepository(BookJpaDao bookJpaDao, BookRedisDao bookRedisDao, AuthorJpaDao authorJpaDao) {
        return new BookRepositoryImpl(bookJpaDao, bookRedisDao, authorJpaDao);
    }*/

    @Bean
    public BookRepository bookRepository(BookJpaDao bookJpaDao) {
        return new BookRepositoryImpl(bookJpaDao);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        return new BookServiceImpl(bookRepository, publisherRepository, authorRepository);
    }

    /************* PUBLISHER *************/

    @Bean
    public PublisherRepository publisherRepository(PublisherJpaDao publisherJpaDao) {
        return new PublisherRepositoryImpl(publisherJpaDao);
    }

    @Bean
    public PublisherService publisherService(PublisherRepository publisherRepository) {
        return new PublisherServiceImpl(publisherRepository);
    }

    /*************** AUTHOR **************/
    @Bean
    public AuthorRepository authorRepository(AuthorJpaDao authorJpaDao) {
        return new AuthorRepositoryImpl(authorJpaDao);
    }

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorServiceImpl(authorRepository);
    }

    /* *************** ObjectMapper **************/
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
