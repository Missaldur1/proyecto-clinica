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
* Farmacia
* Reservas
* Exámenes
* Fichas Clínicas
* Recetas Médicas
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
├── README.md
└── pom.xml
```

---

# Arquitectura de Comunicación

```txt
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
```

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

```bash
mvn -version
```

---

## 1️⃣ Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

```bash
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

# 🖥️ Cómo Abrir los Microservicios en VS Code

Con la implementación de Maven Multi-Módulo, podemos levantar todos los microservicios con la extensión de Spring Boot Dashboard en VS Code

## Recomendación

Abrir la carpeta del proyecto padre:

```txt
Levantar los microservicios siguiendo el orden recomendado de ejecución:
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