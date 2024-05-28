package com.literAlura.literAlura.Main;

import com.literAlura.literAlura.models.*;

import com.literAlura.literAlura.repository.BooksRepository;
import com.literAlura.literAlura.services.DataCasting;
import com.literAlura.literAlura.services.FetchAPIGutendex;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final FetchAPIGutendex fetchAPIGutendex = new FetchAPIGutendex();
    private final String BASE_URL = "https://gutendex.com/books/?page=";
    private final DataCasting dataCasting = new DataCasting();
    private final BooksRepository booksRepository;
    private List<BookModel> booksList = new ArrayList<>();

    public Main(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void Menu() {
        int run = -1;
        int inOption;

        String menu = """
                *** Menu de opciones ***
                                
                1- Cargar información.
                2- Listar todos los libros.
                3- Buscar libro por nombre.
                4- Listar todos los autores.
                5- Buscar autor por nombre.
                6- Listar autores vivos en un año determinado.
                7- Cantidad de libros por idioma.
                8- Listar el top 10 de los libros mas descargados.
                9- Mostrar estadisticas.
                                
                0- Finalizar el programa
                """;

        while (run != 0) {
            System.out.println(menu);
            System.out.println("Porfavor ingrese una opción: ");
            inOption = this.scanner.nextInt();
            this.scanner.nextLine();

            switch (inOption) {
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
                    this.findAuthorByName();
                    break;
                case 6:
                    this.listAuthorsByYearAlive();
                    break;
                case 7:
                    this.listBooksByLanguage();
                    break;
                case 8:
                    this.listTop10DownloadBooks();
                    break;
                case 9:
                    this.showStatistics();
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

    private Books getBooksData(String url) {
        String fetchData = this.fetchAPIGutendex.getData(url);
        return this.dataCasting.getData(fetchData, Books.class);
    }

    private void saveData() {
        System.out.println("Porfavor ingresa el numero de la página: ");
        int page = this.scanner.nextInt();
        this.scanner.nextLine();
        Books dataBooks = this.getBooksData(this.BASE_URL + page);
        List<Book> books = dataBooks.books();

        books.forEach(b -> {
            BookModel bookModel = new BookModel(b);
            b.authors().forEach(a -> {
                AuthorModel authorModel = new AuthorModel(a);
                bookModel.addAuthor(authorModel);
            });
            this.booksRepository.save(bookModel);
            //Save bookModel
        });
    }

    private void listAllBooks() {
        System.out.println("### Lista de libros ###");
        List<BookModel> books = this.booksRepository.findAll();
        books.forEach(System.out::println);
    }

    private void findBookByTitle() {
        System.out.println("Porfavor ingrese el nombre del libro: ");
        String bookTitle = this.scanner.nextLine();
        List<BookModel> bookByTitle = this.booksRepository.findBookByTitle(bookTitle);
        System.out.println("### Libro ###");
        if(bookByTitle.isEmpty()){
            System.out.println("No se encontro ningun libro con ese nombre...");
        }
        bookByTitle.forEach(System.out::println);
    }

    public void listAllAuthors() {
        System.out.println("### Lista de autores ###");
        List<AuthorModel> authors = this.booksRepository.findAllAuthors();
        authors.forEach(System.out::println);
    }

    public void findAuthorByName(){
        System.out.println("Porfavor ingrese el nombre del autor: ");
        String authorName = this.scanner.nextLine();
        List<AuthorModel> authorsByName = this.booksRepository.findAuthorByName(authorName);
        System.out.println("### Autor ###");
        if(authorsByName.isEmpty()){
            System.out.println("No se encontro ningun autor con ese nombre...");
        }
        authorsByName.forEach(System.out::println);
    }

    private void listAuthorsByYearAlive() {
        System.out.println("Porvaor ingrese el año: ");
        int yearAlive = this.scanner.nextInt();
        this.scanner.nextLine();
        List<AuthorModel> authorModelsByYear = this.booksRepository.findAuthorsByYearAlive(yearAlive);
        authorModelsByYear.forEach(System.out::println);
    }

    private void listBooksByLanguage() {
        if(this.booksList.isEmpty()){
            this.booksList = this.booksRepository.findAll();
        }
        System.out.println("Ingrese el idioma: ");
        String language = this.scanner.nextLine();
        Long counterBooks = this.booksList.stream()
                .filter(b -> b.getLanguages().contains(language))
                .count();
        System.out.printf("Total de libros en idioma '%s': %d\n", language, counterBooks);
    }

    private void listTop10DownloadBooks(){
        List<BookModel> top10Books = this.booksRepository.findTop10DownloadBooks();
        top10Books.forEach(System.out::println);
    }

    private void showStatistics(){
        if(this.booksList.isEmpty()){
            this.booksList = this.booksRepository.findAll();
        }

        DoubleSummaryStatistics est = this.booksList.stream().
                filter(b-> b.getDownloadCount() > 0)
                .collect(Collectors.summarizingDouble(BookModel::getDownloadCount));

        Optional<BookModel> bookMinDownloadCount = this.booksList.stream()
                        .min(Comparator.comparing(BookModel::getDownloadCount));

        String bookMinDownloadsMessage = bookMinDownloadCount
                .map(book -> book.getTitle() + " con " + book.getDownloadCount() + " descargas")
                .orElse("No se encontró ningún libro.");

        Optional<BookModel> bookMaxDownloadCount = this.booksList.stream()
                .max(Comparator.comparing(BookModel::getDownloadCount));

        String bookMaxDownloadsMessage = bookMaxDownloadCount
                .map(book -> book.getTitle() + " con " + book.getDownloadCount() + " descargas")
                .orElse("No se encontró ningún libro.");

        System.out.println("Cantidad de libros: " + est.getCount() + "\n" +
                "Total de descargas: " + est.getSum() + "\n" +
                "Libro con la menos cantidad de descargas: " + bookMinDownloadsMessage + "\n" +
                "Libro con la mayor cantidad de descargas: " + bookMaxDownloadsMessage + "\n" +
                "Promedio de descargas: " + est.getAverage());
    }
}
