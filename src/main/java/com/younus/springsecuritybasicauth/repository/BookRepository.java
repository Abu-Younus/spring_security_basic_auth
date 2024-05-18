package com.younus.springsecuritybasicauth.repository;

import com.younus.springsecuritybasicauth.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
