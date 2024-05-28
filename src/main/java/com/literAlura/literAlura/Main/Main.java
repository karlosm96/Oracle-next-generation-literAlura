package com.literAlura.literAlura.Main;

import com.literAlura.literAlura.models.*;

import com.literAlura.literAlura.repository.BooksRepository;
import com.literAlura.literAlura.services.DataCasting;
import com.literAlura.literAlura.services.FetchAPIGutendex;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final FetchAPIGutendex fetchAPIGutendex = new FetchAPIGutendex();
    private final String BASE_URL = "https://gutendex.com/books/?page=";
    private final DataCasting dataCasting = new DataCasting();
    private final BooksRepository booksRepository;

    public Main(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    public void Menu(){
        int run = -1;
        int inOption;

        String menu = """
                *** Menu de opciones ***
                
                1- Cargar información
                2- Listar todos los libros
                3- Buscar libro por nombre
                4- Listar todos los autores
                5- Listar autores vivos en un año determinado
                6- Cantidad de libros por idioma
                
                0- Finalizar el programa
                """;

        while (run != 0){
            System.out.println(menu);
            System.out.println("Porfavor ingrese una opción: ");
            inOption = this.scanner.nextInt();
            this.scanner.nextLine();

            switch (inOption){
                case 1:
                    this.saveData();
                    break;
                case 2:
                    this.listAllBooks();
                    break;
                case 3:
                    this.findBookByTitle();
                    break;
                case 4:
                    this.listAllAuthors();
                    break;
                case 5:
                    this.listAuthorsByYearAlive();
                    break;
                case 6:
                    this.listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Gracias por usar nuestra aplicación....");
                    run = 0;
                    break;
                default:
                    System.out.println("La opcion ingresada es incorrecta;");
                    break;
            }
        }
    }

    private Books getBooksData(String url){
         String fetchData = this.fetchAPIGutendex.getData(url);
         return this.dataCasting.getData(fetchData, Books.class);
    }

    private void saveData(){
        System.out.println("Porfavor ingresa el numero de la página: ");
        int page = this.scanner.nextInt();
        this.scanner.nextLine();
        Books dataBooks = this.getBooksData(this.BASE_URL + page);
        List<Book> books = dataBooks.books();

        books.forEach(b->{
            BookModel bookModel = new BookModel(b);
            b.authors().forEach(a -> {
                AuthorModel authorModel = new AuthorModel(a);
                bookModel.addAuthor(authorModel);
            });
            this.booksRepository.save(bookModel);
            //Save bookModel
        });
    }

    private void listAllBooks(){
        System.out.println("### Lista de libros ###");
        List<BookModel> books = this.booksRepository.findAll();
        books.forEach(System.out::println);
    }

    private void findBookByTitle(){
        System.out.println("Porfavor ingrese el nombre del libro: ");
        String bookTitle = this.scanner.nextLine();
        BookModel bookByTitle = this.booksRepository.findBookByTitle(bookTitle);
        System.out.println("### Libro ###");
        System.out.println(bookByTitle);
    }

    public void listAllAuthors(){
        System.out.println("### Lista de autores ###");
        List<AuthorModel> authors = this.booksRepository.findAllAuthors();
        authors.forEach(System.out::println);
    }

    private void listAuthorsByYearAlive(){
        System.out.println("Porvaor ingrese el año: ");
        int yearAlive = this.scanner.nextInt();
        this.scanner.nextLine();
        List<AuthorModel> authorModelsByYear = this.booksRepository.findAuthorsByYearAlive(yearAlive);
        authorModelsByYear.forEach(System.out::println);
    }

    private void listBooksByLanguage(){
        System.out.println("Ingrese el idioma: ");
        String language = this.scanner.nextLine();
        List<BookModel> books = this.booksRepository.findAll();
        Long counterBooks = books.stream()
                .filter(b-> b.getLanguages().contains(language))
                .count();
        System.out.printf("Total de libros en idioma '%s': %d\n", language, counterBooks);
    }
}
