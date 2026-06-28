## 🎥 Video demostrativo del proyecto

[Ver video del proyecto](https://drive.google.com/file/d/1un5EfTSzYX4VeTAtjRO4xxrMqc0wL1qU/view?usp=drivesdk)





# 🏥 Sistema de Clínica - Arquitectura de Microservicios con Spring Boot

## 📌 Descripción del Proyecto

Proyecto desarrollado bajo una arquitectura de Microservicios utilizando Spring Boot y Spring Cloud, orientado a la administración integral de una clínica médica.

El sistema implementa una arquitectura distribuida donde cada dominio funcional opera de forma independiente, permitiendo escalabilidad, mantenibilidad y facilidad para futuras integraciones.

El sistema permite administrar:

* Usuarios
* Pacientes
* Médicos
* Exámenes
* Fichas clínicas
* Recetas médicas
* Pagos
* Notificaciones

Además, incluye:

* Eureka Server
* API Gateway
* JWT Authentication
* Feign Client
* MySQL
* Spring Security
* Spring Data JPA
* Comunicación entre microservicios
* Docker
* Swagger/OpenAPI
* Maven Multi-Módulo

---

# 🧱 Arquitectura General

## Microservicios Implementados

| Microservicio      | Función                                 | Puerto | Base de datos     |    
| ------------------ | --------------------------------------- | ------ | ----------------- |
| ms-usuarios        | Gestión de usuarios y autenticación JWT | 8081   | db_usuarios       |
| ms-pacientes       | Gestión de pacientes                    | 8082   | db_pacientes      | 
| ms-medicos         | Gestión de médicos                      | 8083   | db_medicos        |
| ms-farmacia        | Gestión de médicos                      | 8084   | db_farmacia       |
| ms-reservas        | Gestión de médicos                      | 8085   | db_reservas       |
| ms-examenes        | Gestión de exámenes médicos             | 8086   | db_examenes       |
| ms-fichas-clinicas | Gestión de fichas clínicas              | 8087   | db_fichasclinicas |
| ms-recetas         | Gestión de recetas médicas              | 8089   | db_recetas        |
| ms-notificaciones  | Gestión de notificaciones               | 8091   | ----------------- |
| ms-pagos           | Gestión de pagos                        | 8092   | db_pagos          |
| api-gateway        | Punto único de entrada                  | 8094   | ----------------- |
| eureka-server      | Registro y descubrimiento de servicios  | 8761   | ----------------- |

Cada microservicio se registra automáticamente en Eureka y es consumido mediante el API Gateway.

---

# ⚙️ Tecnologías Utilizadas

| Tecnología      | Versión |
| --------------- | ------- |
| Java            | 21      |
| Spring Boot     | 3.5.x   |
| Spring Cloud    | 2025.x  |
| Maven           | 3.9+    |
| MySQL           | 8.x     |
| Spring Security | Sí      |
| JWT             | Sí      |
| Spring Data JPA | Sí      |
| Eureka Server   | Sí      |
| Docker          | Sí      |
| Docker Compose  | Sí      |
| Swagger/OpenAPI | Sí      |
| Lombok          | Sí      |
| OpenFeign       | Sí      |

---

# 📁 Estructura del Proyecto

```txt
proyecto-clinica/
│
├── eureka-server
├── api-gateway
|
├── msusuarios
├── mspacientes
├── msmedicos
├── msfarmacia
├── msreservas
├── msexamenes
├── msfichasclinicas
├── ms-recetas
├── ms-pagos
├── ms-notificaciones
│ 
├── docker-compose.yml 
└── pom.xml
```

---

# Arquitectura de Comunicación
Cliente
    │
    ▼
API Gateway
    │
    ▼
 Eureka
    │
    ├────────► Usuarios
    ├────────► Pacientes
    ├────────► Médicos
    ├────────► Farmacia
    ├────────► Reservas
    ├────────► Exámenes
    ├────────► Fichas
    ├────────► Recetas
    ├────────► Pagos
    └────────► Notificaciones

---

# Seguridad

El sistema implementa autenticación basada en JWT mediante Spring Security.

Características:

* Login seguro
* Tokens JWT
* Roles de usuario
* Protección de endpoints
* Autorización basada en roles

---

# Documentación Swagger

Cada microservicio dispone de documentación OpenAPI.

| Servicio        |	Swagger                               |
| --------------- | ------------------------------------- |
| Usuarios        |	http://localhost:8081/swagger-ui.html |
| Pacientes       |	http://localhost:8082/swagger-ui.html |
| Médicos	        | http://localhost:8083/swagger-ui.html |
| Farmacia	      | http://localhost:8084/swagger-ui.html | 
| Reservas	      | http://localhost:8085/swagger-ui.html |
| Exámenes	      | http://localhost:8086/swagger-ui.html |
| Fichas Clínicas |	http://localhost:8087/swagger-ui.html |
| Recetas	        | http://localhost:8089/swagger-ui.html |
| Pagos	          | http://localhost:8092/swagger-ui.html |

---

# 🚀 Cómo Ejecutar el Proyecto Local

## Requisitos

* Java 21
* Maven 3.9+
* MySQL 8
* Git

Verificar instalación:

```bash
java -version
mvn -version
```

---

## 1️⃣ Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO

cd proyecto-clinica
```

---

# Compilar

```bash
mvn clean install -DskipTests
```

---

## 2️⃣ Abrir el proyecto padre en VS Code

Abrir la carpeta:

```txt
proyecto-clinica-main
```

---

# 🗄️ Configuración de MySQL

## Configuración utilizada

```txt
Puerto MySQL: 3307
```

## Creación Automática de Bases de Datos

Las bases de datos NO necesitan crearse manualmente.

Cada microservicio utiliza:

```yaml
createDatabaseIfNotExist=true
```

Ejemplo:

```yaml
url: jdbc:mysql://localhost:3307/db_notificaciones?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

Esto permite que MySQL cree automáticamente la base de datos al iniciar el microservicio.

⚠️ IMPORTANTE:

MySQL/XAMPP debe estar iniciado antes de levantar los microservicios.

---

# 🖥️ Cómo Abrir los Microservicios en VS Code

Con la implementación de Maven Multi-Módulo, podemos levantar todos los microservicios con la extensión de Spring Boot Dashboard en VS Code

## Recomendación

Abrir la carpeta del proyecto padre:

```txt
Levantar los microservicios siguiendo el orden recomendado de ejecución
```

Ejemplo:

```txt
1.  MySQL (Xampp)
2.  Eureka Server
3.  API Gateway
4.  MS Usuarios
5.  MS Pacientes
6.  MS Médicos
7.  MS Farmacia
8.  MS Reservas
9.  MS Exámenes
10. MS Fichas Clínicas
11. MS Recetas
12. MS Pagos
13. MS Notificaciones
```

---

## Qué debe aparecer

Todos los servicios deben mostrarse con estado:

```txt
UP
```

Ejemplo:

```txt
API Gateway
MS Usuarios
MS Pacientes
MS Médicos
MS Farmacia
MS Reservas
MS Exámenes
MS Fichas Clínicas
MS Recetas
MS Pagos
MS Notificaciones
```

---

# 🔐 Autenticación JWT

La autenticación se realiza mediante:

```txt
msUsuarios
```

---

# 👨‍💻 Crear Usuario ADMIN Manualmente desde MySQL o phpMyAdmin

## ⚠️ IMPORTANTE

Antes de iniciar sesión por primera vez, se recomienda crear el usuario administrador directamente desde MySQL o phpMyAdmin.

Esto evita problemas iniciales de autenticación y permite obtener el primer token JWT.

---

# 🗄️ Opción 1 — Crear ADMIN desde phpMyAdmin

## 1. Abrir phpMyAdmin

```txt
http://localhost/phpmyadmin
```

---

## 2. Abrir base de datos

```txt
ms_usuarios
```

---

## 3. Abrir tabla

```txt
usuarios
```

---

## 4. Ir a la pestaña SQL

---

## 5. Ejecutar el siguiente script

```sql
INSERT INTO usuarios (
    nombre,
    email,
    password,
    rol,
    activo
)
VALUES (
    'Administrador',
    'admin@gmail.com',
    '$2a$10$AvKfNXupTWburBM/tVRcRefoFxgDjrYYLeapPdpXihILzn5Ayj.SC',
    'ROLE_ADMIN',
    1
);
```

---

# 🔐 Credenciales del Usuario ADMIN

```txt
Email: admin@gmail.com
Password: 123456
```

---

# ⚠️ IMPORTANTE

La contraseña utilizada en el INSERT fue generada previamente utilizando:

```txt
PasswordGenerator.java en msUsuarios
```

El proyecto incluye una clase para generar hashes BCrypt automáticamente.

Ejemplo:

```java
package com.clinic.msusuarios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = encoder.encode("123456");

        System.out.println(password);
    }
}
```

Al ejecutar la clase se generará un hash similar a:

```txt
$2a$10$AvKfNXupTWburBM/tVRcRefoFxgDjrYYLeapPdpXihILzn5Ayj.SC
```

Ese hash es el que se utiliza en el INSERT SQL.

⚠️ IMPORTANTE:

La contraseña almacenada en MySQL siempre debe estar encriptada con BCrypt.

NO debe guardarse texto plano en la base de datos.

---

# 🖥️ Opción 2 — Crear ADMIN desde MySQL Workbench o consola SQL

## Ejecutar:

```sql
INSERT INTO usuarios (
    nombre,
    email,
    password,
    rol,
    activo
)
VALUES (
    'Administrador',
    'admin@gmail.com',
    '$2a$10$AvKfNXupTWburBM/tVRcRefoFxgDjrYYLeapPdpXihILzn5Ayj.SC',
    'ROLE_ADMIN',
    1
);
```

---

# 🔑 Obtener Token JWT

## ⚠️ IMPORTANTE ANTES DE PROBAR

Antes de utilizar cualquier endpoint protegido, el usuario debe:

1. Crear un usuario ADMIN
2. Iniciar sesión
3. Obtener el token JWT
4. Utilizar el token en Postman

---

## Endpoint

```http
POST http://localhost:8090/auth/login
```

## Body

```json
{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

## Respuesta esperada

```json
{
  "token": "TOKEN_JWT"
}
```

---

# 🔐 Cómo Utilizar el Token en Postman

Después de obtener el token:

## 1. Copiar el token

```txt
TOKEN_JWT
```

---

## 2. Ir a la pestaña Authorization en Postman

Seleccionar:

```txt
Bearer Token
```

---

## 3. Pegar el token

```txt
TOKEN_JWT
```

---

## 4. Enviar la petición

Esto permitirá acceder a los endpoints protegidos.

---

# 🧪 Cómo Probar el Sistema en Postman

## Recomendación Importante

Crear:

```txt
Una pestaña nueva de Postman para cada microservicio
```

Ejemplo:

```txt
Pestaña 1 → Login JWT
Pestaña 2 → Usuarios
Pestaña 3 → Pacientes
Pestaña 4 → Médicos
Pestaña 5 → Exámenes
Pestaña 6 → Fichas Clínicas
Pestaña 7 → Recetas
Pestaña 8 → Pagos
Pestaña 9 → Notificaciones
```

Esto facilita:

* reutilizar el token
* mantener ordenadas las pruebas
* evitar errores

---

Todas las pruebas se realizan desde:

```txt
API Gateway
```

Puerto:

```txt
8090
```

---

# 👤 Pruebas msUsuarios

## Crear Usuario

### Endpoint

```http
POST http://localhost:8090/api/usuarios
```

### Explicación

Este microservicio permite:

* registrar usuarios
* iniciar sesión
* generar JWT
* administrar roles
* proteger endpoints

### Body

```json
{
  "nombre": "IngreseSuNombreAqui",
  "email": "ingrese_su_correo_aqui@gmail.com",
  "password": "IngreseSuPasswordAqui",
  "rol": "ROLE_ADMIN",
  "activo": true
}
```

### Explicación de Campos

| Campo    | Descripción                 |
| -------- | --------------------------- |
| nombre   | Nombre del usuario          |
| email    | Correo utilizado para login |
| password | Contraseña del usuario      |
| rol      | Rol del sistema             |
| activo   | Estado del usuario          |

### Roles disponibles

```txt
ROLE_ADMIN
ROLE_MEDICO
ROLE_RECEPCIONISTA
```

---

## Listar Usuarios

```http
GET http://localhost:8090/api/usuarios
```

---

# 🧍‍♂️ Pruebas msPacientes

## Crear Paciente

```http
POST http://localhost:8090/api/pacientes
```

### Body

```json
{
  "nombre": "Juan",
  "apellido": "Perez",
  "rut": "11111111-1",
  "telefono": "987654321",
  "email": "juan@gmail.com",
  "direccion": "Santiago",
  "edad": 30,
  "prevision": "Fonasa"
}
```

---

## Listar Pacientes

```http
GET http://localhost:8090/api/pacientes
```

---

# 👨‍⚕️ Pruebas msMedicos

## Crear Médico

```http
POST http://localhost:8090/api/medicos
```

### Body

```json
{
  "nombre": "Carlos",
  "apellido": "Soto",
  "rut": "22222222-2",
  "telefono": "987654321",
  "correo": "carlos@clinic.com",
  "especialidad": "Cardiologia",
  "disponible": true
}
```

---

## Listar Médicos

```http
GET http://localhost:8090/api/medicos
```

---

# 🧪 Pruebas msExamenes

## Crear Examen

```http
POST http://localhost:8090/api/examenes
```

### Body

```json
{
  "pacienteId": 1,
  "tipoExamen": "Examen de Sangre",
  "fecha": "2026-05-17",
  "resultado": "Pendiente",
  "estado": "EN_PROCESO"
}
```

---

## Listar Exámenes

```http
GET http://localhost:8090/api/examenes
```

---

# ⚠️ Orden Correcto para las Pruebas Integradas

Para que las relaciones funcionen correctamente se recomienda seguir este orden:

1. Crear Usuario
2. Crear Paciente
3. Crear Médico
4. Crear Examen
5. Crear Ficha Clínica
6. Crear Receta
7. Crear Pago
8. Crear Notificación

---

# 📋 Pruebas msFichasClinicas

## Crear Ficha Clínica

```http
POST http://localhost:8090/api/fichas
```

### Body

```json
{
  "pacienteId": 1,
  "medicoId": 1,
  "examenId": 1,
  "diagnostico": "Paciente con anemia leve",
  "tratamiento": "Suplementos de hierro",
  "observaciones": "Control en 30 dias",
  "fecha": "2026-05-17"
}
```

---

## Qué hace este microservicio

Valida automáticamente:

* existencia del paciente
* existencia del médico
* existencia del examen

utilizando:

```txt
FeignClient
```

---

# 💊 Pruebas msRecetas

## Crear Receta

```http
POST http://localhost:8090/api/recetas
```

### Body

```json
{
  "pacienteId": 1,
  "medicoId": 1,
  "medicamento": "Paracetamol",
  "dosis": "500mg",
  "indicaciones": "Tomar cada 8 horas",
  "fechaEmision": "2026-05-17",
  "activa": true
}
```

---

## Listar Recetas

```http
GET http://localhost:8090/api/recetas
```

---

# 💳 Pruebas msPagos

## Crear Pago

```http
POST http://localhost:8090/api/pagos
```

### Body

```json
{
  "pacienteId": 1,
  "concepto": "Pago consulta cardiologia",
  "monto": 25000,
  "metodoPago": "TARJETA",
  "estado": "PAGADO",
  "fechaPago": "2026-05-17"
}
```

---

## Listar Pagos

```http
GET http://localhost:8090/api/pagos
```

---

# 🔔 Pruebas msNotificaciones

## Crear Notificación

```http
POST http://localhost:8090/api/notificaciones
```

### Body

```json
{
  "destinatario": "misael@gmail.com",
  "titulo": "Pago registrado",
  "mensaje": "Su pago fue registrado correctamente",
  "tipo": "EMAIL",
  "estado": "ENVIADO"
}
```

---

## Listar Notificaciones

```http
GET http://localhost:8090/api/notificaciones
```

---

# ✅ Verificación Final del Sistema

Si todo funciona correctamente:

* Eureka mostrará todos los servicios en estado UP
* El API Gateway responderá correctamente
* Los CRUD funcionarán desde Postman
* Las bases de datos se crearán automáticamente
* FeignClient validará relaciones entre microservicios
* JWT permitirá autenticación segura

---

```http
GET http://localhost:8090/api/notificaciones
```

---

# 🔄 Comunicación Entre Microservicios

El proyecto utiliza:

```txt
OpenFeign
```

para comunicación interna.

Ejemplo:

```txt
msFichasClinicas
```

consulta:

* msPacientes
* msMedicos
* msExamenes

antes de registrar una ficha clínica.

---

# 🔐 Seguridad

El sistema implementa:

* JWT Authentication
* Spring Security
* Roles
* Protección de endpoints

---

# 📡 API Gateway

Todas las peticiones pasan por:

```txt
http://localhost:8090
```

El Gateway enruta automáticamente hacia cada microservicio.

---

# 🧠 Lo Aprendido en el Proyecto

Durante el desarrollo se implementó:

* Arquitectura de microservicios
* Registro y descubrimiento con Eureka
* API Gateway
* JWT Authentication
* Spring Security
* FeignClient
* DTOs
* Validaciones
* Manejo global de excepciones
* MySQL
* JPA/Hibernate
* CRUD completos
* Comunicación entre servicios
* Arquitectura escalable

---

# ✅ Estado del Proyecto

| Funcionalidad      | Estado  |
| ------------------ | ------- |
| Microservicios     | ✅      |
| Eureka             | ✅      |
| API Gateway        | ✅      |
| Spring Security    | ✅      |
| JWT                | ✅      |
| Swagger            | ✅      |
| Docker             | ✅      |
| Maven Multi-Módulo | ✅      |
| OpenAPI            | ✅      |
| FeignClient        | ✅      |
| MySQL              | ✅      |
| CRUD Completo      | ✅      |

---

# 👨‍💻 Autor

Características Implementadas:

* Arquitectura de Microservicios
* Maven Multi-Módulo
* Descubrimiento de servicios con Eureka
* API Gateway
* Seguridad JWT
* Spring Security
* Persistencia con Spring Data JPA
* Bases de datos independientes
* Swagger/OpenAPI
* Docker
* Docker Compose
* Comunicación entre microservicios
* Documentación REST

---

# 📌 Notas Finales

Este proyecto fue desarrollado con fines académicos y educativos, aplicando buenas prácticas de arquitectura backend moderna mediante microservicios.

Cada integrante participó en el diseño, implementación e integración de distintos microservicios, aplicando principios de arquitectura distribuida, desarrollo colaborativo y buenas prácticas con Spring Boot y Spring Cloud.

Autores del proyecto: Marco Carrasco, Jeannette Figueroa y Misael Rojas.






# Sistema de Gestión Clínica — Arquitectura de Microservicios

## Descripción del Proyecto

Este proyecto corresponde a un sistema de gestión clínica desarrollado mediante arquitectura de microservicios utilizando Spring Boot y Spring Cloud.

El objetivo principal del sistema es digitalizar y centralizar distintos procesos clínicos, permitiendo administrar usuarios, pacientes, médicos, exámenes, fichas clínicas, recetas, pagos y notificaciones de manera modular, escalable y segura.

---

# Arquitectura Implementada

El sistema fue desarrollado utilizando arquitectura de microservicios.

Cada microservicio posee responsabilidad independiente y se comunica mediante Spring Cloud.

---

# Microservicios Implementados

| Microservicio    | Función                                |
| ---------------- | -------------------------------------- |
| msusuarios       | autenticación y gestión de usuarios    |
| mspacientes      | administración de pacientes            |
| msmedicos        | administración de médicos              |
| msexamenes       | administración de exámenes clínicos    |
| msfichasclinicas | administración de fichas clínicas      |
| msrecetas        | administración de recetas médicas      |
| mspagos          | administración de pagos                |
| msnotificaciones | administración de notificaciones       |
| api-gateway      | puerta de entrada del sistema          |
| eureka-server    | registro y descubrimiento de servicios |

---

# Microservicios Complementarios

Actualmente el proyecto también contiene:

* msfarmacia
* msreservas

Estos microservicios quedaron como módulos complementarios/en desarrollo.

---

# Tecnologías Utilizadas

## Backend

* Java 21
* Spring Boot 3.5.x
* Spring Cloud 2025.x
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* FeignClient
* Maven

---

## Base de Datos

* MySQL 8.x
* XAMPP

---

## Herramientas

* VS Code
* Postman
* GitHub
* phpMyAdmin

---

# Arquitectura CSR

Todos los microservicios fueron desarrollados utilizando arquitectura CSR:

* Controller
* Service
* Repository

Además se implementó:

* DTOs
* Bean Validation
* manejo global de excepciones
* logs estructurados
* JWT Authentication
* Spring Security

---

# DTOs

El proyecto utiliza DTOs para separar entidades internas de las respuestas REST.

Ejemplos:

* UsuarioRequestDTO
* UsuarioResponseDTO
* PacienteRequestDTO
* RecetaResponseDTO

Esto permite:

* controlar datos enviados
* evitar exponer entidades directamente
* mejorar seguridad
* mejorar mantenimiento

---

# Bean Validation

Se implementó Bean Validation para validar datos automáticamente antes de procesarlos.

Validaciones utilizadas:

```java
@NotBlank
@NotNull
@Email
@Size
@DecimalMin
```

Ejemplo:

```txt
400 Bad Request
```

cuando un campo obligatorio es enviado vacío.

---

# Manejo Global de Excepciones

El proyecto implementa manejo global de excepciones utilizando:

```java
@ControllerAdvice
```

mediante clases:

```txt
GlobalExceptionHandler
```

Esto permite retornar respuestas HTTP personalizadas y controladas.

---

# Eureka Server

El proyecto utiliza Eureka Server para registro y descubrimiento automático de microservicios.

Cada microservicio se registra automáticamente en Eureka.

---

## URL Eureka

```txt
http://localhost:8761
```

---

# API Gateway

El sistema utiliza API Gateway como punto de entrada único.

Todas las solicitudes del sistema son consumidas mediante el Gateway.

Ejemplo:

```txt
http://localhost:8090
```

El Gateway enruta solicitudes automáticamente hacia cada microservicio.

---

# FeignClient

El proyecto implementa comunicación entre microservicios mediante FeignClient.

Ejemplo real:

```txt
msFichasClinicas valida pacientes, médicos y exámenes antes de registrar información clínica.
```

Esto permite validación distribuida entre microservicios.

---

# JWT Authentication

El sistema implementa autenticación mediante JWT (JSON Web Token).

El microservicio principal encargado de autenticación es:

```txt
msusuarios
```

Spring Security fue implementado principalmente para proteger endpoints críticos y autenticación.

---

# Roles Implementados

| Rol                | Función              |
| ------------------ | -------------------- |
| ROLE_ADMIN         | administración total |
| ROLE_MEDICO        | acceso médico        |
| ROLE_RECEPCIONISTA | gestión operativa    |

---

# PasswordGenerator

El proyecto incluye una clase:

```txt
PasswordGenerator.java
```

utilizada para generar contraseñas encriptadas con BCrypt.

Ejemplo:

```java
package com.clinic.msusuarios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = encoder.encode("123456");

        System.out.println(password);
    }
}
```

Esto se utilizó para crear el primer usuario administrador manualmente desde MySQL/phpMyAdmin.

---

# Configuración MySQL

## Puerto utilizado

```txt
3307
```

---

# Creación Automática de Bases de Datos

Las bases de datos se crean automáticamente mediante:

```yaml
createDatabaseIfNotExist=true
```

Ejemplo:

```yaml
url: jdbc:mysql://localhost:3307/db_notificaciones?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

