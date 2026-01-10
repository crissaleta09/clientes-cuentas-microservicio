# Proyecto BankAccount

Este proyecto es una API REST para la gestión de clientes y cuentas bancarias, desarrollada con Spring Boot y siguiendo un diseño de arquitectura hexagonal.

## Características principales
- Gestión de cuentas bancarias (crear, actualizar, consultar)
- Gestión de clientes
- Persistencia con Spring Data JPA y H2 (base de datos en memoria)

## Estructura del proyecto
```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── bankaccount/
│   │   │       ├── application/         # Lógica de negocio
│   │   │       ├── domain/              # Modelos y repositorios
│   │   │       ├── infrastructure/      # Adaptadores (web, JPA, config)
│   │   │       └── BankAccountApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql                 # Datos de ejemplo
│   └── test/
│       └── java/
│           └── bankaccount/
├── pom.xml
```

## Tecnologías utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Requisitos previos
- Java 17 o superior
- Maven
- IDE recomendado: Spring Tool Suite 4 (STS4), IntelliJ IDEA o VS Code

## Instalación y ejecución
1. Clona el repositorio:
   ```
   git clone https://github.com/crissaleta09/clientes-cuentas-microservicio.git
   cd bankAccount
   ```
2. Compila y ejecuta la aplicación:
   con un IDE (Eclipse) 
   hacer maven clean install para descargar las dependencias
   maven update project, Alt+ F5
   Run as Spring Boot App
3. Accede a la API: `http://localhost:8080/swagger-ui/index.html`

4. Accede a la Base de Datos: `http://localhost:8080/h2-console`

5. Colección de Postman en ./postman
   Puedes importar la colección y las variables globales si quieres probar mediante postman


## Endpoints principales
- `POST /cuentas` - Crear cuenta bancaria
  ```
  {
  "dniCliente": "33333333C",
  "tipoCuenta": "NORMAL",
  "total": 50000
   }
   ```
- `PUT /cuentas/{idCuenta}` - Actualizar solo el saldo de una cuenta
   ```
   {
  "total": 50000
   }
   ```
- `GET /clientes` - Listar clientes
- `GET /clientes/{dni}` - Buscar cliente por DNI
- `GET /clientes/mayores-de-edad` - Filtrar clientes por fecha de nacimiento
- `GET /clientes/con-cuenta-superior-a/{cantidad}`- Filtrar clientes cuyas cuentas hagan la suma mayor de la cantidad especificada.
## Contribución
Pull requests y sugerencias son bienvenidas.

## Licencia
MIT
