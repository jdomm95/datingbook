package com.MyBlackBook.datingbook.model;

import com.MyBlackBook.datingbook.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Long id;

    @JsonView(View.Summary.class)
    private String label;

    @ManyToMany(mappedBy = "tags")
    @JsonView(View.WithPeople.class)
    private Set<Person> people = new HashSet<>();

    // constructor, getters/setters
    public Tag() {}

    public Tag(String label) {
        this.label = label;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Set<Person> getPeople() { return people; }
    public void setPeople(Set<Person> people) { this.people = people; }
}
