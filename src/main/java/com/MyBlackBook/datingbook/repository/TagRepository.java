package com.MyBlackBook.datingbook.repository;

import com.MyBlackBook.datingbook.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByLabel(String label);
}
