package com.example.firstrestalishev.repository;

import com.example.firstrestalishev.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name);
    Person findTopByOrderByIdDesc();
}
