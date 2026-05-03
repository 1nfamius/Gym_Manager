# Sistemas Informáticos (0483) — GymManager

## Contenido de esta carpeta

- `informe_sistemas.docx` — Informe técnico completo del entorno de ejecución

## Resumen del entorno

| Elemento | Decisión |
|----------|----------|
| Tipo de sistema | PC de escritorio en recepción |
| SO recomendado | Ubuntu 22.04 LTS |
| CPU | Intel Core i3 mínimo / i5 recomendado |
| RAM | 4 GB mínimo / 8 GB recomendados |
| Base de datos | PostgreSQL 15 (instalación local) |
| Java | OpenJDK 17 |

## Guía rápida de instalación

```bash
# 1. Instalar Java y PostgreSQL
sudo apt install openjdk-17-jdk postgresql -y

# 2. Crear base de datos
sudo -u postgres psql -c "CREATE DATABASE gymmanager;"
sudo -u postgres psql -c "CREATE USER gymuser WITH PASSWORD 'gym1234';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE gymmanager TO gymuser;"

# 3. Importar esquema y datos
psql -U gymuser -d gymmanager -f ../../sql/schema.sql
psql -U gymuser -d gymmanager -f ../../sql/data.sql

# 4. Ejecutar la aplicación
java -cp .:lib/postgresql-42.7.3.jar Main
```

Ver el informe completo para detalle de usuarios, permisos y plan de mantenimiento.
