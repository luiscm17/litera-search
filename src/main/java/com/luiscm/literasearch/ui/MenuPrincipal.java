package com.luiscm.literasearch.ui;

import java.util.Scanner;
import com.luiscm.literasearch.model.DatosLibros;
import com.luiscm.literasearch.services.ConsumoAPI;
import com.luiscm.literasearch.services.IConvierteDatos;

public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final IConvierteDatos conversor = new com.luiscm.literasearch.services.ConvierteDatos();

    public void mostrarMenu() {
        int opcion;
        
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Buscar libro por ID");
            System.out.println("2. Buscar libros por título");
            System.out.println("3. Buscar libros por autor");
            System.out.println("0. Salir");
            System.out.print("\nSeleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                System.out.println(""); // Espacio en blanco para mejor legibilidad
                
                switch (opcion) {
                    case 1:
                        buscarLibroPorId();
                        break;
                    case 2:
                        buscarLibrosPorTitulo();
                        break;
                    case 3:
                        buscarLibrosPorAutor();
                        break;
                    case 0:
                        System.out.println("Gracias por usar LiteraSearch");
                        break;
                    default:
                        System.out.println("ERROR: Opción no válida. Por favor, intente de nuevo.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\nERROR: Por favor, ingrese un número válido.");
                opcion = -1;
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }

    private void buscarLibroPorId() {
        System.out.println("\n=== BUSCAR LIBRO POR ID ===");
        System.out.print("Ingrese el ID del libro: ");
        
        try {
            String id = scanner.nextLine();
            String json = consumoAPI.obtenerDatos("https://gutendex.com/books/?ids=" + id);
            
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);
            
            if (respuesta != null && respuesta.results() != null && !respuesta.results().isEmpty()) {
                DatosLibros libro = respuesta.results().get(0);
                mostrarDetallesLibro(libro);
            } else {
                System.out.println("\nNo se encontró ningún libro con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("\nError al buscar el libro: " + e.getMessage());
        }
    }

    private void buscarLibrosPorTitulo() {
        System.out.println("\n=== BUSCAR LIBROS POR TÍTULO ===");
        System.out.print("Ingrese el título a buscar: ");
        
        try {
            String busqueda = scanner.nextLine().trim().toLowerCase();
            if (busqueda.isEmpty()) {
                System.out.println("\nError: El título no puede estar vacío.");
                return;
            }
            
            // Primero buscamos con una búsqueda amplia
            String url = String.format("https://gutendex.com/books/?search=%s", 
                busqueda.replace(" ", "%20"));
                
            String json = consumoAPI.obtenerDatos(url);
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);
            
            // Filtrar los resultados para incluir solo los que contienen el término de búsqueda en el título
            if (respuesta != null && respuesta.results() != null) {
                var resultadosFiltrados = respuesta.results().stream()
                    .filter(libro -> libro.title().toLowerCase().contains(busqueda))
                    .toList();
                
                // Crear una nueva respuesta con los resultados filtrados
                var respuestaFiltrada = new com.luiscm.literasearch.model.ApiResponse(
                    resultadosFiltrados.size(),
                    respuesta.next(),
                    respuesta.previous(),
                    resultadosFiltrados
                );
                
                mostrarResultadosBusqueda(respuestaFiltrada, "título");
            } else {
                mostrarResultadosBusqueda(respuesta, "título");
            }
        } catch (Exception e) {
            System.out.println("\nError al buscar libros: " + e.getMessage());
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.println("\n=== BUSCAR LIBROS POR AUTOR ===");
        System.out.print("Ingrese el nombre del autor: ");
        
        try {
            String autor = scanner.nextLine().trim();
            if (autor.isEmpty()) {
                System.out.println("\nError: El nombre del autor no puede estar vacío.");
                return;
            }
            
            // Usamos el parámetro 'author' para buscar específicamente por autor
            String url = String.format("https://gutendex.com/books/?author=%s&author_year_start=1000&author_year_end=2000", 
                autor.replace(" ", "%20"));
                
            String json = consumoAPI.obtenerDatos(url);
            var respuesta = conversor.obtenerDatos(json, com.luiscm.literasearch.model.ApiResponse.class);
            
            mostrarResultadosBusqueda(respuesta, "autor");
        } catch (Exception e) {
            System.out.println("\nError al buscar libros: " + e.getMessage());
        }
    }
    
    private void mostrarResultadosBusqueda(com.luiscm.literasearch.model.ApiResponse respuesta, String tipoBusqueda) {
        if (respuesta != null && respuesta.results() != null && !respuesta.results().isEmpty()) {
            int totalResultados = respuesta.results().size();
            System.out.printf("\n=== RESULTADOS DE LA BÚSQUEDA POR %s ===\n", 
                tipoBusqueda.toUpperCase());
                
            for (DatosLibros libro : respuesta.results()) {
                System.out.println("\n" + "-".repeat(60));
                System.out.println("Título: " + libro.title());
                System.out.println("ID: " + libro.id());
                System.out.println("Autor: " + libro.getAuthorName());
                System.out.println("Idioma: " + libro.getFirstLanguage());
                System.out.println("Descargas: " + String.format("%,d", libro.downloadCount()));
                
                // Mostrar temas si existen
                if (libro.subjects() != null && !libro.subjects().isEmpty()) {
                    System.out.println("\nTemas:" + 
                        libro.subjects().stream()
                            .limit(3) // Mostrar máximo 3 temas
                            .map(t -> "\n  - " + t)
                            .collect(java.util.stream.Collectors.joining()));
                }
                
                // Mostrar categorías si existen
                if (libro.bookshelves() != null && !libro.bookshelves().isEmpty()) {
                    System.out.println("\nCategorías:" + 
                        libro.bookshelves().stream()
                            .limit(3) // Mostrar máximo 3 categorías
                            .map(c -> "\n  - " + c)
                            .collect(java.util.stream.Collectors.joining()));
                }
                
                System.out.println("-".repeat(60));
            }
            
            // Mostrar el total de resultados encontrados
            System.out.printf("\nTotal de resultados encontrados: %d\n", totalResultados);
            System.out.println("=".repeat(60));
        } else {
            System.out.printf("\nNo se encontraron libros con ese %s.%n", tipoBusqueda);
        }
    }

    private void mostrarDetallesLibro(DatosLibros libro) {
        System.out.println("\n=== DETALLES DEL LIBRO ===");
        System.out.printf("Título: %s\n", libro.title());
        System.out.printf("Autor: %s\n", libro.getAuthorName());
        System.out.printf("Idioma: %s\n", libro.getFirstLanguage());
        System.out.printf("Descargas: %,d\n", libro.downloadCount());
        
        if (libro.subjects() != null && !libro.subjects().isEmpty()) {
            System.out.println("\nTemas:");
            libro.subjects().forEach(tema -> System.out.println("  • " + tema));
        }
        
        if (libro.bookshelves() != null && !libro.bookshelves().isEmpty()) {
            System.out.println("\nCategorías:");
            libro.bookshelves().forEach(cat -> System.out.println("  • " + cat));
        }
    }
}
