package com.example.book_exhange_platform.controller;

import com.example.book_exhange_platform.model.Book;
import com.example.book_exhange_platform.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestParam String title,
                                        @RequestParam String author,
                                        @RequestParam String genre,
                                        @RequestParam String location,
                                        @RequestParam String condition,
                                        @RequestParam Boolean availability,
                                        @RequestParam Long userId) {

        try {
            Book book = bookService.storeBook(title, author, genre, location, condition, availability, userId);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<Book>> getAllBooks() {

        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.status(HttpStatus.OK).body(books);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Book>> getBooksByUser(@RequestParam Long userId) {
        try {
            List<Book> books = bookService.getBooksByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(books);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<Void> deleteBook(@RequestParam Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

