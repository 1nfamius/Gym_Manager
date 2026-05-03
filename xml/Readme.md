# Módulo XML/XSD — GymManager

## ¿Qué datos representa el XML?

`datos.xml` es una exportación del estado actual del sistema GymManager.
Contiene tres secciones principales:

- **socios** — datos personales de cada socio junto con su membresía activa
- **actividades** — catálogo de clases disponibles con su instructor asignado
- **reservas** — reservas realizadas por los socios en sesiones concretas

## Cómo se valida

El archivo `esquema.xsd` define las reglas de validación:

| Elemento | Restricción |
|----------|------------|
| `email` | Patrón regex: `usuario@dominio.ext` |
| `telefono` | Exactamente 9 dígitos |
| `aforoMaximo` | Entero entre 1 y 100 |
| `duracionMinutos` | Entero entre 15 y 180 |
| `estado` (membresía) | Enumeración: `activa`, `expirada`, `cancelada` |
| `estado` (reserva) | Enumeración: `confirmada`, `cancelada`, `pendiente` |
| `tipo` (membresía) | Enumeración con los 4 tipos del sistema |
| `version` | Patrón: `N.N` |

### Validación correcta
```
VALIDACIÓN CORRECTA: datos.xml cumple el esquema.xsd
```

### Validación incorrecta (datos_invalido.xml)
El archivo `datos_invalido.xml` contiene 5 errores intencionados para demostrar
que el XSD los detecta:
1. Email sin `@`
2. Teléfono con letras
3. Aforo con valor 0 (mínimo es 1)
4. Estado de membresía no permitido (`pendiente`)
5. Duración de 200 minutos (máximo es 180)

## Cómo encaja en el proyecto

El XML actúa como **capa de exportación** del sistema. Desde la aplicación Java
se pueden volcar los datos de socios, actividades y reservas a este formato para:
- Hacer copias de seguridad legibles
- Compartir datos con otros sistemas
- Generar informes exportables

La integración está prevista en `ActividadService` mediante un método
`exportarXML()` que genera el archivo a partir de los datos de la base de datos.