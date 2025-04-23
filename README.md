# 🏥 MonoRepo Panel Transacciones

## 📌 Descripción del Proyecto

Este repositorio contiene dos aplicaciones coordinadas para la gestión de transacciones:

transaction-service: Backend implementado con Spring Boot, expone una API REST para crear, editar, listar y eliminar transacciones.

transaction-frontend: Frontend desarrollado en React/Vite con Shadcn UI y TailwindCSS, que consume los endpoints del backend para ofrecer una interfaz moderna y responsiva.

Cada aplicación se encuentra en su propio directorio y puede ejecutarse de forma independiente o mediante docker-compose para un despliegue orquestado. 

---

## **🚀 Ejecución de los servicios **
Para levantar los servicios, ejecuta el siguiente script en la raíz del repositorio:  

```sh
./run.sh
```

Este script se encarga de:  
✅ Compilar los dos proyectos.  
✅ Levantar los servicios de infraestructura (Redis, PostgreSQL).  
✅ Iniciar los proyectos (transaction-service, transaction-frontend).  

---

## 🛠️ **Requisitos Previos**
Asegúrate de tener instalado en tu máquina:
- **Docker** 
- **Docker Compose**  
- **Java 17** 
- **Maven**  

---

## 🐟 **Swagger - Documentación de APIs**
El  endpoint está documentado en Swagger y accesible desde:

- **Microservicio de Transacciones**  
  👉 [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)  

## Interfaz del lado cliente

- **Frontend**  
  👉 [http://localhost:5173](http://localhost:5173)  

---

## 🔥 **APIs Implementadas**

### 🤝 **Transacciones - CRUD**
| Método | Endpoint | Descripción |
|--------|---------|-------------|
| `GET` | `/transactions/` | Obtener transacciones |
| `GET` | `/transactions/{usuario}` | Obtener transacciones del usuario |
| `POST` | `/transactions` | Crear una transaccion |
| `PUT` | `/transactions` | Actualizar una transaccion  |
| `DELETE` | `/transactions/{transactionId}` | Eliminar transaccion |


---

Puedes verificar los contenedores en ejecución con:  
```bash
docker ps
```

---

😊🚀