---

# Puertos Utilizados

| Servicio         | Puerto |
| ---------------- | ------ |
| Eureka Server    | 8761   |
| API Gateway      | 8090   |
| msusuarios       | 8081   |
| mspacientes      | 8082   |
| msmedicos        | 8083   |
| msexamenes       | 8084   |
| msfichasclinicas | 8085   |
| msrecetas        | 8086   |
| mspagos          | 8087   |
| msnotificaciones | 8088   |

---

# Cómo Abrir los Microservicios

Cada microservicio debe ejecutarse de forma independiente.

Se recomienda abrir:

```txt
Cada microservicio en una ventana separada de VS Code
```

Ejemplo:

```txt
VS Code 1 → eureka-server
VS Code 2 → api-gateway
VS Code 3 → msusuarios
VS Code 4 → mspacientes
VS Code 5 → msmedicos
VS Code 6 → msexamenes
VS Code 7 → msfichasclinicas
VS Code 8 → msrecetas
VS Code 9 → mspagos
VS Code 10 → msnotificaciones
```

---

# Orden Correcto de Ejecución

## 1. Iniciar MySQL/XAMPP

---

## 2. Levantar Eureka Server

---

## 3. Levantar API Gateway

---

## 4. Levantar microservicios

Orden recomendado:

```txt
msusuarios
mspacientes
msmedicos
msexamenes
msfichasclinicas
msrecetas
mspagos
msnotificaciones
```

