package com.MyBlackBook.datingbook.controller;

import com.MyBlackBook.datingbook.model.Person;
import com.MyBlackBook.datingbook.model.Tag;
import com.MyBlackBook.datingbook.repository.TagRepository;
import com.MyBlackBook.datingbook.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Manage tags and tag assignments")
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepo;

    public TagController(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Operation(summary = "Create a new tag", description = "Adds new tag record")
    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        // If tag with label already exists, return it
        Optional<Tag> existing = tagRepo.findByLabel(tag.getLabel());
        return existing.orElseGet(() -> tagRepo.save(tag));
    }

    @Operation(summary = "Retrieve all tags", description = "Get all tag records")
    @GetMapping
    @JsonView(View.WithPeople.class)
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Operation(summary = "Get a tag", description = "Retrieve tag with given ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return tagRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a tag", description = "Removes a tag record")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        if (tagRepo.existsById(id)) {
            tagRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Lists all people with a particular tag", description = "Returns all people with the given tag ID")
    @GetMapping("/{tagId}/people")
    @JsonView(View.Summary.class)
    public ResponseEntity<Set<Person>> getPeopleWithTag(@PathVariable Long tagId) {
        return tagRepo.findById(tagId)
                .map(tag -> ResponseEntity.ok(tag.getPeople()))
                .orElse(ResponseEntity.notFound().build());
    }
}
