# QuickLibrary07-C — Sistema de Gestión de Biblioteca

## Descripción del proyecto

**QuickLibrary07-C** es una aplicación de escritorio desarrollada en **Java** para gestionar libros y solicitudes de préstamo dentro de una biblioteca. El sistema permite registrar, buscar, modificar, eliminar, prestar, devolver y listar libros mediante una interfaz gráfica creada con **Java Swing**.

El proyecto fue desarrollado como trabajo final del curso de **Algoritmos y Estructuras de Datos**, por lo que integra estructuras propias como un **árbol binario de búsqueda** para almacenar y organizar los libros, y una **cola enlazada** para administrar las solicitudes de préstamo en orden de llegada.

La aplicación no utiliza una base de datos externa. La información se mantiene durante la ejecución del programa, por lo que al cerrar el sistema los datos ingresados se pierden. Para facilitar las pruebas, el sistema incluye una opción que carga automáticamente **30 libros de prueba**.

---

## Objetivo del sistema

El objetivo principal del sistema es brindar una herramienta sencilla para administrar una biblioteca, aplicando estructuras de datos vistas en clase. A través del programa, el usuario puede gestionar libros y solicitudes de préstamo de manera ordenada, visual y práctica.

---

## Funcionalidades principales

El sistema permite realizar las siguientes operaciones:

- Registrar nuevos libros.
- Buscar libros por código, título, autor o categoría.
- Modificar los datos de un libro registrado.
- Eliminar libros existentes.
- Mostrar todos los libros registrados.
- Mostrar únicamente libros disponibles.
- Mostrar únicamente libros prestados.
- Registrar solicitudes de préstamo.
- Consultar la primera solicitud pendiente.
- Procesar solicitudes de préstamo en orden de llegada.
- Eliminar la solicitud actual de la cola.
- Registrar devoluciones de libros.
- Generar reportes de totales en pantalla.
- Generar reportes en formato PDF.
- Ejecutar una prueba funcional integrada.
- Cargar 30 libros de prueba.
- Mostrar un manual interno desde la aplicación.

---

## Tecnologías utilizadas

- **Lenguaje:** Java
- **Interfaz gráfica:** Java Swing
- **Paradigma:** Programación Orientada a Objetos
- **Estructuras de datos:** Árbol binario de búsqueda y cola enlazada
- **Generación de reportes:** PDF generado desde el propio código Java
- **IDE recomendado:** IntelliJ IDEA, NetBeans, Eclipse o Visual Studio Code

---

## Requisitos para ejecutar el programa

Para ejecutar correctamente el sistema se necesita lo siguiente:

- Tener instalado **Java JDK 17** o una versión superior.
- Contar con un sistema operativo compatible, como Windows, Linux o macOS.
- Utilizar un IDE compatible con Java, como IntelliJ IDEA, NetBeans, Eclipse o Visual Studio Code.
- Tener configurada correctamente la variable de entorno de Java si se ejecutará desde terminal.

El sistema no requiere conexión a internet ni una base de datos externa.

---

## Estructura del proyecto

La estructura principal del proyecto es la siguiente:

```text
QuickLibrary07-C/
│
├── README.md
├── wa2.iml
├── src/
│   │
│   ├── Main.java
│   │
│   ├── Controller/
│   │   └── GestorBiblioteca.java
│   │
│   ├── Model/
│   │   ├── Libro.java
│   │   ├── Solicitud.java
│   │   └── Estado.java
│   │
│   ├── View/
│   │   ├── VentanaBiblioteca.java
│   │   ├── Menu_consola.java
│   │   └── GeneradorPDF.java
│   │
│   ├── estructuras_auxiliares/
│   │   ├── ArbolBinarioBusqueda.java
│   │   ├── NodoArbol.java
│   │   ├── Queue.java
│   │   ├── QueueLink.java
│   │   └── Nodo.java
│   │
│   ├── Exceptions/
│   │   └── ExceptionIsEmpty.java
│   │
│   ├── TestArbol.java
│   ├── TestArbolLibro.java
│   └── PruebaArbolLibro.java
│
└── out/
    └── production/
        └── wa2/
```

