package com.luiscm.literasearch.ui;

import com.luiscm.literasearch.model.Libro;
import java.io.Console;
import java.util.List;
import com.luiscm.literasearch.model.DatosLibros;
import com.luiscm.literasearch.service.ConsumoAPI;
import com.luiscm.literasearch.service.IConvierteDatos;
import com.luiscm.literasearch.service.LibroService;

public class MenuPrincipal {
    private final Console console;
    private final LibroService libroService;
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final IConvierteDatos conversor = new com.luiscm.literasearch.service.ConvierteDatos();

    // Constantes para formato de texto
    private static final String LINEA = "===================================";

    public MenuPrincipal(LibroService libroService) {
        this.console = System.console();
        this.libroService = libroService;
    }

    public void mostrarMenu() {
        if (console == null) {
            System.err.println("No se pudo obtener la consola. Ejecuta la aplicación desde una terminal.");
            System.exit(1);
        }

        int opcion;
        System.out.println("\n" + LINEA);
        System.out.println("       LITERASEARCH");
        System.out.println(LINEA);

        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println(LINEA);
            System.out.println("1. Buscar libro por ID");
            System.out.println("2. Buscar libros por título");
            System.out.println("3. Buscar libros por autor");
            System.out.println("4. Ver historial de búsqueda");
            System.out.println("0. Salir");
            
            try {
                String input = console.readLine("\nSeleccione una opción: ");
                opcion = Integer.parseInt(input);
                System.out.println();

                switch (opcion) {
                    case 1 -> buscarLibroPorId();
                    case 2 -> buscarLibrosPorTitulo();
                    case 3 -> buscarLibrosPorAutor();
                    case 4 -> mostrarHistorialDeBusqueda();
                    case 0 -> {
                        System.out.println("\nGracias por usar LiteraSearch. ¡Hasta pronto!");
                        System.exit(0);
                    }
                    default -> System.out.println("ERROR: Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERROR: Por favor, ingrese un número válido.");
                opcion = -1;
            }

        } while (opcion != 0);
    }

    private void buscarLibroPorId() {
        System.out.println("\nBUSCAR LIBRO POR ID");
        System.out.println(LINEA);
        try {
            String input = console.readLine("Ingrese el ID del libro: ");
            String id = input.trim();
            
            if (id.isEmpty()) {
                System.out.println("\nERROR: El ID no puede estar vacío.");
                return;
            }

            String json = consumoAPI.obtenerDatos("https://gutendex.com/books/?ids=" + id);
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);

            if (respuesta != null && !respuesta.results().isEmpty()) {
                DatosLibros libro = respuesta.results().get(0);
                mostrarDetallesLibro(libro);
            } else {
                System.out.println("\nNo se encontró ningún libro con el ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("\nError al buscar el libro: " + e.getMessage());
        }
    }

    private void buscarLibrosPorTitulo() {
        System.out.println("\nBUSCAR LIBROS POR TÍTULO");
        System.out.println(LINEA);
        try {
            String busqueda = console.readLine("Ingrese el título a buscar: ").trim().toLowerCase();
            
            if (busqueda.isEmpty()) {
                System.out.println("\nERROR: El título no puede estar vacío.");
                return;
            }

            String json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + busqueda.replace(" ", "%20"));
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);

            if (respuesta != null && !respuesta.results().isEmpty()) {
                manejarResultadosDeBusqueda(respuesta.results(), "título");
            } else {
                System.out.println("\nNo se encontraron libros con el título: " + busqueda);
            }
        } catch (Exception e) {
            System.out.println("\nError al buscar libros: " + e.getMessage());
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.println("\nBUSCAR LIBROS POR AUTOR");
        System.out.println(LINEA);
        try {
            String autor = console.readLine("Ingrese el nombre del autor: ").trim();
            
            if (autor.isEmpty()) {
                System.out.println("\nERROR: El nombre del autor no puede estar vacío.");
                return;
            }

            String json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + autor.replace(" ", "%20") + "&sort=popular");
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);

            if (respuesta != null && !respuesta.results().isEmpty()) {
                manejarResultadosDeBusqueda(respuesta.results(), "autor");
            } else {
                System.out.println("\nNo se encontraron libros del autor: " + autor);
            }
        } catch (Exception e) {
            System.out.println("\nError al buscar libros: " + e.getMessage());
        }
    }

    private void manejarResultadosDeBusqueda(List<DatosLibros> libros, String tipoBusqueda) {
        if (libros == null || libros.isEmpty()) {
            System.out.printf("\nNo se encontraron libros con ese %s.%n", tipoBusqueda);
            return;
        }

        System.out.println("\nRESULTADOS DE BÚSQUEDA");
        System.out.println(LINEA);
        System.out.printf("Se encontraron %d libros:%n%n", libros.size());

        for (int i = 0; i < libros.size(); i++) {
            DatosLibros libro = libros.get(i);
            System.out.printf("%d. %s - %s%n", 
                i + 1, 
                libro.title(), 
                libro.getAuthorName() != null ? libro.getAuthorName() : "Autor desconocido");
        }

        int seleccion = -1;
        while (seleccion < 0 || seleccion > libros.size()) {
            try {
                String input = console.readLine("\nSeleccione el número del libro para ver detalles (o 0 para volver): ");
                seleccion = Integer.parseInt(input);
                
                if (seleccion > 0 && seleccion <= libros.size()) {
                    mostrarDetallesLibro(libros.get(seleccion - 1));
                    break;
                } else if (seleccion != 0) {
                    System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private void mostrarDetallesLibro(DatosLibros libro) {
        System.out.println("\nDETALLES DEL LIBRO");
        System.out.println(LINEA);
        System.out.println("Título:   " + libro.title());
        System.out.println("Autor:    " + (libro.getAuthorName() != null ? libro.getAuthorName() : "Desconocido"));
        System.out.println("Idioma:   " + (libro.getFirstLanguage() != null ? libro.getFirstLanguage() : "No especificado"));
        System.out.println("Descargas: " + (libro.downloadCount() > 0 ? String.format("%,d", libro.downloadCount()) : "No disponible"));

        if (libro.subjects() != null && !libro.subjects().isEmpty()) {
            System.out.println("\nTemas:");
            libro.subjects().stream()
                .limit(5)
                .forEach(tema -> System.out.println("- " + tema));
        }

        if (libro.bookshelves() != null && !libro.bookshelves().isEmpty()) {
            System.out.println("\nCategorías:");
            libro.bookshelves().stream()
                .limit(3)
                .forEach(cat -> System.out.println("- " + cat));
        }

        System.out.println(LINEA);

        // Guardar el libro en el historial
        libroService.guardarLibro(libro);
    }

    private void mostrarHistorialDeBusqueda() {
        System.out.println("\nHISTORIAL DE BÚSQUEDA");
        System.out.println(LINEA);
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el historial de búsqueda.");
        } else {
            System.out.println("Libros guardados en el historial:");
            System.out.println(LINEA);
            libros.forEach(libro -> 
                System.out.printf("- %s (%s)%n", 
                    libro.getTitulo(), 
                    (libro.getAutor() != null ? libro.getAutor().getNombre() : "Autor desconocido"))
            );
            System.out.println(LINEA);
        }
        console.readLine("\nPresione Enter para continuar...");
    }
}