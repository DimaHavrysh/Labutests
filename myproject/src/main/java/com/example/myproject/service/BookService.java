package com.example.myproject.service;

import com.example.myproject.model.Book;
import com.example.myproject.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();
    {
        books.add(new Book("1","name1","0001","description1"));
        books.add(new Book("2","name2","0002","description2"));
        books.add(new Book("3","name3","0003","description3"));
    }

    @PostConstruct
    void init() {
        bookRepository.deleteAll();
        bookRepository.saveAll(books);
    }
    //CRUD
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delById(String id) {
        bookRepository.deleteById(id);
    }
}
