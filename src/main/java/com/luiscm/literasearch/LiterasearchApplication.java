package com.luiscm.literasearch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luiscm.literasearch.ui.MenuPrincipal;

@SpringBootApplication
public class LiterasearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LiterasearchApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n=== LITERASEARCH ===");
        System.out.println("Explora miles de libros gratuitos del Proyecto Gutenberg.\n");
        
        // Iniciar el menú principal
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrarMenu();
    }
}
