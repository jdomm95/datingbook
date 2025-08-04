package com.MyBlackBook.datingbook.repository;

import com.MyBlackBook.datingbook.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByNameContainingIgnoreCase(String name);
    List<Person> findByAreaContainingIgnoreCase(String area);
    List<Person> findByNameContainingIgnoreCaseAndAreaContainingIgnoreCase(String name, String area);

}
