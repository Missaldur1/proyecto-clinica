# 🏥 Sistema de Clínica - Arquitectura de Microservicios con Spring Boot

## 📌 Descripción del Proyecto

Este proyecto corresponde a un sistema backend de gestión clínica desarrollado con arquitectura de microservicios usando Spring Boot.

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
* Comunicación entre microservicios

---

# 🧱 Arquitectura General

## Microservicios Implementados

| Microservicio      | Función                                 |
| ------------------ | --------------------------------------- |
| ms-usuarios        | Gestión de usuarios y autenticación JWT |
| ms-pacientes       | Gestión de pacientes                    |
| ms-medicos         | Gestión de médicos                      |
| ms-examenes        | Gestión de exámenes médicos             |
| ms-fichas-clinicas | Gestión de fichas clínicas              |
| ms-recetas         | Gestión de recetas médicas              |
| ms-pagos           | Gestión de pagos                        |
| ms-notificaciones  | Gestión de notificaciones               |
| api-gateway        | Punto único de entrada                  |
| eureka-server      | Registro y descubrimiento de servicios  |

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
| Eureka          | Sí      |
| OpenFeign       | Sí      |

---

# 📁 Estructura del Proyecto

```txt
proyecto-clinica/
│
├── eureka-server
├── api-gateway
├── msusuarios
├── mspacientes
├── msmedicos
├── msexamenes
├── msfichasclinicas
├── msrecetas
├── mspagos
└── msnotificaciones
```

---

# 🚀 Cómo Ejecutar el Proyecto

## 1️⃣ Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

---

## 2️⃣ Abrir el proyecto en VS Code

Abrir la carpeta:

```txt
proyecto-clinica
```

---

# 🗄️ Configuración de MySQL

## Configuración utilizada

```txt
Puerto MySQL: 3307
```

## Crear las bases de datos

```sql
CREATE DATABASE ms_usuarios;
CREATE DATABASE ms_pacientes;
CREATE DATABASE ms_medicos;
CREATE DATABASE ms_examenes;
CREATE DATABASE ms_fichas_clinicas;
CREATE DATABASE ms_recetas;
CREATE DATABASE ms_pagos;
CREATE DATABASE ms_notificaciones;
```

---

# ▶️ Orden Correcto de Ejecución

Es MUY IMPORTANTE levantar los microservicios en este orden:

## 1. Eureka Server

```bash
mvn spring-boot:run
```

Verificar:

```txt
http://localhost:8761
```

---

## 2. API Gateway

```bash
mvn spring-boot:run
```

Puerto:

```txt
8090
```

---

## 3. msUsuarios

```bash
mvn spring-boot:run
```

---

## 4. msPacientes

```bash
mvn spring-boot:run
```

---

## 5. msMedicos

```bash
mvn spring-boot:run
```

---

## 6. msExamenes

```bash
mvn spring-boot:run
```

---

## 7. msFichasClinicas

```bash
mvn spring-boot:run
```

---

## 8. msRecetas

```bash
mvn spring-boot:run
```

---

## 9. msPagos

```bash
mvn spring-boot:run
```

---

## 🔟 msNotificaciones

```bash
mvn spring-boot:run
```

---

# 🌐 Eureka Server

Eureka permite el registro y descubrimiento automático de microservicios.

## URL

```txt
http://localhost:8761
```

## Qué debe aparecer

Todos los servicios deben mostrarse con estado:

```txt
UP
```

Ejemplo:

```txt
MS-USUARIOS
MS-PACIENTES
MS-MEDICOS
MS-EXAMENES
MS-FICHAS-CLINICAS
MS-RECETAS
MS-PAGOS
MS-NOTIFICACIONES
API-GATEWAY
```

---

# 🔐 Autenticación JWT

La autenticación se realiza mediante:

```txt
msUsuarios
```

---

# 🔑 Obtener Token JWT

## Endpoint

```http
POST http://localhost:8090/auth/login
```

## Body

```json
{
  "email": "misael@gmail.com",
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

# 🧪 Cómo Probar el Sistema en Postman

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
POST /api/usuarios
```

### Body

```json
{
  "nombre": "Misael",
  "email": "misael@gmail.com",
  "password": "123456",
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

# 🧍‍♂️ Pruebas msPacientes

## Crear Paciente

```http
POST /api/pacientes
```

### Body

```json
{
  "nombre": "Juan",
  "apellido": "Perez",
  "rut": "11111111-1",
  "telefono": "987654321",
  "correo": "juan@gmail.com",
  "direccion": "Santiago",
  "edad": 30,
  "prevision": "Fonasa"
}
```

---

## Listar Pacientes

```http
GET /api/pacientes
```

---

# 👨‍⚕️ Pruebas msMedicos

## Crear Médico

```http
POST /api/medicos
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
GET /api/medicos
```

---

# 🧪 Pruebas msExamenes

## Crear Examen

```http
POST /api/examenes
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
GET /api/examenes
```

---

# 📋 Pruebas msFichasClinicas

## Crear Ficha Clínica

```http
POST /api/fichas
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
POST /api/recetas
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
GET /api/recetas
```

---

# 💳 Pruebas msPagos

## Crear Pago

```http
POST /api/pagos
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
GET /api/pagos
```

---

# 🔔 Pruebas msNotificaciones

## Crear Notificación

```http
POST /api/notificaciones
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
GET /api/notificaciones
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

# ✅ Estado Final del Proyecto

| Funcionalidad   | Estado |
| --------------- | ------ |
| Eureka          | ✅      |
| API Gateway     | ✅      |
| JWT             | ✅      |
| Usuarios        | ✅      |
| Pacientes       | ✅      |
| Médicos         | ✅      |
| Exámenes        | ✅      |
| Fichas Clínicas | ✅      |
| Recetas         | ✅      |
| Pagos           | ✅      |
| Notificaciones  | ✅      |
| FeignClient     | ✅      |
| MySQL           | ✅      |
| CRUD Completo   | ✅      |

---

# 👨‍💻 Autor

Proyecto desarrollado utilizando:

* Spring Boot
* Java 21
* Arquitectura de Microservicios
* MySQL
* JWT
* Eureka
* API Gateway

---

# 📌 Notas Finales

Este proyecto fue desarrollado con fines académicos y educativos, aplicando buenas prácticas de arquitectura backend moderna mediante microservicios.
