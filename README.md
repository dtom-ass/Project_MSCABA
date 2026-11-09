# Project MSCABA: Agro-Industrial Collection Center & Scale Simulator
Modelo de Simulación de Centros de Acopio y Básculas Agroindustriales

---

## English Version

### About the Project
Project MSCABA is an academic software solution developed for a Data Structures course. It simulates an agro-industrial collection center where trucks arrive with agricultural goods, get prioritized, weighed on a scale, and assigned to specific warehouse storage slots.

### Development Note
The code for this project was generated with the assistance of Artificial Intelligence (AI), strictly structured and developed under user specifications, supervision, and domain requirements for third-semester Data Structures.

### Architecture & Design Pattern
The system is built following the MVC (Model-View-Controller) design pattern:
- model: Core domain logic and implementation of linear/non-linear data structures.
- view: Text-based interface (Console CLI) designed for easy transition to JavaFX/CSS.
- controller: Coordinates data flow between view and model options.

### Implemented Data Structures
1. Priority Queue: Handles incoming trucks, prioritizing perishable goods over standard dry goods.
2. 2D Matrix: Represents the warehouse grid layout, tracking free (0) and occupied (1) storage slots.
3. Stack (LIFO): Implements the Undo feature for recent scale weighing operations.
4. Simple Linked List: Maintains the chronological record of processed trucks.
5. Double Linked List: Manages scale tickets for bidirectional audit inspection.
6. Circular Linked List: Handles equitable rotation for quality control inspectors.

---

## Versión en Español

### Sobre el Proyecto
Project MSCABA es una aplicación académica desarrollada para la asignatura de Estructuras de Datos. Simula la operación de un centro de acopio agroindustrial donde los camiones ingresan con productos agrícolas, se organizan por prioridad, pasan por báscula y se asignan a celdas de almacenamiento en bodega.

### Nota de Desarrollo
El código de este proyecto fue generado utilizando herramientas de Inteligencia Artificial (IA), bajo el diseño, especificaciones, supervisión directa y requerimientos del usuario para el nivel universitario de Estructuras de Datos.

### Arquitectura y Patrón de Diseño
La aplicación está construida bajo el patrón de diseño MVC (Modelo-Vista-Controlador):
- model: Contiene la lógica del dominio y la implementación de las estructuras de datos.
- view: Interfaz por consola de comandos (CLI), estructurada para su futura migración a JavaFX/CSS.
- controller: Gestor principal que conecta el menú de usuario con la lógica del modelo.

### Estructuras de Datos Implementadas
1. Cola con Prioridad: Atiende camiones priorizando cargas perecederas sobre granos secos.
2. Matriz 2D: Mapeo cuadricular de la bodega para control de bahías libres (0) u ocupadas (1).
3. Pila (LIFO): Permite revertir o deshacer el último pesaje en báscula.
4. Lista Enlazada Simple: Historial cronológico de atención a camiones.
5. Lista Doble Enlazada: Registro de auditoría de tiquetes navegable hacia adelante y hacia atrás.
6. Lista Circular: Rotación automática de turno entre inspectores de calidad.

---

## How to Run / Cómo Ejecutar

1. Compilar clases / Compile classes:
   javac -d bin src/model/*.java src/view/*.java src/controller/*.java

2. Ejecutar programa / Run application:
   java -cp bin controller.Main