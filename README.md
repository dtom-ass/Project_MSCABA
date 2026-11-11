# Project MSCABA: Simulador de Centro de Acopio y Básculas Agroindustriales

Sistema de gestión logística y simulación de centro de acopio agroindustrial desarrollado en **Java (JDK 21+)** con interfaz gráfica moderna construida en **JavaFX**. Implementa de manera estricta estructuras de datos avanzadas, patrones de diseño de software y un modelo matemático estadístico para el control de camiones, básculas y almacenamiento en bodega.

---

## Arquitectura y Estructuras de Datos Implementadas

El proyecto sigue estrictamente el patrón arquitectónico **MVC (Modelo-Vista-Controlador)**:
1. **Cola con Prioridad (`TruckPriorityQueue`):** Organiza la llegada de vehículos a la entrada priorizando de forma automática las mercancías perecederas sobre granos e insumos.
2. **Matriz Bidimensional (`WarehouseMatrix`):** Representa la cuadrícula de almacenamiento de la bodega ($3 \times 3$ muelles de recepción), gestionando estados dinámicos (libres `0` y ocupados `1`).
3. **Pila LIFO (`UndoStack`):** Permite revertir las últimas operaciones de pesaje y asignación, liberando el slot correspondiente en la bodega y devolviendo el camión a la cola prioritaria.
4. **Listas Enlazadas (Simple, Doble y Circular):**
   * **Lista Simple (`ProcessedHistoryList`):** Historial cronológico de camiones procesados con cálculo de peso neto.
   * **Lista Doble (`AuditDoubleList` & `VehicleCatalogList`):** Auditoría bidireccional de tiquetes de báscula y catálogo dinámico de tipos de vehículos (*Sencillo*, *Doble Troque*, *Tractocamión*) con pesos de tara automáticos.
   * **Lista Circular (`InspectorCircularList`):** Rotación equitativa y continua de los inspectores de calidad en turno.
5. **Vectores y Matrices Estadísticas (`CenterStatistics`):** Vectores 1D de tasas de descarga y matrices 2D estadísticas para el cálculo de tiempos estimados de espera y descarga basados en fórmulas algorítmicas.

---

## Requisitos del Sistema y Ejecución

* **Java Development Kit (JDK):** Versión 21 o superior.
* **JavaFX SDK:** Versión 27+ (o compatible con tu entorno).

### Comandos de Compilación y Ejecución (Terminal / CLI)

Compila todas las clases del modelo, vista y controlador apuntando al SDK de JavaFX:

```bash
javac --module-path "C:\javafx-sdk-27.0.1\lib" --add-modules javafx.controls,javafx.fxml -d bin src/model/*.java src/view/*.java src/controller/*.java
java --module-path "C:\javafx-sdk-27.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp bin controller.Main