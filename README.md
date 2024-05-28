# literAlura

Esta aplicación de consola permite gestionar y consultar información de libros y autores obtenidos desde la API de Gutendex (https://gutendex.com/). Proporciona varias funcionalidades, como cargar datos, listar libros y autores, buscar libros por título, buscar autores por nombre, y mostrar estadísticas de descargas.

## Requisitos/ Tecnologias

- Java 17
- PostgreSQL 15
- Spring 3.2.6
- Maven 3

## Instalación

1. Clona este repositorio:

    ```bash
    git clone https://github.com/tu-usuario/literAlura.git
    cd literAlura
    ```

2. Configura tu base de datos en el archivo `application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/tubasedatos
    spring.datasource.username=tuusuario
    spring.datasource.password=tupassword
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

3. Compila y ejecuta la aplicación:

    ```bash
    mvn clean install
    mvn exec:java -Dexec.mainClass="com.literAlura.literAlura.Main.Main"
    ```

## Uso

Al ejecutar la aplicación, se presentará un menú con las siguientes opciones:

### Funcionalidades

1. **Cargar información**: Carga datos de libros desde la API de Gutendex según el número de página especificado.
2. **Listar todos los libros**: Muestra una lista de todos los libros almacenados en la base de datos.
3. **Buscar libro por nombre**: Busca y muestra libros cuyo título coincida con el nombre ingresado.
4. **Listar todos los autores**: Muestra una lista de todos los autores almacenados en la base de datos.
5. **Buscar autor por nombre**: Busca y muestra autores cuyo nombre coincida con el nombre ingresado.
6. **Listar autores vivos en un año determinado**: Muestra autores que estaban vivos en el año especificado.
7. **Cantidad de libros por idioma**: Muestra la cantidad de libros disponibles en el idioma especificado.
8. **Listar el top 10 de los libros más descargados**: Muestra una lista de los 10 libros con más descargas.
9. **Mostrar estadísticas**: Muestra estadísticas de descargas, incluyendo el total de descargas, el libro con la menor cantidad de descargas y el libro con la mayor cantidad de descargas, así como el promedio de descargas por libro.


## Personas Desarrolladoras del Proyecto

- [Carlos Muñoz](https://github.com/karlosm96)