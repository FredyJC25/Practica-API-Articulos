# Práctica: Integración de API con Aplicación de Escritorio Java

Práctica del curso de Programación II. Consiste en una **API REST** que consulta una tabla de artículos en **Oracle Database** y devuelve los datos en formato **JSON**, y una **aplicación de escritorio en Java** que consume esa API y muestra los artículos en un `JTable`.

## Funcionamiento

```
Aplicación de escritorio (Swing)  →  API REST (Spring Boot)  →  Oracle Database
```

1. El usuario presiona el botón **Cargar artículos**.
2. La aplicación de escritorio hace una petición `GET` a la API con `HttpClient`.
3. La API ejecuta un `SELECT` sobre la tabla `articulo` y responde con un arreglo JSON.
4. La aplicación convierte el JSON con la librería `org.json`: recorre el `JSONArray`, crea un objeto `Articulo` por cada `JSONObject` y agrega una fila al modelo del `JTable`.

La consulta a la API se ejecuta en segundo plano con `SwingWorker`, por lo que la ventana no se congela mientras espera la respuesta. Si la API no está en ejecución o responde con un error, la aplicación muestra un mensaje al usuario.

## Tecnologías

| Componente | Herramienta |
|---|---|
| Base de datos | Oracle AI Database 26ai Free |
| API REST | Spring Boot 4.1.1 (Java 17) con Spring Web, JDBC API y Oracle Driver |
| Aplicación de escritorio | Java 21 con Swing |
| Conversión de JSON | `org.json` 20260814 |
| Gestor de dependencias | Maven |
| Control de versiones | Git y GitHub |

## Estructura del repositorio

```
PracticaArticulos/
├── api/        API REST con Spring Boot
├── sql/        Scripts para crear el usuario y la tabla de artículos
├── src/        Aplicación de escritorio
├── pom.xml     Configuración de Maven de la aplicación de escritorio
└── README.md
```

### API (`api/src/main/java/com/practica/api`)

| Paquete | Clase | Responsabilidad |
|---|---|---|
| `modelo` | `Articulo` | Datos de un artículo que se envían en el JSON |
| `servicio` | `ArticuloServicio` | Ejecuta el `SELECT` con `JdbcTemplate` |
| `controlador` | `ArticuloControlador` | Expone el endpoint `GET /api/articulos` |

### Aplicación de escritorio (`src/main/java/com/practica`)

| Paquete | Clase | Responsabilidad |
|---|---|---|
| `principal` | `Main` | Inicia la aplicación |
| `vista` | `VentanaArticulos` | Ventana con el `JTable` y el botón para cargar los datos |
| `servicio` | `ArticuloServicio` | Consume la API y convierte el JSON con `org.json` |
| `modelo` | `Articulo` | Datos de un artículo |

## Endpoint

`GET http://localhost:8080/api/articulos`

Ejemplo de respuesta:

```json
[
  {
    "id": 1,
    "codigo": "ART001",
    "nombre": "Teclado mecánico",
    "descripcion": "Teclado mecánico con iluminación RGB",
    "precio": 450.00,
    "existencia": 15
  }
]
```

## Instalación y ejecución

### Requisitos

- JDK 21
- Oracle Database con el servicio `FREEPDB1` y SQL Developer
- IntelliJ IDEA

### 1. Crear la base de datos

1. Conectado como `system`, ejecutar `sql/crear_usuario.sql`.
2. Conectado como `practica` (contraseña `practica123`), ejecutar `sql/crear_tablas.sql`.

Para borrar la tabla y volver a empezar, ejecutar `sql/eliminar_tablas.sql`.

### 2. Ejecutar la API

Abrir la carpeta `api` en IntelliJ como proyecto Maven y ejecutar `com.practica.api.ApiApplication`. También se puede ejecutar desde la carpeta `api` con:

```
mvnw spring-boot:run
```

Los datos de conexión a Oracle están en `api/src/main/resources/application.properties`.

### 3. Ejecutar la aplicación de escritorio

Con la API en ejecución, abrir el proyecto en IntelliJ, ejecutar `com.practica.principal.Main` y presionar **Cargar artículos**.

## Flujo de trabajo con Git

- `master` contiene la versión estable del proyecto.
- `develop` es la rama de trabajo.
- Cada parte terminada se integra a `master` mediante un pull request:

| Pull request | Contenido |
|---|---|
| #1 | API REST de artículos |
| #2 | Aplicación de escritorio con `JTable` |
| #3 | Documentación del proyecto |

## Autor

Fredy — [FredyJC25](https://github.com/FredyJC25)   