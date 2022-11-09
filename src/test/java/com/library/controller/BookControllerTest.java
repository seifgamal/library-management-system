package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBookById() throws Exception {
        String bookName = "New Book";
        Long bookId = 1L;
        Book testBook = new Book(bookId, bookName, "Here's a new book", true, 0, 1, 1);
        Mockito.when(bookService.getBookById(Mockito.anyLong())).thenReturn(testBook);

        this.mockMvc.perform(get("/books/1"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", Matchers.is(bookName)),
                        jsonPath("$.id", Matchers.is(bookId))
                );
    }
}
