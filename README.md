# LiteraSearch

Aplicación de línea de comandos para buscar y explorar libros del Proyecto Gutenberg.

## Características

- Búsqueda de libros por ID
- Búsqueda de libros por título
- Búsqueda de libros por autor
- Visualización detallada de la información de los libros
- Interfaz de línea de comandos intuitiva

## Requisitos

- Java 17 o superior
- Maven
- Conexión a Internet para acceder a la API de Gutenberg

## Cómo ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/luiscm17/litera-search.git
   cd litera-search
   ```

2. Compila el proyecto con Maven:

   ```bash
   mvn clean package
   ```

3. Ejecuta la aplicación:

   ```bash
   java -jar target/litera-search-0.0.1-SNAPSHOT.jar
   ```

## Uso

1. Selecciona una opción del menú principal:
   - Buscar libro por ID
   - Buscar libros por título
   - Buscar libros por autor
   - Salir

2. Sigue las instrucciones en pantalla para realizar búsquedas.

## Estructura del proyecto

- `src/main/java/com/luiscm/literasearch/` - Código fuente de la aplicación
  - `model/` - Clases de modelo para los datos
  - `services/` - Lógica de negocio y servicios
  - `ui/` - Interfaz de usuario por línea de comandos
  - `LiterasearchApplication.java` - Clase principal

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

Desarrollado como parte de un proyecto personal para explorar la API de Gutenberg y el desarrollo de aplicaciones Java con Spring Boot.
