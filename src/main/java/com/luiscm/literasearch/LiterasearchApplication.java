package com.luiscm.literasearch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luiscm.literasearch.service.AutorService;
import com.luiscm.literasearch.service.LibroService;
import com.luiscm.literasearch.ui.MenuPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class LiterasearchApplication implements CommandLineRunner {

    @Autowired
    private LibroService libroService;
    
    @Autowired
    private AutorService autorService;


    public static void main(String[] args) {
        SpringApplication.run(LiterasearchApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n=== LITERASEARCH ===");
        System.out.println("Explora miles de libros gratuitos del Proyecto Gutenberg.\n");
        
        // Iniciar el menú principal
        MenuPrincipal menu = new MenuPrincipal(libroService, autorService);
        menu.mostrarMenu();
    }
}
