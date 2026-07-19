# Proyecto: Minimarket Plus

Este es el backend del sistema "Minimarket Plus", desarrollado con Spring Boot. 

## Tecnologías utilizadas
* **Spring Boot 3.x**
* **Spring Data JPA** (Persistencia)
* **H2 Database** (Base de datos en memoria)
* **Spring HATEOAS** (Servicios RESTful)
* **Swagger/OpenAPI** (Documentación)

## Cómo ejecutar el proyecto
1. Clona este repositorio.
2. Abre el proyecto en tu IDE favorito (IntelliJ, VS Code, Eclipse).
3. Asegúrate de tener instalado **JDK 17 o superior**.
4. Ejecuta la clase `MinimarketApplication.java`.
5. Accede a la documentación en `http://localhost:8080/swagger-ui.html`.

## Endpoints principales
* **Listar productos:** `GET /api/productos`
* **Guardar producto:** `POST /api/productos`
* **Eliminar producto:** `DELETE /api/productos/{id}`