---

## Descripción de carpetas y clases principales

### `Main.java`

Es la clase principal del proyecto. Se encarga de iniciar la aplicación gráfica mediante `SwingUtilities` y abrir la ventana principal `VentanaBiblioteca`.

### `Controller/GestorBiblioteca.java`

Contiene la lógica principal del sistema. Esta clase administra los libros y las solicitudes de préstamo. Utiliza un árbol binario de búsqueda para registrar, buscar, eliminar y listar libros. También utiliza una cola enlazada para gestionar las solicitudes pendientes.

### `Model/Libro.java`

Representa la entidad libro. Contiene datos como código, título, autor, categoría, año de publicación y estado. El código del libro se utiliza como identificador principal para ordenar y buscar dentro del árbol.

### `Model/Solicitud.java`

Representa una solicitud de préstamo realizada por un estudiante. Guarda el código del estudiante, nombre del estudiante, código del libro solicitado y fecha de solicitud.

### `Model/Estado.java`

Define los estados posibles de un libro: `DISPONIBLE` y `PRESTADO`.

### `View/VentanaBiblioteca.java`

Contiene la interfaz gráfica principal del sistema. Está desarrollada con Java Swing y organiza las funciones en pestañas: **Libros**, **Préstamos**, **Reportes y prueba**, y **Manual**.

### `View/Menu_consola.java`

Contiene una versión de menú por consola. En el proyecto actual, la ejecución principal abre la interfaz gráfica mediante `VentanaBiblioteca`.

### `View/GeneradorPDF.java`

Permite generar reportes en formato PDF sin depender de librerías externas. Recibe el contenido del reporte y lo guarda en un archivo seleccionado por el usuario.

### `estructuras_auxiliares/ArbolBinarioBusqueda.java`

Implementa el árbol binario de búsqueda utilizado para almacenar los libros. Incluye operaciones como insertar, buscar, eliminar, contar y recorrer en inorden.

### `estructuras_auxiliares/QueueLink.java`

Implementa una cola enlazada utilizada para gestionar las solicitudes de préstamo. Permite agregar solicitudes al final de la cola, consultar la primera solicitud y eliminar solicitudes atendidas.

### `Exceptions/ExceptionIsEmpty.java`

Clase de excepción personalizada utilizada cuando se intenta operar sobre una cola vacía.

---

## Instalación del proyecto

Para instalar y preparar el proyecto, se deben seguir estos pasos:

1. Descargar o clonar el repositorio del proyecto.
2. Descomprimir la carpeta si el proyecto se encuentra en formato `.zip`.
3. Abrir la carpeta del proyecto en un IDE compatible con Java.
4. Verificar que el proyecto esté configurado con **Java JDK 17** o superior.
5. Ubicar la clase `Main.java` dentro de la carpeta `src`.
6. Ejecutar la clase `Main.java`.

---

## Ejecución desde un IDE

La forma recomendada de ejecutar el sistema es desde un entorno de desarrollo.

### Pasos

1. Abrir el proyecto en IntelliJ IDEA, NetBeans, Eclipse o Visual Studio Code.
2. Verificar que el JDK esté configurado correctamente.
3. Abrir el archivo:

```text
src/Main.java
```

4. Ejecutar el método `main`.
5. Al iniciar el programa, se mostrará la ventana principal con el título:

```text
Sistema de Gestión de Biblioteca
```

---

## Ejecución desde terminal

También se puede compilar y ejecutar el proyecto desde terminal.

### En Windows

Abrir una terminal en la carpeta principal del proyecto y ejecutar:

```bash
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out Main
```

### En Linux o macOS

Abrir una terminal en la carpeta principal del proyecto y ejecutar:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

Después de ejecutar estos comandos, se abrirá la interfaz gráfica del sistema.

---

## Uso del sistema

Al iniciar el programa, se muestra una ventana principal dividida en pestañas. Cada pestaña agrupa funciones relacionadas con una parte del sistema.

Las pestañas disponibles son:

