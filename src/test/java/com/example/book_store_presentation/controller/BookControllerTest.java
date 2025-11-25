package com.example.book_store_presentation.controller;

import com.example.book_store_presentation.controller.mapper.BookMapper;
import com.example.book_store_presentation.controller.webModel.request.BookInsertRequest;
import com.example.book_store_presentation.controller.webModel.request.BookUpdateRequest;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.service.BookService;
import es.cesguiro.domain.service.PublisherService;
import es.cesguiro.domain.service.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private PublisherService publisherService;

    @Test
    void return_list_of_books() throws Exception {
        /*List<BookDto> bookDtos = Instancio.ofList(InstancioModel.BOOK_DTO_MODEL)
                .size(2)
                .create();*/
        BookDto bookDto1 = new BookDto(
                1234567890123L,
                "9780306406157",
                "Title 1", "Title 1 EN",
                "Synopsis 1 ES",
                "Synopsis 1 EN",
                new BigDecimal(40), new BigDecimal(20), new BigDecimal(5), "cover1.png",
                null, null, null);

        BookDto bookDto2 = new BookDto(
                125345354L,
                "2780308466157",
                "Synopsis 2 ES",
                "Title 2 EN",
                "Synopsis 2 EN",
                "cover2.jpg", null,
                new BigDecimal("25.00"),
                new BigDecimal("10.0"), "cover2.png",
                null, null, null);

        List<BookDto> bookDtos = List.of(bookDto1, bookDto2);

        Page<BookDto> bookDtoPage = new Page<>(
                bookDtos,
                1,
                10,
                2L,
                1
        );

        when(bookService.getAll(1, 10)).thenReturn(bookDtoPage);

        mockMvc.perform(get("/api/books?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].isbn").value(bookDtos.getFirst().isbn()))
                .andExpect(jsonPath("$.data[1].isbn").value(bookDtos.getLast().isbn()))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void return_book_when_isbn_exists() throws Exception {
        /*BookDto bookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .create();*/
        BookDto bookDto1 = new BookDto(
                1234567890123L,
                "9780306406157",
                "Title 1", "Title 1 EN",
                "Synopsis 1 ES",
                "Synopsis 1 EN",
                new BigDecimal(40), new BigDecimal(20), new BigDecimal(5), "cover1.png",
                null, null, null);

        when(bookService.getByIsbn(anyString())).thenReturn(bookDto1);
        
        
        mockMvc.perform(get("/api/books/" + bookDto1.isbn()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.isbn").value(bookDto1.isbn()));
    }

    @Test
    void return_not_found_when_isbn_does_not_exist() throws Exception {
        when(bookService.getByIsbn(anyString())).thenThrow(new ResourceNotFoundException("Book with isbn 1234567890123 not found"));

        mockMvc.perform(get("/api/books/1234567890123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return_newly_created_book() throws Exception {
        /*BookInsertRequest bookInsertRequest = Instancio.of(InstancioModel.BOOK_INSERT_REQUEST_MODEL)
                .create();
        BookDto bookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .create();*/
        BookDto bookDto1 = new BookDto(
                1234567890123L,
                "9780306406157",
                "Title 1", "Title 1 EN",
                "Synopsis 1 ES",
                "Synopsis 1 EN",
                new BigDecimal(40), new BigDecimal(20), new BigDecimal(5), "cover1.png",
                null, null, null);

        BookInsertRequest bookInsertRequest = new BookInsertRequest(
                "9780306406157",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                "new_cover.jpg",
                java.time.LocalDate.of(1987, 5, 10),
                new BigDecimal("19.50"),
                new BigDecimal("5.00"),
                2L,
                null
        );
        when(bookService.create(any())).thenReturn(bookDto1);

        // Convertir request a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(bookInsertRequest);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    void return_bad_request_when_create_book_with_invalid_data() throws Exception {
        /*BookInsertRequest bookInsertRequest = Instancio.of(InstancioModel.BOOK_INSERT_REQUEST_MODEL)
                .set(field(BookInsertRequest::isbn), "invalid_isbn")
                .create();*/

        BookInsertRequest bookInsertRequest = new BookInsertRequest(
                "invalid isbn",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                "new_cover.jpg",
                java.time.LocalDate.of(1987, 5, 10),
                new BigDecimal("19.50"),
                new BigDecimal("5.00"),
                2L,
                null
        );

        // Convertir request a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(bookInsertRequest);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_bad_request_when_create_book_with_future_publication_date() throws Exception {
        /*BookInsertRequest bookInsertRequest = Instancio.of(InstancioModel.BOOK_INSERT_REQUEST_MODEL)
                .set(field(BookInsertRequest::publicationDate), LocalDate.now().plusDays(10))
                .create();*/
        BookInsertRequest bookInsertRequest = new BookInsertRequest(
                "9780306406157",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                "new_cover.jpg",
                java.time.LocalDate.of(1987, 5, 10),
                new BigDecimal("19.50"),
                new BigDecimal("5.00"),
                2L,
                null
        );


        // Convertir request a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(bookInsertRequest);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_bad_request_when_json_is_invalid() throws Exception {
        // JSON mal formado: isbn como boolean en lugar de String
        String invalidJson = """
        {
            "isbn": true,
            "titleEs": "Libro ejemplo",
            "titleEn": "Example Book",
            "synopsisEs": "Sinopsis en español",
            "synopsisEn": "Synopsis in English",
            "cover": "cover.jpg",
            "publicationDate": "10-05-1987",
            "basePrice": 19.50,
            "discountPercentage": 5.00,
            "publisherId": 2,
            "authorIds": [3]
        }
        """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return_updated_book_when_update_book_with_valid_data() throws Exception {
        /*BookUpdateRequest bookUpdateRequest = Instancio.of(InstancioModel.BOOK_UPDATE_REQUEST_MODEL)
                .create();
        BookDto bookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .create();*/
        BookDto bookDto1 = new BookDto(
                1234567890123L,
                "9780306406157",
                "Title 1", "Title 1 EN",
                "Synopsis 1 ES",
                "Synopsis 1 EN",
                new BigDecimal(40), new BigDecimal(20), new BigDecimal(5), "cover1.png",
                null, null, null);

        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                978030640L,
                "9780306406157",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                "new_cover.jpg",
                java.time.LocalDate.of(1987, 5, 10),
                new BigDecimal("19.50"),
                new BigDecimal("5.00"),
                2L,
                null
        );

        when(bookService.update(any())).thenReturn(bookDto1);

        // Convertir request a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(bookUpdateRequest);

        mockMvc.perform(put("/api/books/" + bookUpdateRequest.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void return_bad_request_when_update_book_id_mismatch() throws Exception {
        // Creamos un BookUpdateRequest con un id concreto
        /*BookUpdateRequest bookUpdateRequest = Instancio.of(InstancioModel.BOOK_UPDATE_REQUEST_MODEL)
                .set(field(BookUpdateRequest::id), 1L) // id del body
                .create();*/
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest(
                1L,
                "9780306406157",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                "new_cover.jpg",
                java.time.LocalDate.of(1987, 5, 10),
                new BigDecimal("19.50"),
                new BigDecimal("5.00"),
                2L,
                null
        );

        // Convertir request a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(bookUpdateRequest);

        // Hacemos PUT con un id diferente en la URL
        mockMvc.perform(put("/api/books/2") // id distinto del body
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isBadRequest()); // Se espera 400
    }

    @Test
    void delete_book_successfully() throws Exception {
        // Suponemos que bookService.deleteByIsbn no lanza excepción
        doNothing().when(bookService).deleteByIsbn("1234567890123");

        mockMvc.perform(delete("/api/books/1234567890123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_book_not_found() throws Exception {
        // Si el libro no existe, lanzamos ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Book with isbn 1234567890123 not found"))
                .when(bookService).deleteByIsbn("1234567890123");

        mockMvc.perform(delete("/api/books/1234567890123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_book_unexpected_error() throws Exception {
        // Simular un error inesperado
        doThrow(new RuntimeException("Database error"))
                .when(bookService).deleteByIsbn("1234567890123");

        mockMvc.perform(delete("/api/books/1234567890123"))
                .andExpect(status().isInternalServerError());
    }




}