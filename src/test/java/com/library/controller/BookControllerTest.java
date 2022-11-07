package com.library.controller;

import com.library.model.Book;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import com.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBookById() throws Exception {
        Book testBook = new Book(1, "New Book", "Here's a new book", true, 0, 1, 1);
        Mockito.when(bookService.getBookById(Mockito.anyInt())).thenReturn(testBook);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/books/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":1,\"name\":\"New Book\",\"description\":\"Here's a new book\",\"rate\":0.0"
                + ",\"authorId\":1,\"categoryId\":1,\"available\":true}";
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(expected, result.getResponse().getContentAsString());
    }
}
