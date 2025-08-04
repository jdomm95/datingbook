package com.MyBlackBook.datingbook.controller;

import com.MyBlackBook.datingbook.model.Child;
import com.MyBlackBook.datingbook.model.Person;
import com.MyBlackBook.datingbook.repository.ChildRepository;

import com.MyBlackBook.datingbook.repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

@Tag(name = "Children", description = "Manage children linked to people")
@RestController
@RequestMapping("/children")
public class ChildController {

    private final ChildRepository childRepo;

    @Autowired
    private PersonRepository personRepository;


    public ChildController(ChildRepository childRepo) {
        this.childRepo = childRepo;
    }

    @GetMapping
    public List<Child> getAllChildren() {
        return childRepo.findAll();
    }

    @Operation(summary = "Get child by ID", description = "Retrieves a child record based on ID")
    @GetMapping("/{id}")
    public ResponseEntity<Child> getChildById(@PathVariable Long id) {
        return childRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new child", description = "Adds new child record")
    @PostMapping
    public Child createChild(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String birthdayStr = (String) payload.get("birthday");
        Long personId = ((Number) payload.get("personId")).longValue();

        LocalDate birthday = LocalDate.parse(birthdayStr);
        Optional<Person> parent = personRepository.findById(personId);
        if (parent.isEmpty()) throw new RuntimeException("Parent not found");

        Child child = new Child();
        child.setName(name);
        child.setBirthday(birthday);
        child.setParent(parent.get());

        return childRepo.save(child);
    }

    @Operation(summary = "Modify child record", description = "Updates details of child")
    @PutMapping("/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable Long id, @RequestBody Child updated) {
        return childRepo.findById(id).map(child -> {
            child.setName(updated.getName());
            child.setBirthday(updated.getBirthday());
            child.setAge(updated.getAge());
            child.setParent(updated.getParent()); // assumes parent is already saved
            return ResponseEntity.ok(childRepo.save(child));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a child", description = "Removes child from the db.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        if (childRepo.existsById(id)) {
            childRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get children of person ID", description = "Returns the children of a person ID")
    @GetMapping("/people/{personId}/children")
    public List<Child> getChildrenByPersonId(@PathVariable Long personId) {
        return childRepo.findByParentId(personId);
    }

    @Operation(summary = "Get a parent from a child", description = "Returns parents of a child")
    @GetMapping("/{id}/parent")
    public ResponseEntity<Person> getParentByChildId(@PathVariable Long id) {
        Optional<Child> child = childRepo.findById(id);
        return child.map(c -> ResponseEntity.ok(c.getParent()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