- **Libros**
- **Préstamos**
- **Reportes y prueba**
- **Manual**

También existe una barra superior con las opciones:

- **Archivo**
- **Ayuda**

---

## Pestaña Libros

La pestaña **Libros** permite administrar los libros registrados en el sistema.

Desde esta sección se puede:

- Registrar un libro.
- Buscar un libro por código.
- Buscar libros por título, autor o categoría.
- Modificar datos de un libro.
- Eliminar un libro.
- Mostrar todos los libros.
- Mostrar libros disponibles.
- Mostrar libros prestados.
- Cargar 30 libros de prueba.

### Datos solicitados para registrar un libro

Para registrar un libro, el usuario debe ingresar:

- Código del libro.
- Título.
- Autor.
- Categoría.
- Año de publicación.
- Estado del libro.

Los estados disponibles son:

```text
DISPONIBLE
PRESTADO
```

---

## Pestaña Préstamos

La pestaña **Préstamos** permite gestionar las solicitudes de préstamo realizadas por los estudiantes.

Desde esta sección se puede:

- Registrar una solicitud de préstamo.
- Consultar la primera solicitud pendiente.
- Procesar la primera solicitud pendiente.
- Eliminar la solicitud actual de la cola.
- Registrar la devolución de un libro.

### Datos solicitados para registrar una solicitud

Para registrar una solicitud de préstamo, se deben ingresar los siguientes datos:

- Código del estudiante.
- Nombre del estudiante.
- Código del libro solicitado.

Las solicitudes se almacenan en una cola, por lo que se atienden en el mismo orden en que fueron registradas.

---

## Pestaña Reportes y prueba

La pestaña **Reportes y prueba** permite revisar el estado general del sistema y generar evidencias de funcionamiento.

Desde esta sección se puede:

- Generar un reporte PDF.
- Ver el reporte general en pantalla.
- Ejecutar una prueba funcional integrada.
- Limpiar el área de resultados.

El reporte general muestra:

- Cantidad total de libros registrados.
- Cantidad de libros disponibles.
- Cantidad de libros prestados.
- Cantidad de solicitudes pendientes en cola.

---

## Pestaña Manual

La pestaña **Manual** muestra una guía rápida de uso dentro de la misma aplicación. Esta sección sirve como apoyo para recordar las funciones principales del sistema.

También se puede acceder al manual desde el menú superior:

```text
Ayuda > Ver manual
```

---

## Ejemplos de uso

### Ejemplo 1: cargar datos de prueba

1. Ingresar a la pestaña **Libros**.
2. Presionar el botón **Cargar 30 libros de prueba**.
3. Presionar el botón **Todos**.
4. El sistema mostrará la lista de libros cargados.

### Ejemplo 2: registrar un libro

Ingresar los siguientes datos en el formulario de libros:

```text
Código: 200
Título: Fundamentos de Java
Autor: Juan Perez
Categoría: Programacion
Año: 2023
Estado: DISPONIBLE
```

Luego presionar el botón **Registrar libro**. Si los datos son correctos, el sistema mostrará un mensaje de confirmación.

### Ejemplo 3: buscar un libro por código

1. Ingresar a la pestaña **Libros**.
2. Escribir el código del libro en la sección de búsqueda por código.
3. Por ejemplo, ingresar:

```text
101
```

4. Presionar el botón **Buscar**.
5. El sistema mostrará los datos del libro encontrado.

### Ejemplo 4: registrar una solicitud de préstamo

1. Ingresar a la pestaña **Préstamos**.
2. Completar los siguientes datos:

```text
Código estudiante: 1
Nombre estudiante: Estudiante Prueba
Código libro: 101
```

3. Presionar el botón **Registrar solicitud**.
4. La solicitud quedará guardada en la cola de espera.

### Ejemplo 5: procesar una solicitud de préstamo

1. Registrar primero una solicitud de préstamo.
2. Presionar el botón **Consultar primera solicitud pendiente** para revisar la solicitud.
3. Presionar el botón **Procesar primera solicitud pendiente**.
4. Si el libro existe y está disponible, el sistema cambiará su estado a `PRESTADO`.

