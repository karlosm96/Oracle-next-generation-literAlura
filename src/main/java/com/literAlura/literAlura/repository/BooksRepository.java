package com.literAlura.literAlura.repository;

import com.literAlura.literAlura.models.AuthorModel;
import com.literAlura.literAlura.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<BookModel, Long> {

    @Query("SELECT b FROM BookModel b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :bookTitle, '%'))")
    List<BookModel> findBookByTitle(String bookTitle);

    @Query("SELECT authors FROM BookModel")
    List<AuthorModel> findAllAuthors();

    @Query("SELECT a FROM BookModel b JOIN b.authors a WHERE a.deathYear > :yearAlive")
    List<AuthorModel> findAuthorsByYearAlive(int yearAlive);

    @Query("SELECT DISTINCT a FROM BookModel b JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<AuthorModel> findAuthorByName(String authorName);

    @Query("SELECT b FROM BookModel b ORDER BY b.downloadCount DESC LIMIT 10")
    List<BookModel> findTop10DownloadBooks();
}
