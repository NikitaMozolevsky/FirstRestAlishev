package com.example.firstrestalishev.service;

import com.example.firstrestalishev.entity.Person;
import com.example.firstrestalishev.repository.BookRepository;
import com.example.firstrestalishev.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Book;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class BookService {

    private final BookRepository bookRepository;

    private final PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }
/*
    public Optional<List<Book>> findBooksByNameIsStartingWith(String name) {
        return bookRepository.findBooksByNameIsStartingWith(name);
    }*/

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }


    public boolean bookIsExist(String name) {
        return !bookRepository.findBooksByName(name).get().isEmpty();
        /*try {
            Book stringOptional = bookRepository.findBooksByName(name).get().get(0);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }*/
    }

    @PostConstruct
    public void startProgramProcessBook() {

    }
}
