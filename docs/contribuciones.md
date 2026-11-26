# Contribuciones - SIGAC-UTA

Este examen lo hice de forma individual, así que todos los módulos (M1 a M6), las pruebas y la documentación son míos.

| Campo | Detalle |
|---|---|
| Autor | Romel Fabricio Gamboa Araujo |
| Carrera / nivel | Ingeniería en Software, tercer nivel, FISEI - UTA |
| Repositorio | https://github.com/gamboarommel580-rgb/Prueba-practica-sigac-uta-estructuras-datos |

Armé el sistema por partes: primero las estructuras, luego el modelo, los servicios de cada módulo, la integración, las pruebas, el menú y al final la documentación. Cada commit de código compila por sí solo. Para ver un paso basta con abrir su hash en GitHub.

## Registro de commits

Los hashes salen de `git log --oneline`.

| # | Commit | Módulo | Archivos | Qué hice | Pruebas relacionadas |
|---|---|---|---|---|---|
| 1 |  | M1 | `.gitignore`, `Condicion`, excepciones, `Vector`, `ListaEnlazada` | Vector dinámico y lista simple con cabeza y cola | U-VEC, U-LIS, E-03, E-04, E-21 |
| 2 |  | M2 | `Pila`, `Cola` | Pila LIFO y cola FIFO con nodos | U-PIL, U-COL, E-05, E-06 |
| 3 |  | M3 | `ArbolGeneral`, `ABB` | Árbol general y ABB con los tres casos de eliminación | U-AG, U-ABB, E-17 |
| 4 |  | M4/M5 | `Grafo` | Listas de adyacencia, BFS, DFS y eliminación de aristas entrantes | U-GRA |
| 5 |  | Modelo | `src/modelo/*` | Entidades del dominio |  |
| 6 |  | M1/M3 | `ServicioEstudiantes`, `ServicioAcademico` | CRUD, búsqueda secuencial contra ABB y jerarquía académica | U-M1, U-M3 |
| 7 |  | M2 | `ServicioAtencion` | Cola de solicitudes e historial | U-M2 |
| 8 |  | M4/M5 | `ServicioRedLaboratorio`, `ServicioRedSocial` | Red de equipos y recomendaciones de segundo grado | U-M4, U-M5 |
| 9 |  | M6 | `SistemaSIGAC`, `DemoCasoIntegrador` | Integración de módulos, deshacer y caso integrador | CI-01 a CI-17 |
| 10 |  | Pruebas | `Verificador` y cinco suites | Pruebas unitarias, del caso integrador, normales, extremas y erróneas | U, CI, N, E, ER |
| 11 |  | M6 | `Main`, `EjecutorPruebas`, `PruebasRegresion` | Menú por consola, ejecutor de pruebas y pruebas de regresión | RG-01 a RG-17 |
| 12 |  | Docs | `README.md`, `docs/*` | README, matriz de pruebas generada y capturas |  |

## Preguntas de comprobación individual

| Pregunta | Dónde está en el código | Pruebas |
|---|---|---|
| Eliminación de un nodo del ABB con dos hijos | `ABB.eliminarRec`, caso 3 (sucesor inorden) | U-ABB-06, U-ABB-07, CI-02 a CI-04 |
| Uso de la cola en BFS | `Grafo.bfs` (marca al encolar) | U-GRA-01, CI-09, CI-15 |
| Exclusión de amigos directos en las recomendaciones | `ServicioRedSocial.recomendar`, PASO 2 | CI-12, CI-13, N-05, U-M5-02 |
| Eliminación de aristas entrantes a un vértice | `Grafo.eliminarAristasEntrantes` y `Grafo.eliminarVertice` | U-GRA-04, U-GRA-05, N-08 |