---

# Crear Usuario ADMIN Manualmente

## 1. Abrir phpMyAdmin

```txt
http://localhost/phpmyadmin
```

---

## 2. Abrir base de datos

```txt
ms_usuarios
```

---

## 3. Abrir tabla

```txt
usuarios
```

---

## 4. Ejecutar SQL

```sql
INSERT INTO usuarios (
    nombre,
    email,
    password,
    rol,
    activo
)
VALUES (
    'Administrador',
    'admin@gmail.com',
    '$2a$10$AvKfNXupTWburBM/tVRcRefoFxgDjrYYLeapPdpXihILzn5Ayj.SC',
    'ROLE_ADMIN',
    1
);
```

---

# Credenciales ADMIN

```txt
Email: admin@gmail.com
Password: 123456
```

---

# Obtener Token JWT

## Endpoint

```http
POST /auth/login
```

---

## URL

```txt
http://localhost:8090/auth/login
```

---

## Body

```json
{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

---

## Respuesta esperada

```json
{
  "token": "JWT_AQUI"
}
```

---

# Cómo Utilizar el Token en Postman

## 1. Copiar token JWT

---

## 2. Ir a Authorization

Seleccionar:

```txt
Bearer Token
```

---

## 3. Pegar token

---

## 4. Consumir endpoints protegidos

---

# CRUD msusuarios

## Crear Usuario

```http
POST /api/usuarios
```

### Body

```json
{
  "nombre": "IngreseSuNombreAqui",
  "email": "ingrese_su_correo_aqui@gmail.com",
  "password": "IngreseSuPasswordAqui",
  "rol": "ROLE_ADMIN",
  "activo": true
}
```

---

## Listar Usuarios

```http
GET /api/usuarios
```

---

## Buscar Usuario

```http
GET /api/usuarios/{id}
```

---

## Actualizar Usuario

```http
PUT /api/usuarios/{id}
```

---

## Eliminar Usuario

```http
DELETE /api/usuarios/{id}
```

---

# CRUD mspacientes

| Método | Endpoint            |
| ------ | ------------------- |
| POST   | /api/pacientes      |
| GET    | /api/pacientes      |
| GET    | /api/pacientes/{id} |
| PUT    | /api/pacientes/{id} |
| DELETE | /api/pacientes/{id} |

---

# CRUD msmedicos

| Método | Endpoint          |
| ------ | ----------------- |
| POST   | /api/medicos      |
| GET    | /api/medicos      |
| GET    | /api/medicos/{id} |
| PUT    | /api/medicos/{id} |
| DELETE | /api/medicos/{id} |

---

# CRUD msexamenes

| Método | Endpoint           |
| ------ | ------------------ |
| POST   | /api/examenes      |
| GET    | /api/examenes      |
| GET    | /api/examenes/{id} |
| PUT    | /api/examenes/{id} |
| DELETE | /api/examenes/{id} |

---

# CRUD msfichasclinicas

| Método | Endpoint                  |
| ------ | ------------------------- |
| POST   | /api/fichas-clinicas      |
| GET    | /api/fichas-clinicas      |
| GET    | /api/fichas-clinicas/{id} |
| PUT    | /api/fichas-clinicas/{id} |
| DELETE | /api/fichas-clinicas/{id} |

---

# CRUD msrecetas

| Método | Endpoint          |
| ------ | ----------------- |
| POST   | /api/recetas      |
| GET    | /api/recetas      |
| GET    | /api/recetas/{id} |
| PUT    | /api/recetas/{id} |
| DELETE | /api/recetas/{id} |

---

# CRUD mspagos

| Método | Endpoint        |
| ------ | --------------- |
| POST   | /api/pagos      |
| GET    | /api/pagos      |
| GET    | /api/pagos/{id} |
| PUT    | /api/pagos/{id} |
| DELETE | /api/pagos/{id} |

---

# CRUD msnotificaciones

| Método | Endpoint                 |
| ------ | ------------------------ |
| POST   | /api/notificaciones      |
| GET    | /api/notificaciones      |
| GET    | /api/notificaciones/{id} |
| PUT    | /api/notificaciones/{id} |
| DELETE | /api/notificaciones/{id} |

---

# Flujo General del Sistema

1. El usuario inicia sesión.
2. El sistema genera JWT.
3. El usuario consume endpoints mediante API Gateway.
4. Spring Security valida JWT.
5. Los microservicios procesan solicitudes.
6. FeignClient permite comunicación entre servicios.
7. JPA persiste información en MySQL.

---

# Repositorio GitHub

Agregar aquí el enlace del repositorio:

```txt
https://github.com/Missaldur1/proyecto-clinica.git
```

---

# Estado Actual del Proyecto

Actualmente el proyecto cuenta con:

* arquitectura de microservicios funcional
* autenticación JWT
* CRUD completos
* persistencia con JPA/Hibernate
* Bean Validation
* manejo global de excepciones
* Eureka Server
* API Gateway
* FeignClient
* comunicación distribuida
* logs estructurados


---

# Autores

* Jeannette Figueroa
* Marco Carrasco
* Misael Rojas
