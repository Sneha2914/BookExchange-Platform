package com.example.book_exhange_platform.service;

import com.example.book_exhange_platform.model.Book;
import com.example.book_exhange_platform.model.User;
import com.example.book_exhange_platform.repository.BookRepository;
import com.example.book_exhange_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    // Store a new book
    public Book storeBook(String title,
                          String author,
                          String genre,
                          String location,
                          String condition,
                          Boolean availability,
                          Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            Book book = new Book(title, author, genre, location, condition, availability, user.get());
            return bookRepository.save(book);
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> bookRepository.findByUser(value)).orElse(null);
    }

    // Search books
    public List<Book> searchBooks(String title, String author, String genre, String location, Boolean availability) {
        List<Book> books = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        }
        if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        }
        if (genre != null && !genre.isEmpty()) {
            return bookRepository.findByGenreContainingIgnoreCase(genre);
        }
        if (location != null && !location.isEmpty()) {
            return bookRepository.findByLocationContainingIgnoreCase(location);
        }
        if (availability != null) {
            return bookRepository.findByAvailability(availability);
        }
        return bookRepository.findAll();
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}