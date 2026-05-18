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

Cada microservicio debe abrirse y ejecutarse de forma independiente.

## Recomendación

Abrir:

```txt
Cada microservicio en una pestaña/ventana separada de VS Code
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

Esto ayuda a:

* evitar errores de puertos
* controlar mejor las terminales
* reducir conflictos
* identificar errores rápidamente

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

La contraseña ya está encriptada con BCrypt.

NO debe modificarse manualmente.

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
  "correo": "juan@gmail.com",
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
