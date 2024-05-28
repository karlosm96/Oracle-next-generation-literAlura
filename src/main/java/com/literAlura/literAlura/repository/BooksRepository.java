package com.literAlura.literAlura.repository;

import com.literAlura.literAlura.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<BookModel, Long> {

}
