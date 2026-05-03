# GymManager

Sistema de gestión integral para gimnasios. Permite administrar socios, membresías, actividades, instructores y reservas desde una aplicación de escritorio en Java con base de datos PostgreSQL.

## ¿Qué problema resuelve?

Muchos gimnasios pequeños gestionan sus socios y reservas en papel o en hojas de cálculo. GymManager centraliza toda esa información en una base de datos relacional, con una interfaz por consola que permite realizar las operaciones del día a día de forma rápida y fiable.

## Tecnologías utilizadas

- **Java 17** — lógica de la aplicación
- **PostgreSQL 15** — base de datos relacional
- **JDBC** (postgresql-42.7.3) — conexión entre Java y PostgreSQL
- **XML / XSD** — exportación e importación de datos estructurados
- **Git / GitHub** — control de versiones

## Funcionalidades de la aplicación

### Gestión de socios
- Listar todos los socios activos
- Buscar un socio por ID
- Dar de alta un nuevo socio
- Modificar datos de un socio existente
- Dar de baja a un socio (baja lógica, no se borra el registro)

### Actividades y reservas
- Ver catálogo de actividades disponibles con instructor asignado
- Consultar reservas confirmadas de un socio
- Realizar una nueva reserva en una sesión (con control de plazas)
- Cancelar una reserva (devuelve la plaza automáticamente)

### Base de datos
- Todas las operaciones usan JDBC con `PreparedStatement`
- Las reservas usan **transacciones** (si falla una operación se hace rollback)
- Baja lógica en socios: campo `activo = false` en lugar de borrar el registro

## Arquitectura del proyecto

El código está organizado en capas siguiendo el patrón MVC:

```
src/
├── app/          → Main.java — punto de entrada y menú principal
├── model/        → Clases de dominio: Socio, Actividad, Reserva
├── service/      → Lógica de negocio y acceso a BD (JDBC): SocioService, ActividadService
├── controller/   → Controladores de menús: SocioController, ReservaController
└── utils/        → Utilidades: DBConnection (Singleton), Consola (input validado)
```

- `model/` — encapsula los datos, sin lógica de negocio
- `service/` — toda la lógica JDBC vive aquí, separada de la presentación
- `controller/` — solo gestiona menús y llama a los services
- `utils/` — reutilizable desde cualquier capa

## Requisitos previos

- Java 17 o superior
- PostgreSQL 15 o superior
- Driver JDBC: `postgresql-42.7.3.jar` (descargar desde https://jdbc.postgresql.org)

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/1nfamius/GymManager.git
cd GymManager
```

### 2. Crear la base de datos en PostgreSQL

```bash
sudo -u postgres psql
```
```sql
CREATE DATABASE gymmanager;
CREATE USER gymuser WITH PASSWORD 'gym1234';
GRANT ALL PRIVILEGES ON DATABASE gymmanager TO gymuser;
\q
```

### 3. Importar el esquema y los datos

```bash
psql -U gymuser -d gymmanager -f sql/schema.sql
psql -U gymuser -d gymmanager -f sql/data.sql
```

### 4. Configurar la conexión

Edita `src/utils/DBConnection.java` y ajusta usuario y contraseña si es necesario:

```java
private static final String URL      = "jdbc:postgresql://localhost:5432/gymmanager";
private static final String USER     = "gymuser";
private static final String PASSWORD = "gym1234";
```

### 5. Compilar

```bash
mkdir -p out
javac -cp src:lib/postgresql-42.7.3.jar -d out $(find src -name "*.java")
```

### 6. Ejecutar

```bash
java -cp out:lib/postgresql-42.7.3.jar app.Main
```

> En Windows sustituye `:` por `;` en el classpath:
> `java -cp "out;lib/postgresql-42.7.3.jar" app.Main`

## Estructura del repositorio

```
GymManager/
├── src/
│   ├── app/Main.java
│   ├── model/         Socio.java · Actividad.java · Reserva.java
│   ├── service/       SocioService.java · ActividadService.java
│   ├── controller/    SocioController.java · ReservaController.java
│   └── utils/         DBConnection.java · Consola.java
├── sql/
│   ├── schema.sql     Creación de tablas (PostgreSQL)
│   ├── data.sql       Datos de ejemplo
│   └── queries.sql    Consultas de la aplicación
├── xml/
│   ├── datos.xml      Exportación de datos del sistema
│   ├── esquema.xsd    Esquema de validación XML
│   └── datos_invalido.xml  Ejemplo de XML que falla la validación
├── docs/
│   ├── diagramas/     Diagrama E/R y modelo relacional
│   ├── sistemas/      Informe técnico de entorno de ejecución
│   └── empleabilidad/ Perfil profesional y presentación del proyecto
├── README.md
└── .gitignore
```

## Módulos del Proyecto Intermodular

| Módulo | Entregables |
|--------|-------------|
| Bases de Datos (0484) | `/sql/` + `/docs/diagramas/` |
| Programación (0485) + MPO | `/src/` |
| Lenguajes de Marcas (0373) | `/xml/` |
| Sistemas Informáticos (0483) | `/docs/sistemas/` |
| Entornos de Desarrollo (0487) | Este repositorio completo |
| Empleabilidad (1709) | `/docs/empleabilidad/` |