### Ejemplo 6: registrar una devolución

1. Ingresar a la pestaña **Préstamos**.
2. Escribir el código del libro devuelto.
3. Por ejemplo:

```text
101
```

4. Presionar el botón **Registrar devolución**.
5. El sistema cambiará el estado del libro a `DISPONIBLE`.

### Ejemplo 7: generar reporte en pantalla

1. Ingresar a la pestaña **Reportes y prueba**.
2. Presionar el botón **Ver reporte en pantalla**.
3. El sistema mostrará los totales actuales de libros y solicitudes.

### Ejemplo 8: generar reporte PDF

1. Ingresar a la pestaña **Reportes y prueba**.
2. Presionar el botón **Generar reporte PDF**.
3. Seleccionar la ubicación donde se guardará el archivo.
4. Confirmar la generación del reporte.
5. El sistema creará un archivo PDF con el resumen del sistema.

---

## Validaciones implementadas

El sistema incluye validaciones para evitar errores durante el uso.

Las principales validaciones son:

- El código del libro debe ser un número entero positivo.
- El código del estudiante debe ser un número entero positivo.
- El título del libro no puede estar vacío.
- El autor solo debe contener letras y espacios.
- La categoría solo debe contener letras y espacios.
- El nombre del estudiante solo debe contener letras y espacios.
- El año de publicación debe estar dentro de un rango válido.
- No se permite registrar dos libros con el mismo código.
- No se puede procesar una solicitud si la cola está vacía.
- No se puede prestar un libro que no existe.
- No se puede prestar un libro que ya se encuentra en estado `PRESTADO`.

---

## Estructuras de datos aplicadas

### Árbol binario de búsqueda

El árbol binario de búsqueda se utiliza para almacenar los libros registrados. Cada libro se ordena según su código. Esta estructura permite realizar operaciones como inserción, búsqueda, eliminación y recorrido inorden.

En el sistema, el árbol permite:

- Registrar libros.
- Buscar libros por código.
- Eliminar libros.
- Mostrar los libros ordenados.
- Contar la cantidad total de libros.
- Filtrar libros por estado.

### Cola enlazada

La cola enlazada se utiliza para administrar las solicitudes de préstamo. Su funcionamiento se basa en el principio FIFO, es decir, la primera solicitud que ingresa es la primera en ser atendida.

En el sistema, la cola permite:

- Registrar solicitudes al final de la cola.
- Consultar la primera solicitud pendiente.
- Procesar la primera solicitud pendiente.
- Eliminar solicitudes atendidas.
- Contar solicitudes pendientes.

---

## Capturas recomendadas para el README

Se recomienda crear una carpeta llamada `docs/capturas` para guardar las imágenes del funcionamiento del sistema.

```text
docs/
└── capturas/
    ├── 01_ventana_principal.png
    ├── 02_pestana_libros.png
    ├── 03_carga_libros_prueba.png
    ├── 04_busqueda_libro.png
    ├── 05_modificacion_libro.png
    ├── 06_eliminacion_libro.png
    ├── 07_pestana_prestamos.png
    ├── 08_procesar_solicitud.png
    ├── 09_registrar_devolucion.png
    ├── 10_reporte_pantalla.png
    ├── 11_reporte_pdf.png
    └── 12_prueba_funcional.png
```

### Captura de la ventana principal

Colocar una imagen de la ventana inicial del sistema.

```markdown
![Ventana principal](docs/capturas/01_ventana_principal.png)
```

### Captura de la pestaña Libros

Colocar una imagen donde se visualice el formulario de registro de libros.

```markdown
![Pestaña Libros](docs/capturas/02_pestana_libros.png)
```

### Captura de carga de libros de prueba

Colocar una imagen donde se evidencie la carga de los 30 libros de prueba.

```markdown
![Carga de libros de prueba](docs/capturas/03_carga_libros_prueba.png)
```

### Captura de búsqueda de libro

Colocar una imagen donde se muestre una búsqueda por código, título, autor o categoría.

