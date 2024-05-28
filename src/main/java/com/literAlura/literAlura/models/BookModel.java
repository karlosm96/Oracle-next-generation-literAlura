package com.literAlura.literAlura.models;

import jakarta.persistence.*;
import org.apache.tomcat.util.digester.ArrayStack;

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
    private List<String> subjects;
    private Double downloadCount;

    public BookModel(){
    }

    public BookModel(Book book){
        this.id = book.id();
        this.title = book.title();
        this.downloadCount = book.downloadCount();
        this.subjects = book.subjects();
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

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }


    @Override
    public String toString() {
        return "id=" + id +
                ", title='" + title + '\'' +
                //", authors=" + authors +
                ", subjects=" + subjects +
                ", downloadCount=" + downloadCount;
    }
}
