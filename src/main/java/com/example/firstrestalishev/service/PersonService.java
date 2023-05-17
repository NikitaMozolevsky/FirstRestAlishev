package com.example.firstrestalishev.service;

import com.example.firstrestalishev.entity.Person;
import com.example.firstrestalishev.repository.BookRepository;
import com.example.firstrestalishev.repository.PersonRepository;
import com.example.firstrestalishev.util.PersonNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional()
@Service
public class PersonService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonService(BookRepository bookRepository,
                         PersonRepository personRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElseThrow(PersonNotFoundException::new);
    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void updatePerson(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }


    public boolean personIsExist(String name) {
        try {
            return personRepository.findByName(name).get(0) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Transactional
    public void save(Person person) {
        enrichPerson(person);
        personRepository.save(person);
    }

    @PostConstruct
    public void startProgramProcessPerson() {
        personRepository.deleteAll();
        personRepository.saveAll(List.of(
            new Person("Nikolas, Cage", 1989),
            new Person("Garry, Potter", 1989),
            new Person("Char, Bos", 1989),
            new Person("Egin, Bauer", 1989)
        ));
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");//with Spring Security
    }

}