```markdown
![Búsqueda de libro](docs/capturas/04_busqueda_libro.png)
```

### Captura de modificación de libro

Colocar una imagen donde se muestre un libro cargado para modificar o el mensaje de confirmación.

```markdown
![Modificación de libro](docs/capturas/05_modificacion_libro.png)
```

### Captura de eliminación de libro

Colocar una imagen donde se muestre la confirmación o resultado de eliminar un libro.

```markdown
![Eliminación de libro](docs/capturas/06_eliminacion_libro.png)
```

### Captura de la pestaña Préstamos

Colocar una imagen de la pestaña donde se registran solicitudes de préstamo.

```markdown
![Pestaña Préstamos](docs/capturas/07_pestana_prestamos.png)
```

### Captura del procesamiento de solicitud

Colocar una imagen donde se observe el resultado de procesar una solicitud pendiente.

```markdown
![Procesamiento de solicitud](docs/capturas/08_procesar_solicitud.png)
```

### Captura de devolución

Colocar una imagen donde se observe el registro de devolución de un libro.

```markdown
![Registro de devolución](docs/capturas/09_registrar_devolucion.png)
```

### Captura del reporte en pantalla

Colocar una imagen donde se visualicen los totales del sistema.

```markdown
![Reporte en pantalla](docs/capturas/10_reporte_pantalla.png)
```

### Captura del reporte PDF

Colocar una imagen del archivo PDF generado o del mensaje de confirmación.

```markdown
![Reporte PDF](docs/capturas/11_reporte_pdf.png)
```

### Captura de la prueba funcional

Colocar una imagen donde se muestre el resultado de la prueba funcional integrada.

```markdown
![Prueba funcional integrada](docs/capturas/12_prueba_funcional.png)
```

---

## Prueba funcional integrada

El sistema cuenta con una prueba funcional integrada dentro de la pestaña **Reportes y prueba**. Esta opción ejecuta automáticamente una secuencia de operaciones para comprobar el funcionamiento general del programa.

La prueba realiza las siguientes acciones:

1. Carga los libros de prueba.
2. Muestra los libros registrados.
3. Registra una solicitud para el libro con código `101`.
4. Consulta la primera solicitud pendiente.
5. Procesa la solicitud de préstamo.
6. Verifica el estado del libro `101`.
7. Registra la devolución del libro.
8. Genera un reporte final.

Esta función sirve como evidencia del correcto funcionamiento del sistema.

---

## Consideraciones importantes

- El sistema no utiliza base de datos.
- Los datos se almacenan temporalmente durante la ejecución.
- Al cerrar el programa, la información registrada se pierde.
- Para realizar pruebas rápidas, se recomienda usar la opción **Cargar 30 libros de prueba**.
- La ejecución principal del sistema se realiza desde la clase `Main.java`.
- La interfaz principal corresponde a la clase `VentanaBiblioteca.java`.
- El reporte PDF se guarda en la ubicación seleccionada por el usuario.

---

## Posibles mejoras futuras

Como mejoras futuras para el sistema, se podrían considerar las siguientes:

- Implementar una base de datos para guardar la información permanentemente.
- Agregar inicio de sesión para usuarios administradores y bibliotecarios.
- Registrar historial de préstamos por estudiante.
- Permitir búsqueda parcial de libros por coincidencias.
- Exportar reportes en otros formatos, como Excel o CSV.
- Mejorar el diseño visual de la interfaz gráfica.
- Agregar control de fechas de préstamo y devolución.
- Incluir multas o alertas por devolución tardía.

---

## Autor

Proyecto desarrollado como trabajo final del curso de **Algoritmos y Estructuras de Datos**.


**- Cruz Torres Angel Fernando
- Espino Veas Karla Daniela
- Ferro Huanchi Alvaro Jeanpiero
- Montenegro Gonzales Orlando Jose
- Montoya Magallanes Alvaro Fabrizio**


---

## Estado del proyecto

El proyecto se encuentra funcional y permite realizar las operaciones principales de gestión de biblioteca mediante interfaz gráfica.

