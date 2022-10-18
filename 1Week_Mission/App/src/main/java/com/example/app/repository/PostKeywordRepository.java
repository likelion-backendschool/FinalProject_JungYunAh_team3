package com.example.app.repository;

import com.example.app.domain.PostKeyword;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostKeywordRepository extends JpaRepository<PostKeyword, Long> {

  Optional<PostKeyword> findByContent(String content);
}
