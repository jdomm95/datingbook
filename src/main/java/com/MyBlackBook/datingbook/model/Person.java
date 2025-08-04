package com.MyBlackBook.datingbook.model;

import com.MyBlackBook.datingbook.view.View;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Long id;

    @JsonView(View.Summary.class)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "person_tag",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonView(View.WithTags.class)
    private Set<Tag> tags = new HashSet<>();

    // other fields and getters/setters
    @JsonView(View.PersonFull.class)
    private LocalDate firstDate;
    @JsonView(View.PersonFull.class)
    private String howYouMet;         // e.g., "Hinge", "Feeld", "through friends"
    @JsonView(View.PersonFull.class)
    private String relationshipStage; // e.g., "talking", "casual", "exclusive", "FWB", "no longer dating"
    @JsonView(View.PersonFull.class)
    private String communicationStyle; // e.g., "text mostly", "calls at night"
    @JsonView(View.PersonFull.class)
    private String frequencyOfSeeing; // e.g., "every weekend", "randomly", etc.
    @JsonView(View.PersonFull.class)
    private LocalDate birthday;
    @JsonView(View.PersonFull.class)
    private String area; // general area like "North Dallas"
    @JsonView(View.PersonFull.class)
    private String jobTitle;
    @JsonView(View.PersonFull.class)
    private String employer;
    @JsonView(View.PersonFull.class)
    private String industry;
    @JsonView(View.PersonFull.class)
    private String education; // e.g., "B.A. in Psychology, UT Austin"
    @JsonView(View.PersonFull.class)
    private String school;
    @JsonView(View.PersonFull.class)
    private String loveLanguages; // e.g., comma-separated "Touch, Quality Time"
    @JsonView(View.PersonFull.class)
    private String personalityNotes;
    @JsonView(View.PersonFull.class)
    private String notes;
    @JsonView(View.PersonFull.class)
    private String imageUrl; // e.g., local path or external URL to an image
    @JsonView(View.PersonFull.class)
    private Integer age;

    @JsonView(View.PersonFull.class)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    @JsonView(View.PersonFull.class)
    private String job;

    @JsonView(View.PersonFull.class)
    private String college;

    public Person() {
        // Required by JPA
    }

    public Person(String name, LocalDate birthday, String job) {
        this.name = name;
        this.birthday = birthday;
        this.job = job;
    }

    public Person(String name, LocalDate birthday, Integer age, String job,  String area) {
        this.name = name;
        this.birthday = birthday;
        this.job = job;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPeople().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPeople().remove(this);
    }


    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getFirstDate() { return firstDate; }
    public void setFirstDate(LocalDate firstDate) { this.firstDate = firstDate; }

    public String getHowYouMet() { return howYouMet; }
    public void setHowYouMet(String howYouMet) { this.howYouMet = howYouMet; }

    public String getRelationshipStage() { return relationshipStage; }
    public void setRelationshipStage(String relationshipStage) { this.relationshipStage = relationshipStage; }

    public String getCommunicationStyle() { return communicationStyle; }
    public void setCommunicationStyle(String communicationStyle) { this.communicationStyle = communicationStyle; }

    public String getFrequencyOfSeeing() { return frequencyOfSeeing; }
    public void setFrequencyOfSeeing(String frequencyOfSeeing) { this.frequencyOfSeeing = frequencyOfSeeing; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getEmployer() { return employer; }
    public void setEmployer(String employer) { this.employer = employer; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getLoveLanguages() { return loveLanguages; }
    public void setLoveLanguages(String loveLanguages) { this.loveLanguages = loveLanguages; }

    public String getPersonalityNotes() { return personalityNotes; }
    public void setPersonalityNotes(String personalityNotes) { this.personalityNotes = personalityNotes; }

    public Set<Tag> getTags() {return tags;}
    public void setTags(Set<Tag> tags) {this.tags = tags;}

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Child> getChildren() { return children; }
    public void setChildren(List<Child> children) { this.children = children; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
