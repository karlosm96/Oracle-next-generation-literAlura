package com.literAlura.literAlura.models;

import jakarta.persistence.*;
import org.apache.tomcat.util.digester.ArrayStack;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class BookModel {
    @Id
    @Column(unique = true)
    private Long id;
    private String title;
    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private List<AuthorModel> authors;
    private List<String> languages;
    //private List<String> subjects;
    private Double downloadCount;

    public BookModel(){
    }

    public BookModel(Book book){
        this.id = book.id();
        this.title = book.title();
        this.downloadCount = book.downloadCount();
        this.languages = book.languages();
        // this.subjects = book.subjects();
        this.authors = new ArrayStack<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorModel> authors) {
        this.authors = authors;
    }

    public void addAuthor(AuthorModel author) {
        if(!this.authors.contains(author)){
            this.authors.add(author);
            author.addBook(this);
        }
    }

//    public List<String> getSubjects() {
//        return subjects;
//    }
//
//    public void setSubjects(List<String> subjects) {
//        this.subjects = subjects;
//    }


    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }

    public List<String> getAuthorsNames(List<AuthorModel> authors){
        List<String> authorsNames = new ArrayList<>();
        authors.forEach(a->authorsNames.add(a.getName()));
        return authorsNames;
    }


    @Override
    public String toString() {
        return "Id=" + this.id +
                ", Titulo='" + this.title +
                ", Authores='" + getAuthorsNames(this.authors) +
                //", authors=" + authors +
                ", Lenguajes=" + this.languages +
                ", Cantidad de descargas=" + this.downloadCount;
    }
}
