package com.MyBlackBook.datingbook.controller;

import com.MyBlackBook.datingbook.model.Person;
import com.MyBlackBook.datingbook.model.Tag;
import com.MyBlackBook.datingbook.repository.PersonRepository;

import com.MyBlackBook.datingbook.repository.TagRepository;
import com.MyBlackBook.datingbook.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@io.swagger.v3.oas.annotations.tags.Tag(name = "People", description = "Manage people and their details")
@CrossOrigin(origins = "*") // allows requests from any domain (good for testing)
@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonRepository personRepo;

    public PersonController(PersonRepository personRepo, TagRepository tagRepo) {
        this.personRepo = personRepo;
        this.tagRepo = tagRepo;
    }

    private final TagRepository tagRepo;

    @Operation(summary = "Get all people", description = "Returns a list of all people in the system.")
    @GetMapping
    @JsonView(View.WithTags.class)
    public List<Person> getAllPeople() {
        return personRepo.findAll();
    }

    @Operation(summary = "Get person from ID", description = "Returns single person from ID.")
    @GetMapping("/{id}")
    @JsonView(View.PersonFull.class)
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a person", description = "Create new person in db.")
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person saved = personRepo.save(person);
        return ResponseEntity.ok(saved); // or created(URI) if you want to be fancy
    }

    @Operation(summary = "Change details on a person", description = "Make modifications to a person record.")
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person updated) {
        return personRepo.findById(id).map(person -> {
            person.setName(updated.getName());
            person.setAge(updated.getAge());
            person.setArea(updated.getArea());
            person.setBirthday(updated.getBirthday());
            person.setJobTitle(updated.getJobTitle());
            person.setEmployer(updated.getEmployer());
            person.setEducation(updated.getEducation());
            person.setSchool(updated.getSchool());
            person.setHowYouMet(updated.getHowYouMet());
            person.setRelationshipStage(updated.getRelationshipStage());
            person.setCommunicationStyle(updated.getCommunicationStyle());
            person.setFrequencyOfSeeing(updated.getFrequencyOfSeeing());
            person.setTags(updated.getTags());
            person.setNotes(updated.getNotes());
            person.setImageUrl(updated.getImageUrl());
            return ResponseEntity.ok(personRepo.save(person));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove a person", description = "Remove a person.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personRepo.existsById(id)) {
            personRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Find a person", description = "Retrieve a person based on name, area, or both.")
    @GetMapping("/search")
    public List<Person> searchPeople(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String area) {
        if (name != null && area != null) {
            return personRepo.findByNameContainingIgnoreCaseAndAreaContainingIgnoreCase(name, area);
        } else if (name != null) {
            return personRepo.findByNameContainingIgnoreCase(name);
        } else if (area != null) {
            return personRepo.findByAreaContainingIgnoreCase(area);
        } else {
            return personRepo.findAll(); // fallback
        }
    }

    @Operation(summary = "Add a tag to a person", description = "Associates a tag with a person.")
    @PutMapping("/{personId}/tags/{tagId}")
    public ResponseEntity<?> addTagToPerson(@PathVariable Long personId, @PathVariable Long tagId) {
        Optional<Person> personOpt = personRepo.findById(personId);
        Optional<Tag> tagOpt = tagRepo.findById(tagId);

        if (personOpt.isEmpty() || tagOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Person person = personOpt.get();
        Tag tag = tagOpt.get();

        person.getTags().add(tag);
        personRepo.save(person); // Only saving one side is enough

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a person", description = "Removes a person.")
    @DeleteMapping("/{personId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromPerson(@PathVariable Long personId, @PathVariable Long tagId) {
        Optional<Person> personOpt = personRepo.findById(personId);
        Optional<Tag> tagOpt = tagRepo.findById(tagId);
        if (personOpt.isEmpty() || tagOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Person person = personOpt.get();
        Tag tag = tagOpt.get();
        person.getTags().remove(tag);
        personRepo.save(person);
        return ResponseEntity.noContent().build();
    }

    // GET /api/people/{personId}/tags
    @Operation(summary = "Get tags for a person", description = "Associates a tag with a person.")
    @GetMapping("/{personId}/tags")
    public ResponseEntity<Set<Tag>> getTagsForPerson(@PathVariable Long personId) {
        return personRepo.findById(personId)
                .map(person -> ResponseEntity.ok(person.getTags()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Use tag to find people", description = "Locates people matching a tag.")
    @GetMapping("/{tagId}/people")
    public ResponseEntity<Set<Person>> getPeopleWithTag(@PathVariable Long tagId) {
        return tagRepo.findById(tagId)
                .map(tag -> ResponseEntity.ok(tag.getPeople()))
                .orElse(ResponseEntity.notFound().build());
    }

}
