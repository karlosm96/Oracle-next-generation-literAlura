package com.literAlura.literAlura.repository;

import com.literAlura.literAlura.models.AuthorModel;
import com.literAlura.literAlura.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<BookModel, Long> {

    @Query("SELECT b FROM BookModel b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :bookTitle, '%'))")
    BookModel findBookByTitle(String bookTitle);

    @Query("SELECT authors FROM BookModel")
    List<AuthorModel> findAllAuthors();

    @Query("SELECT a FROM BookModel b JOIN b.authors a WHERE a.deathYear > :yearAlive")
    List<AuthorModel> findAuthorsByYearAlive(int yearAlive);

    @Query(value = "SELECT DISTINCT b FROM BookModel b WHERE :language = ANY(b.languages)", nativeQuery = true)
    List<BookModel> findBooksByLanguage(String language);
}
