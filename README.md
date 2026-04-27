# GymManager

Sistema de gestión integral para gimnasios. Permite administrar socios, membresías, actividades, instructores y reservas desde una aplicación de escritorio en Java con base de datos MySQL.

## ¿Qué problema resuelve?

Muchos gimnasios pequeños gestionan sus socios y reservas en papel o en hojas de cálculo. GymManager centraliza toda esa información en una base de datos relacional, con una interfaz por consola que permite realizar las operaciones del día a día de forma rápida y fiable.

## Tecnologías utilizadas

- **Java 17** — lógica de la aplicación
- **MySQL 8** — base de datos relacional
- **JDBC** — conexión entre Java y MySQL
- **XML / XSD** — exportación e importación de datos estructurados
- **Git / GitHub** — control de versiones

## Estructura del repositorio

```
GymManager/
├── src/
│   ├── Main.java              # Punto de entrada
│   ├── model/                 # Clases de dominio
│   ├── service/               # Lógica de negocio
│   ├── controller/            # Controladores de flujo y menús
│   └── utils/                 # Utilidades 
├── sql/
│
├── xml/
│
├── docs/
│
└── README.md
```

## Funcionalidades

- Alta, baja y modificación de socios
- Gestión de membresías (tipos y fechas de vigencia)
- Catálogo de actividades e instructores
- Sistema de reservas por actividad
- Consulta de socios activos, reservas del día y actividades disponibles
- Exportación de datos a XML

## Requisitos previos

- Java 17 o superior
- MySQL 8.0 o superior
- MySQL Connector/J (JDBC driver)

## Autor

**Iker Bermejo** — 1º DAM · Prometeo by The Power  
[GitHub](https://github.com/1nfamius)