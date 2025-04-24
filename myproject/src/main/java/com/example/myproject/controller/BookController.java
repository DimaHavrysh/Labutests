package com.example.myproject.controller;

import com.example.myproject.model.Book;
import com.example.myproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<Book> showAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book showOneById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @PostMapping
    public Book insert(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping
    public Book edit(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        bookService.delById(id);
    }
}
