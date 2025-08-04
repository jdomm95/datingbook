package com.MyBlackBook.datingbook.view;

public class View {
    public interface Summary {}               // Minimal info (id, name/label)
    public interface WithTags extends Summary {}   // Person with tags (shallow)
    public interface WithPeople extends Summary {} // Tag with people (shallow)
    public interface PersonFull extends WithTags {}
}
