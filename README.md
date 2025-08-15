## Punto Red Backend

Backend del sistema Punto Red, desarrollado con Java 17 y Spring Boot.
Este proyecto permite la gestión de usuarios, roles, operadores y recargas, incluyendo autenticación segura con JWT y documentación interactiva con Swagger.

🛠 Tecnologías y dependencias

- Java 17
- Spring Boot
- Spring Security con JWT
- Spring Data JPA
- H2 / PostgreSQL
JUnit / pruebas unitarias

## Características principales

- Autenticación y autorización con JWT
- Configuración de CORS y seguridad
- CRUD de operadores y recargas
- Registro y consulta de historial de recargas
- Documentación Swagger
- Pruebas unitarias para servicios y lógica de dominio

---

## 🏗 Arquitectura del proyecto

1. Usuario envía credenciales a `/auth/login`
2. Backend valida usuario y contraseña
3. Se genera JWT firmado
4. JWT se envía en la respuesta
5. En cada solicitud protegida, el token se envía en el header `Authorization: Bearer <token>`
6. Spring Security valida token y permisos antes de permitir acceso a recursos

---

## 🧪 Pruebas

Pruebas unitarias disponibles:

- `RechargeDomainTest` – lógica de dominio de recargas
- `OperatorServiceImplTest` – pruebas del servicio de operadores
- `RechargeServiceImplTest` – pruebas del servicio de recargas
- `PuntoRedApplicationTests` – pruebas de contexto Spring Boot

## Usuario
Usuario inicial para pruebas:

- Usuario: edwin
- Contraseña: Edwin.$12
- API disponible en: http://localhost:8080

## 📚 Documentación Swagger

Interactiva disponible en: http://localhost:8080/swagger-ui.html

**Ejecutar pruebas:**

```bash
./gradlew test


