package com.MyBlackBook.datingbook.model;

import com.MyBlackBook.datingbook.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.PersonFull.class)
    private String name;
    private LocalDate birthday;
    @JsonView(View.PersonFull.class)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Person parent;
    private String custodySchedule;

    // Constructors
    public Child() {}

    public Child(String name, LocalDate birthday, Person parent, Integer age) {
        this.name = name;
        this.birthday = birthday;
        this.parent = parent;
        this.age = age;
    }
    public Child(String name, LocalDate birthday, Integer age, String custodySchedule, Person parent) {
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.custodySchedule = custodySchedule;
        this.parent = parent;
    }


    // Getters and setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public Person getParent() { return parent; }
    public void setParent(Person parent) { this.parent = parent; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public void setPerson(Person person) {
        this.parent = person;
    }
    public String getCustodySchedule() { return custodySchedule; }
    public void setCustodySchedule(String custodySchedule) { this.custodySchedule = custodySchedule; }

}
