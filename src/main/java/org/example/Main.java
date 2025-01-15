package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros en la base de datos");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    Book book = gutendexService.searchBookByTitle(title);
                    if (!bookRepository.existsByTitle(book.getTitle())) {
                        bookRepository.save(book);
                        System.out.println("Libro guardado: " + book);
                    } else {
                        System.out.println("El libro ya existe en la base de datos.");
                    }
                    break;
                case 2:
                    bookRepository.findAll().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}