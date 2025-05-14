# User Management API

A Spring Boot RESTful API that allows for secure user registration, retrieval, updating, deletion, and role-based access control (ADMIN / USER).  
Deployed on [Railway](https://user-management-api-production-7709.up.railway.app).

---

## 🧰 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- Maven
- Docker
- Railway (deployment)

---

## 🚀 Features

- Register new users
- Secure password hashing with BCrypt
- Login with basic auth (admin / user access)
- CRUD endpoints for users
- Role-based endpoint protection
- Deployed and accessible via HTTPS

---

## 📦 API Endpoints

All endpoints are protected. Use Basic Auth (`admin:admin` for admin actions).

**Register User**  
`POST /users`  
JSON body:
```json
{
  "username": "newbie",
  "email": "newbie@example.com",
  "password": "123123",
  "role": "USER"
}
---

## 🛠️ How to Run

### 🧪 Run Locally with Maven
```bash
./mvnw spring-boot:run
