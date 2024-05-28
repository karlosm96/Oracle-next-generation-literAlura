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
    private final String BASE_URL = "https://gutendex.com/books/";
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
                2- Mostrar todos los libros
                3- Buscar libro por nombre
                
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
        Books dataBooks = this.getBooksData(this.BASE_URL);
        List<Book> books = dataBooks.books();

        books.forEach(b->{
            BookModel bookModel = new BookModel(b);
            b.authors().forEach(a -> {
                AuthorModel authorModel = new AuthorModel(a);
                bookModel.addAuthor(authorModel);
            });
            System.out.println(bookModel);
            this.booksRepository.save(bookModel);
            //Save bookModel
        });
    }
}
