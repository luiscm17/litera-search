# 📚 LiteraSearch

[![Java](https://img.shields.io/badge/Java-21+-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-6DB33F.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Aplicación de línea de comandos desarrollada en Java con Spring Boot para explorar y gestionar libros del Proyecto Gutenberg. Permite buscar libros por diferentes criterios y muestra información detallada de las obras.

## ✨ Características

- 🔍 Búsqueda de libros por ID, título o autor
- 📊 Estadísticas de libros por idioma
- 👥 Búsqueda de autores vivos en un año específico
- 💾 Historial de búsquedas local
- 📱 Interfaz de línea de comandos intuitiva
- 🚀 Despliegue sencillo con Maven

## 🛠️ Requisitos

- Java 21 o superior (LTS)
- Maven 3.6.3 o superior
- MySQL 8.0 o superior (opcional, configuración en memoria disponible)
- Conexión a Internet para acceder a la API de Gutenberg

## 🚀 Instalación

1. **Clona el repositorio**

   ```bash
   git clone https://github.com/luiscm17/litera-search.git
   cd litera-search
   ```

2. **Configura la base de datos** (opcional)
   - Crea una base de datos MySQL llamada `literasearch_db`
   - Actualiza las credenciales en `src/main/resources/application.properties` si es necesario

3. **Compila el proyecto**

   ```bash
   mvn clean package
   ```

4. **Ejecuta la aplicación**

   ```bash
   java -jar target/litera-search-0.0.1-SNAPSHOT.jar
   ```

## 🎮 Uso

Al iniciar la aplicación, se mostrará un menú con las siguientes opciones:

1. **Buscar libro por ID** - Busca un libro específico por su identificador único
2. **Buscar libros por título** - Encuentra libros que coincidan con un término de búsqueda
3. **Buscar libros por autor** - Localiza libros escritos por un autor específico
4. **Buscar autores vivos en un año** - Encuentra autores que estaban vivos en un año determinado
5. **Ver historial de búsqueda** - Muestra un registro de búsquedas anteriores
6. **Ver estadísticas de libros** - Muestra estadísticas sobre los libros disponibles
0. **Salir** - Cierra la aplicación

## 🏗️ Estructura del proyecto

```yml
src/main/java/com/luiscm/literasearch/
├── LiterasearchApplication.java  # Punto de entrada de la aplicación
├── config/                      # Configuraciones de la aplicación
├── model/                       # Entidades del dominio
│   ├── Autor.java
│   ├── Libro.java
│   └── dto/                     # Objetos de transferencia de datos
├── repository/                  # Repositorios de acceso a datos
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── service/                     # Lógica de negocio
│   ├── AutorService.java
│   ├── LibroService.java
│   └── api/                     # Integración con APIs externas
└── ui/                          # Interfaz de usuario
    └── MenuPrincipal.java
```

## 📊 Características Técnicas

- **Arquitectura**: Aplicación Spring Boot con patrón MVC
- **Persistencia**: JPA/Hibernate con MySQL
- **Procesamiento**: Streams de Java para manipulación de datos
- **Manejo de errores**: Excepciones personalizadas y mensajes de error amigables
- **Logging**: Configuración detallada para desarrollo y producción

## 📧 Contacto

Si tienes preguntas o sugerencias, no dudes en abrir un issue o contactar al desarrollador.

---

Desarrollado con ❤️ por [LuisCM](https://github.com/luiscm17) - Explorando el mundo de la literatura digital 📖
