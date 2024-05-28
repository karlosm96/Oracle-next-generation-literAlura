package com.literAlura.literAlura.models;

import jakarta.persistence.*;
import org.apache.tomcat.util.digester.ArrayStack;

import java.util.List;

@Entity
@Table(name = "autores")
public class AuthorModel {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookModel> books;

    public AuthorModel(){
    }

    public AuthorModel(Author author){
        this.name = author.name();
        this.birthYear = author.birthYear();
        this.deathYear = author.deathYear();
        this.books = new ArrayStack<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<BookModel> getBook() {
        return books;
    }

    public void setBook(BookModel book) {
        this.books.add(book);
    }

    public void addBook(BookModel book) {
        if(!this.books.contains(book)){
            this.books.add(book);
            book.addAuthor(this);
        }
    }

    @Override
    public String toString() {
        return "Nombre='" + name +
                ", Año de nacimiento=" + birthYear +
                ", Año de defunción=" + deathYear;
                //", books=" + books;
    }
}
