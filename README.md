# Proyecto Clínica - Sistema de Microservicios

Sistema backend desarrollado con arquitectura de microservicios para la gestión de procesos clínicos. El proyecto permite administrar usuarios, pacientes, médicos, medicamentos, reservas, exámenes, fichas clínicas, recetas, pagos y notificaciones.

El sistema fue desarrollado con Java 21, Spring Boot, Spring Cloud, Eureka Server, API Gateway, JWT, MySQL, Swagger/OpenAPI, Maven, Postman y Docker.

---

## Componentes de distribución y defensa técnica

| Componente               | Descripción                                                                                                                        | Enlace                                        |
| ------------------------ | ---------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- |
| Versión Nativa           | Archivo `.zip` con carpeta `apps/`, archivos `.jar` compilados y script `arrancar-nativo.bat` para ejecutar el sistema sin Docker. | [Descargar ZIP Nativo]([PEGAR_LINK_ZIP_NATIVO](https://drive.google.com/file/d/1vETnuGLEFPcSEd__yVj0gXanpdAHbQ6x/view?usp=drive_link)) |
| Versión Docker           | Archivo `.zip` con `docker-compose.yml`, `.env`, carpeta `apps/`, scripts y configuración para ejecutar el sistema con Docker.     | [Descargar ZIP Docker]([PEGAR_LINK_ZIP_DOCKER](https://drive.google.com/file/d/11CRka9-k_E09m1_Ox6V2C9RRJ-jzpDCX/view?usp=drive_link)) |
| Video de Defensa Técnica | Video explicativo del proyecto, funcionamiento, pruebas, ejecución nativa, Docker y aporte personal.                               | [Ver Video](PEGAR_LINK_VIDEO)                 |
| Subtítulos / Guion       | Archivo de apoyo para el video de defensa técnica.                                                                                 | `subtitulos-video.txt`                        |

Duración recomendada del video: **15 minutos ideal**, con un máximo de **18 minutos**.

---

## 1. Objetivo del proyecto

El objetivo del Proyecto Clínica es construir un sistema backend distribuido mediante microservicios, capaz de gestionar los procesos principales de una clínica.

El sistema permite:

1. Administrar usuarios del sistema.
2. Autenticar usuarios mediante JWT.
3. Gestionar pacientes.
4. Gestionar médicos.
5. Administrar medicamentos.
6. Crear y administrar reservas médicas.
7. Registrar y consultar exámenes.
8. Crear fichas clínicas.
9. Administrar recetas médicas.
10. Registrar pagos.
11. Administrar notificaciones.
12. Centralizar el acceso mediante API Gateway.
13. Registrar y descubrir servicios mediante Eureka Server.

---

## 2. Arquitectura general

El sistema utiliza una arquitectura basada en microservicios. Cada microservicio tiene una responsabilidad específica, su propio puerto y su propia configuración de base de datos.

```text
Cliente externo / Postman / Navegador
        |
        v
API Gateway :8094
        |
        +--> msusuarios          :8081  -> db_usuarios
        +--> mspacientes         :8082  -> db_pacientes
        +--> msmedicos           :8083  -> db_medicos
        +--> msfarmacia          :8084  -> db_farmacia
        +--> msreservas          :8085  -> db_reservas
        +--> msexamenes          :8086  -> db_examenes
        +--> msfichasclinicas    :8087  -> db_fichas_clinicas
        +--> ms-recetas          :8089  -> db_recetas
        +--> ms-notificaciones   :8091  -> db_notificaciones
        +--> ms-pagos            :8092  -> db_pagos

Eureka Server :8761
```

Flujo general:

1. El cliente realiza una petición HTTP.
2. La petición llega al API Gateway.
3. Si la ruta está protegida, se valida el token JWT.
4. El Gateway redirige la solicitud al microservicio correspondiente.
5. El microservicio procesa la lógica de negocio.
6. Si corresponde, se comunica con otros microservicios mediante Feign Client.
7. El microservicio consulta o guarda información en MySQL.
8. Se devuelve la respuesta al cliente.

---

## 3. Tecnologías utilizadas

| Tecnología              | Uso                                    |
| ----------------------- | -------------------------------------- |
| Java 21                 | Lenguaje principal del backend         |
| Spring Boot 3.5.x       | Framework principal                    |
| Spring Cloud 2025.x     | Gateway, Eureka y Feign                |
| Eureka Server           | Registro y descubrimiento de servicios |
| Spring Cloud Gateway    | Punto único de entrada                 |
| Spring Security         | Seguridad de endpoints                 |
| JWT                     | Autenticación y autorización           |
| Spring Data JPA         | Persistencia                           |
| MySQL 8                 | Base de datos                          |
| Maven                   | Compilación y gestión de dependencias  |
| Lombok                  | Reducción de código repetitivo         |
| Jakarta Validation      | Validación de DTOs                     |
| Swagger/OpenAPI         | Documentación de endpoints             |
| Postman                 | Pruebas HTTP                           |
| Docker / Docker Compose | Ejecución contenerizada                |
| Git / GitHub            | Control de versiones                   |

---

## 4. Microservicios del sistema

| Módulo              | Puerto | Responsabilidad                                 |
| ------------------- | -----: | ----------------------------------------------- |
| `eureka-server`     |   8761 | Registro y descubrimiento de microservicios     |
| `api-gateway`       |   8094 | Punto único de entrada a las APIs               |
| `msusuarios`        |   8081 | Gestión de usuarios, roles, autenticación y JWT |
| `mspacientes`       |   8082 | Administración de pacientes                     |
| `msmedicos`         |   8083 | Administración de médicos                       |
| `msfarmacia`        |   8084 | Administración de medicamentos                  |
| `msreservas`        |   8085 | Administración de reservas médicas              |
| `msexamenes`        |   8086 | Administración de exámenes médicos              |
| `msfichasclinicas`  |   8087 | Administración de fichas clínicas               |
| `ms-recetas`        |   8089 | Administración de recetas médicas               |
| `ms-notificaciones` |   8091 | Administración de notificaciones                |
| `ms-pagos`          |   8092 | Administración de pagos                         |

---

## 5. Bases de datos

El proyecto utiliza MySQL. Cada microservicio trabaja con una base de datos independiente o separada lógicamente.

| Microservicio       | Base de datos        | Tabla principal esperada |
| ------------------- | -------------------- | ------------------------ |
| `msusuarios`        | `db_usuarios`        | `usuarios`               |
| `mspacientes`       | `db_pacientes`       | `pacientes`              |
| `msmedicos`         | `db_medicos`         | `medicos`                |
| `msfarmacia`        | `db_farmacia`        | `medicamentos`           |
| `msreservas`        | `db_reservas`        | `reservas`               |
| `msexamenes`        | `db_examenes`        | `examenes`               |
| `msfichasclinicas`  | `db_fichas_clinicas` | `fichas_clinicas`        |
| `ms-recetas`        | `db_recetas`         | `recetas`                |
| `ms-notificaciones` | `db_notificaciones`  | `notificaciones`         |
| `ms-pagos`          | `db_pagos`           | `pagos`                  |

---

## 6. Ejecución nativa del sistema

La versión nativa permite ejecutar el sistema sin Docker, usando Java 21 instalado en el equipo y MySQL local mediante XAMPP.

### Requisitos

* Java 21.
* Maven.
* MySQL 8.
* XAMPP con MySQL activo.
* Puertos disponibles.
* ZIP nativo descargado.

### Puerto MySQL usado en entorno local

```text
3307
```

Si el equipo usa MySQL en el puerto `3306`, se debe ajustar la configuración de los microservicios.

---

## 7. Ejecución nativa mediante script .bat

La versión nativa incluye el archivo:

```text
arrancar-nativo.bat
```

Este script inicia el sistema respetando el orden jerárquico requerido:

1. Eureka Server.
2. Microservicios de negocio.
3. API Gateway.

Este orden es importante porque los microservicios necesitan registrarse en Eureka antes de que el Gateway pueda redirigir las solicitudes correctamente.

### Pasos para ejecutar la versión nativa

1. Descargar el ZIP nativo desde el enlace indicado al inicio del README.
2. Descomprimir el archivo.
3. Verificar que exista la carpeta `apps/`.
4. Verificar que dentro de `apps/` estén los archivos `.jar`.
5. Abrir XAMPP.
6. Activar MySQL.
7. Ejecutar el archivo:

```text
arrancar-nativo.bat
```

8. Esperar a que se levanten los servicios.
9. Abrir Eureka:

```text
http://localhost:8761
```

10. Verificar que los microservicios aparezcan registrados.
11. Probar el Gateway:

```text
http://localhost:8094
```

---

## 8. Orden de ejecución nativa manual

Si no se utiliza el script `.bat`, el sistema debe ejecutarse manualmente en este orden:

| Orden | Servicio            | Puerto |
| ----: | ------------------- | -----: |
|     1 | `eureka-server`     |   8761 |
|     2 | `msusuarios`        |   8081 |
|     3 | `mspacientes`       |   8082 |
|     4 | `msmedicos`         |   8083 |
|     5 | `msfarmacia`        |   8084 |
|     6 | `msreservas`        |   8085 |
|     7 | `msexamenes`        |   8086 |
|     8 | `msfichasclinicas`  |   8087 |
|     9 | `ms-recetas`        |   8089 |
|    10 | `ms-notificaciones` |   8091 |
|    11 | `ms-pagos`          |   8092 |
|    12 | `api-gateway`       |   8094 |

Ejemplo de ejecución manual:

```bash
cd eureka-server
mvn spring-boot:run
```

---

## 9. Compilación del proyecto con Maven

Desde la raíz del proyecto:

```bash
mvn clean install
```

Este comando:

1. Limpia compilaciones anteriores.
2. Compila todos los módulos.
3. Ejecuta las pruebas configuradas.
4. Genera los artefactos del proyecto.

Para ejecutar solo las pruebas:

```bash
mvn clean test
```

Para generar los `.jar`:

```bash
mvn clean package
```

En caso de que las pruebas ya hayan sido validadas previamente y solo se necesite empaquetar rápidamente:

```bash
mvn clean package -DskipTests
```

---

## 10. Pruebas unitarias

El proyecto incluye una suite de pruebas unitarias desarrolladas con:

* JUnit 5.
* Mockito.
* MockMvc para pruebas de controllers cuando corresponde.

Estas pruebas permiten validar:

1. Lógica de servicios.
2. Comportamiento de controllers.
3. Respuestas HTTP esperadas.
4. Simulación de dependencias mediante Mockito.
5. Correcta compilación del proyecto multimódulo.

Comando principal para validar el proyecto completo:

```bash
mvn clean install
```

Resultado esperado:

```text
BUILD SUCCESS
```

---

## 11. Eureka Server

La consola de Eureka está disponible en:

```text
http://localhost:8761
```

Cuando el sistema está levantado correctamente, deben aparecer registrados los servicios:

```text
API-GATEWAY
MS-USUARIOS
MS-PACIENTES
MS-MEDICOS
MS-FARMACIA
MS-RESERVAS
MS-EXAMENES
MS-FICHAS-CLINICAS
MS-RECETAS
MS-NOTIFICACIONES
MS-PAGOS
```

Si un servicio no aparece en Eureka, se debe revisar:

1. Que el microservicio esté ejecutándose.
2. Que no haya fallado al iniciar.
3. Que el puerto esté disponible.
4. Que MySQL esté activo.
5. Que la URL de Eureka esté correctamente configurada.

---

## 12. API Gateway

El API Gateway permite consumir los microservicios desde un único puerto:

```text
http://localhost:8094
```

Rutas principales:

| Recurso         | URL                                        |
| --------------- | ------------------------------------------ |
| Login           | `http://localhost:8094/auth/login`         |
| Usuarios        | `http://localhost:8094/api/usuarios`       |
| Pacientes       | `http://localhost:8094/api/pacientes`      |
| Médicos         | `http://localhost:8094/api/medicos`        |
| Medicamentos    | `http://localhost:8094/api/medicamentos`   |
| Reservas        | `http://localhost:8094/api/reservas`       |
| Exámenes        | `http://localhost:8094/api/examenes`       |
| Fichas clínicas | `http://localhost:8094/api/fichas`         |
| Recetas         | `http://localhost:8094/api/recetas`        |
| Notificaciones  | `http://localhost:8094/api/notificaciones` |
| Pagos           | `http://localhost:8094/api/pagos`          |

---

## 13. Seguridad JWT

El proyecto utiliza JWT para proteger endpoints.

Flujo general:

1. El usuario realiza login.
2. El sistema valida email y contraseña.
3. Si las credenciales son correctas, se genera un token JWT.
4. El token se envía en las siguientes peticiones usando Bearer Token.

Endpoint de login:

```http
POST http://localhost:8094/auth/login
Content-Type: application/json
```

Body de ejemplo:

```json
{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

Respuesta esperada:

```text
200 OK
```

Las rutas protegidas deben incluir:

```http
Authorization: Bearer TOKEN_GENERADO
```

Códigos esperados:

| Caso                 | Código HTTP |
| -------------------- | ----------: |
| Login correcto       |         200 |
| Login incorrecto     |         401 |
| Acceso sin token     |         401 |
| Token inválido       |         401 |
| Usuario sin permisos |         403 |

---

## 14. Documentación de endpoints con Swagger / OpenAPI

El proyecto utiliza Swagger/OpenAPI para documentar los endpoints REST de los microservicios.

Swagger permite revisar:

1. Endpoints disponibles.
2. Métodos HTTP.
3. Parámetros.
4. Body esperado.
5. Respuestas esperadas.
6. Códigos HTTP documentados.

Swagger por microservicio:

| Microservicio       | Swagger                                 |
| ------------------- | --------------------------------------- |
| `msusuarios`        | `http://localhost:8081/swagger-ui.html` |
| `mspacientes`       | `http://localhost:8082/swagger-ui.html` |
| `msmedicos`         | `http://localhost:8083/swagger-ui.html` |
| `msfarmacia`        | `http://localhost:8084/swagger-ui.html` |
| `msreservas`        | `http://localhost:8085/swagger-ui.html` |
| `msexamenes`        | `http://localhost:8086/swagger-ui.html` |
| `msfichasclinicas`  | `http://localhost:8087/swagger-ui.html` |
| `ms-recetas`        | `http://localhost:8089/swagger-ui.html` |
| `ms-notificaciones` | `http://localhost:8091/swagger-ui.html` |
| `ms-pagos`          | `http://localhost:8092/swagger-ui.html` |

---

## 15. Arquitectura de capas

Cada microservicio sigue una arquitectura por capas. La estructura puede variar levemente según el microservicio, pero la organización principal es:

```text
controller
service
service.impl
repository
model
dto
mapper
exception
client
config
```

### Controller

La capa Controller recibe las peticiones HTTP.

Responsabilidades:

* Definir endpoints.
* Recibir parámetros y body.
* Aplicar `@Valid`.
* Llamar al Service.
* Retornar `ResponseEntity`.

### Service

La capa Service contiene la lógica de negocio.

Responsabilidades:

* Validar reglas del sistema.
* Coordinar operaciones.
* Llamar al Repository.
* Invocar Feign Clients si corresponde.
* Usar Mapper para convertir datos.
* Lanzar excepciones controladas.

### Repository

La capa Repository se comunica con la base de datos usando Spring Data JPA.

Responsabilidades:

* Guardar entidades.
* Buscar por ID.
* Listar registros.
* Eliminar registros.
* Definir consultas personalizadas cuando sea necesario.

### Model

La capa Model representa las entidades persistentes del sistema.

Responsabilidades:

* Definir la tabla.
* Definir los atributos.
* Usar anotaciones JPA como `@Entity`, `@Id` y `@GeneratedValue`.

### DTO

Los DTOs se usan para transferir datos entre el cliente y el backend.

Tipos comunes:

```text
Request DTO
Response DTO
```

Ventajas:

* Evitan exponer directamente las entidades.
* Permiten validar datos de entrada.
* Controlan qué información se recibe y se devuelve.
* Mejoran la seguridad y mantenibilidad.

### Mapper

El Mapper convierte entre DTOs y entidades.

Ejemplo:

```text
PacienteRequest -> Paciente
Paciente -> PacienteResponse
```

### Exception

La capa Exception permite manejar errores de forma controlada.

Ejemplos:

```text
PacienteNoEncontradoException
FichaClinicaNoEncontradaException
RecursoNoEncontradoException
ForbiddenException
GlobalExceptionHandler
```

### Client

La capa Client se usa para comunicación entre microservicios mediante Feign Client.

Ejemplos:

```text
PacienteClient
MedicoClient
ExamenClient
```

---

## 16. Comunicación entre microservicios

El proyecto utiliza OpenFeign para que algunos microservicios puedan validar información en otros servicios.

| Servicio origen    | Servicio destino | Objetivo                                          |
| ------------------ | ---------------- | ------------------------------------------------- |
| `msfichasclinicas` | `mspacientes`    | Validar que el paciente exista                    |
| `msfichasclinicas` | `msmedicos`      | Validar que el médico exista                      |
| `msfichasclinicas` | `msexamenes`     | Validar que el examen exista                      |
| `msreservas`       | `mspacientes`    | Validar que el paciente exista                    |
| `msreservas`       | `msmedicos`      | Validar que el médico exista                      |
| `ms-recetas`       | `mspacientes`    | Asociar o validar paciente según regla de negocio |
| `ms-recetas`       | `msmedicos`      | Asociar o validar médico según regla de negocio   |
| `ms-pagos`         | `mspacientes`    | Asociar pago a paciente según regla de negocio    |

Ejemplo conceptual de Feign Client:

```java
@FeignClient(name = "MS-PACIENTES")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    PacienteResponse obtenerPacientePorId(@PathVariable Long id);
}
```

---

## 17. Manejo de errores

Cada microservicio maneja errores de forma controlada mediante excepciones personalizadas y/o `@RestControllerAdvice`.

Códigos HTTP esperados:

| Código | Caso                                                |
| -----: | --------------------------------------------------- |
|    200 | Operación exitosa                                   |
|    201 | Recurso creado                                      |
|    204 | Recurso eliminado sin contenido                     |
|    400 | Datos inválidos o JSON mal formado                  |
|    401 | Sin autenticación o token inválido                  |
|    403 | Usuario autenticado sin permisos                    |
|    404 | Recurso no encontrado                               |
|    409 | Conflicto de negocio, por ejemplo reserva duplicada |
|    500 | Error interno no controlado                         |
|    503 | Servicio remoto no disponible                       |

Ejemplo de respuesta de error:

```json
{
  "mensaje": "Paciente no encontrado con ID 999999",
  "status": 404
}
```

---

## 18. Logs

Los microservicios incorporan o pueden incorporar logs mediante Lombok:

```java
@Slf4j
```

Ejemplos de uso:

```java
log.info("Creando paciente");
log.warn("Paciente no encontrado con ID {}", id);
log.error("Error al comunicarse con otro microservicio", ex);
```

Uso recomendado:

| Nivel       | Uso                                                        |
| ----------- | ---------------------------------------------------------- |
| `log.info`  | Operaciones normales como crear, listar o actualizar       |
| `log.warn`  | Casos esperados pero anormales, como recurso no encontrado |
| `log.error` | Errores inesperados o fallas de comunicación               |

---

## 19. Pruebas con Postman

El proyecto cuenta con pruebas HTTP mediante Postman.

Se recomienda usar:

```text
base_url = http://localhost:8094
```

Flujo recomendado:

1. Importar la colección Postman.
2. Importar el environment local.
3. Seleccionar el environment.
4. Ejecutar login.
5. Verificar que se guarde el token.
6. Ejecutar las pruebas CRUD.
7. Ejecutar pruebas de errores.
8. Ejecutar Collection Runner si corresponde.

Pruebas mínimas recomendadas:

| Prueba                     | Resultado esperado |
| -------------------------- | -----------------: |
| Login correcto             |                200 |
| Login incorrecto           |                401 |
| Acceso sin token           |                401 |
| Token inválido             |                401 |
| Listar recursos            |                200 |
| Crear recurso válido       |                201 |
| Crear recurso inválido     |                400 |
| Buscar recurso inexistente |                404 |
| Eliminar recurso           |                204 |
| Reserva duplicada          |                409 |

---

## 20. Versión Docker

Además de la ejecución nativa, el proyecto incluye una versión Docker distribuida en un ZIP externo.

La versión Docker contiene:

* `docker-compose.yml`.
* `.env`.
* Carpeta `apps/` con los archivos `.jar`.
* Configuración de MySQL en contenedor.
* Eureka Server.
* API Gateway.
* Microservicios del sistema.
* Scripts auxiliares de ejecución o detención si corresponde.

### Comando principal

Desde la carpeta donde se encuentra el archivo `docker-compose.yml`:

```bash
docker compose up -d
```

### Verificar contenedores

```bash
docker compose ps
```

### Ver logs

```bash
docker compose logs msusuarios --tail=100
```

### Detener el sistema

```bash
docker compose down
```

### URLs principales en Docker

| Servicio    | URL                     |
| ----------- | ----------------------- |
| Eureka      | `http://localhost:8761` |
| API Gateway | `http://localhost:8094` |

Notas importantes para Docker:

* En Docker, los microservicios no deben usar `localhost` para comunicarse entre contenedores.
* Para MySQL se usa el nombre del servicio: `mysql-db:3306`.
* Para Eureka se usa el nombre del servicio: `eureka-server:8761`.
* Los contenedores Java deben usar Java 21, ya que los `.jar` fueron compilados con Java 21.

---

## 21. Video de defensa técnica

El proyecto incluye un video de defensa técnica con duración ideal de 15 minutos y máximo de 18 minutos.

El video debe explicar:

1. Arquitectura general del sistema.
2. Eureka Server.
3. API Gateway.
4. Microservicios implementados.
5. Seguridad JWT.
6. Ejecución nativa.
7. Ejecución con Docker.
8. Pruebas con Postman.
9. Documentación Swagger/OpenAPI.
10. Pruebas unitarias con JUnit 5 y Mockito.
11. Aporte personal de los integrantes.

Además, se incluye el archivo:

```text
subtitulos-video.txt
```

Este archivo contiene los subtítulos o guion textual utilizado para apoyar la defensa técnica.

---

## 22. Estado actual del proyecto

| Elemento                  | Estado                                    |
| ------------------------- | ----------------------------------------- |
| Proyecto padre Maven      | Implementado                              |
| Eureka Server             | Implementado                              |
| API Gateway               | Implementado                              |
| `msusuarios`              | Implementado                              |
| `mspacientes`             | Implementado                              |
| `msmedicos`               | Implementado                              |
| `msfarmacia`              | Implementado                              |
| `msreservas`              | Implementado                              |
| `msexamenes`              | Implementado                              |
| `msfichasclinicas`        | Implementado                              |
| `ms-recetas`              | Implementado                              |
| `ms-notificaciones`       | Implementado                              |
| `ms-pagos`                | Implementado                              |
| JWT                       | Implementado                              |
| Swagger por microservicio | Implementado                              |
| Feign Client              | Implementado                              |
| Manejo de errores         | Implementado                              |
| Validaciones              | Implementado                              |
| Logs                      | Implementado                              |
| Pruebas Postman           | Implementado                              |
| Pruebas unitarias         | Implementado                              |
| Ejecución nativa          | Implementado                              |
| Docker                    | Implementado y distribuido en ZIP externo |

---

## 23. Comandos útiles finales

### Verificar versión de Java

```bash
java -version
```

Debe mostrar Java 21.

### Verificar Maven

```bash
mvn -v
```

### Compilar todo

```bash
mvn clean install
```

### Ejecutar pruebas

```bash
mvn clean test
```

### Levantar un microservicio

```bash
cd NOMBRE_MICROSERVICIO
mvn spring-boot:run
```

### Abrir Eureka

```text
http://localhost:8761
```

### Probar Gateway

```text
http://localhost:8094
```

---

## 24. Próximas mejoras sugeridas

* Mejorar cobertura de pruebas unitarias.
* Agregar más pruebas con MockMvc.
* Mejorar logs por microservicio.
* Estandarizar respuestas de error.
* Centralizar documentación Swagger si se requiere.
* Revisar seguridad por roles en todos los microservicios.
* Agregar monitoreo o Actuator en una futura versión.
* Mejorar documentación técnica por cada microservicio.
