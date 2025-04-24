package com.example.myproject;

import com.example.myproject.model.Book;
import com.example.myproject.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    BookRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        Book freddy = new Book("1", "Freddy Mercury", "Queen", "###test");
        Book paul = new Book("2", "Paul McCartney", "Beatles", "###test");
        Book mick = new Book("3", "Mick Jagger", "Rolling Stones", "###test");
        underTest.saveAll(List.of(freddy, paul, mick));
    }

    @AfterEach
    void tearDown() {
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getDescription().contains("###test"))
                .toList();
        underTest.deleteAll(booksToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testSetShouldContains_3_Records_ToTest(){
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getDescription().contains("###test"))
                .toList();
        assertEquals(3,booksToDelete.size());
    }

    @Test
    void shouldGiveIdForNewRecord() {
        // given
        Book john = new Book("John Lennon", "Beatles", "###test");
        // when
        underTest.save(john);
        Book bookFromDb = underTest.findAll().stream()
                .filter(book -> book.getName().equals("John Lennon"))
                .findFirst().orElse(null);
        // then
        assertFalse(bookFromDb.getId() == john.getId());
        assertNotNull(bookFromDb);
        assertNotNull(bookFromDb.getId());
        assertFalse(bookFromDb.getId().isEmpty());
        assertEquals(24, bookFromDb.getId().length());
    }@Test
    void whenRecordHasIdThenItIsPossibleToSave() {
        // given
        Book john = Book.builder()
                .id("1")
                .description("###test2")
                .build();
        // when
        underTest.save(john);
        Book bookFromDb = underTest.findAll().stream()
                .filter(book -> book.getDescription().equals("###test2"))
                .findFirst().orElse(null);
        // then

        assertNotNull(bookFromDb);
    }

    //  id = "";

    // upgrade  - 2 test
    // find by Code native query


}