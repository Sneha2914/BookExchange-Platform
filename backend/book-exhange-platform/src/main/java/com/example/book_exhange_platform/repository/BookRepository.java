package com.example.book_exhange_platform.repository;

import com.example.book_exhange_platform.model.Book;
import com.example.book_exhange_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreContainingIgnoreCase(String genre);
    List<Book> findByLocationContainingIgnoreCase(String location);
    List<Book> findByAvailability(Boolean availability);
    List<Book> findByUser(User user);
}