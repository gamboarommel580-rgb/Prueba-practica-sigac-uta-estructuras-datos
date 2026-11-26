# Matriz de pruebas - SIGAC-UTA

Este archivo lo genera el ejecutor de pruebas (`java -cp bin pruebas.EjecutorPruebas --matriz docs/matriz-pruebas.md`), así el resultado obtenido es el real de la última ejecución y no algo escrito a mano.

Total: 166/166 aprobadas.

| ID | Módulo | Tipo | Entrada | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|---|
| U-VEC-01 | M1 | Unitaria | agregar 1,2,3 | [1, 2, 3] | [1, 2, 3] | Pasó |
| U-VEC-02 | M1 | Unitaria | insertarEn(0, 0) sobre [1,2] | [0, 1, 2] | [0, 1, 2] | Pasó |
| U-VEC-03 | M1 | Unitaria | eliminarEn(1) (medio) sobre [1,2,3] | 2 [1, 3] | 2 [1, 3] | Pasó |
| U-VEC-04 | M1 | Unitaria | eliminarEn(0) cabeza y eliminarEn(ultimo) cola sobre [1,2,3,4] | [2, 3] | [2, 3] | Pasó |
| U-VEC-05 | M1 | Unitaria | agregar 10 elementos con capacidad inicial 2 | 10 16 | 10 16 | Pasó |
| U-VEC-06 | M1 | Unitaria | indiceDe(x == 3) en [1,2,3] | 2 | 2 | Pasó |
| U-LIS-01 | M1 | Unitaria | agregarAlFinal 2,3 y agregarAlInicio 1 | [1, 2, 3] | [1, 2, 3] | Pasó |
| U-LIS-02 | M1 | Unitaria | eliminar cabeza de [1,2,3] | [2, 3] primero=2 | [2, 3] primero=2 | Pasó |
| U-LIS-03 | M1 | Unitaria | eliminar medio de [1,2,3] | [1, 3] | [1, 3] | Pasó |
| U-LIS-04 | M1 | Unitaria | eliminar cola de [1,2,3] y luego agregarAlFinal 9 | [1, 2, 9] ultimo=9 | [1, 2, 9] ultimo=9 | Pasó |
| U-LIS-05 | M1 | Unitaria | insertarEn(1, 5) sobre [1,2] | [1, 5, 2] | [1, 5, 2] | Pasó |
| U-LIS-06 | M1 | Unitaria | eliminarPrimero(x == 2) en [1,2,3,2] | 2 [1, 3, 2] | 2 [1, 3, 2] | Pasó |
| U-PIL-01 | M2 | Unitaria | apilar A,B,C y desapilar 3 veces | C B A vacia=true | C B A vacia=true | Pasó |
| U-PIL-02 | M2 | Unitaria | cima no quita el elemento | B 2 | B 2 | Pasó |
| U-COL-01 | M2 | Unitaria | encolar A,B,C y desencolar 3 veces | A B C vacia=true | A B C vacia=true | Pasó |
| U-COL-02 | M2 | Unitaria | encolar A,B; desencolar; encolar C | A [B, C] | A [B, C] | Pasó |
| U-COL-03 | M2 | Unitaria | vaciar la cola y volver a encolar (puntero fin) | [X] frente=X | [X] frente=X | Pasó |
| U-AG-01 | M3 | Unitaria | preorden UTA>FISEI>{SW>{E50,E30}, TI} | [UTA, FISEI, SW, E50, E30, TI] | [UTA, FISEI, SW, E50, E30, TI] | Pasó |
| U-AG-02 | M3 | Unitaria | posorden del mismo arbol | [E50, E30, SW, TI, FISEI, UTA] | [E50, E30, SW, TI, FISEI, UTA] | Pasó |
| U-AG-03 | M3 | Unitaria | altura y tamanio | 4 6 | 4 6 | Pasó |
| U-AG-04 | M3 | Unitaria | eliminar subarbol SW | true [UTA, FISEI, TI] 3 | true [UTA, FISEI, TI] 3 | Pasó |
| U-ABB-01 | M3 | Unitaria | insertar 50,30,70,20,40,60,80 -> inorden | [20, 30, 40, 50, 60, 70, 80] | [20, 30, 40, 50, 60, 70, 80] | Pasó |
| U-ABB-02 | M3 | Unitaria | preorden del caso | [50, 30, 20, 40, 70, 60, 80] | [50, 30, 20, 40, 70, 60, 80] | Pasó |
| U-ABB-03 | M3 | Unitaria | posorden del caso | [20, 40, 30, 60, 80, 70, 50] | [20, 40, 30, 60, 80, 70, 50] | Pasó |
| U-ABB-04 | M3 | Unitaria | eliminar hoja 20 | true [50, 30, 40, 70, 60, 80] valido=true | true [50, 30, 40, 70, 60, 80] valido=true | Pasó |
| U-ABB-05 | M3 | Unitaria | eliminar 30 con un hijo (arbol 50,30,70,20) | true [50, 20, 70] valido=true | true [50, 20, 70] valido=true | Pasó |
| U-ABB-06 | M3 | Unitaria | eliminar 30 con dos hijos (sucesor inorden 40) | true [50, 40, 20, 70, 60, 80] valido=true | true [50, 40, 20, 70, 60, 80] valido=true | Pasó |
| U-ABB-07 | M3 | Unitaria | eliminar la raiz 50 (dos hijos, sucesor 60) | true [60, 30, 20, 40, 70, 80] valido=true | true [60, 30, 20, 40, 70, 80] valido=true | Pasó |
| U-ABB-08 | M3 | Unitaria | buscar 80 y contar comparaciones | E80 3 | E80 3 | Pasó |
| U-ABB-09 | M3 | Unitaria | minimo, maximo, altura y tamanio | 20 80 3 7 | 20 80 3 7 | Pasó |
| U-ABB-10 | M3 | Unitaria | insertar duplicado 40 | false 7 | false 7 | Pasó |
| U-GRA-01 | M4 | Unitaria | BFS desde PC1 | [PC1, SW1, PC3, SRV1, PC2] | [PC1, SW1, PC3, SRV1, PC2] | Pasó |
| U-GRA-02 | M4 | Unitaria | DFS desde PC1 | [PC1, SW1, SRV1, PC2, PC3] | [PC1, SW1, SRV1, PC2, PC3] | Pasó |
| U-GRA-03 | M4 | Unitaria | eliminar arista SW1->SRV1 | true 3 [PC1, SW1, PC3] | true 3 [PC1, SW1, PC3] | Pasó |
| U-GRA-04 | M4 | Unitaria | eliminar vertice SRV1 quita entrante SW1->SRV1 y saliente SRV1->PC2 | true [] 2 5 | true [] 2 5 | Pasó |
| U-GRA-05 | M4 | Unitaria | quitar las aristas que llegan a SW1 (solo PC1->SW1) | 1 [PC3] 0 | 1 [PC3] 0 | Pasó |
| U-GRA-06 | M4 | Unitaria | grafo no dirigido: A-B aparece en ambos lados | [B] [A] 1 | [B] [A] 1 | Pasó |
| U-GRA-07 | M4 | Unitaria | no dirigido: eliminar A-B borra las dos copias | [] [] 0 | [] [] 0 | Pasó |
| U-GRA-08 | M4 | Unitaria | agregarAristaEn posicion 0 respeta orden de vecinos | [PC3, SW1] | [PC3, SW1] | Pasó |
| U-GRA-09 | M4 | Unitaria | ids con espacios a los lados se tratan igual (' PC1 ' y 'PC1') | true [PC1, SW1, PC3, SRV1, PC2] | true [PC1, SW1, PC3, SRV1, PC2] | Pasó |
| U-GRA-10 | M4 | Unitaria | pesos de las aristas del caso | 2.0 1.0 3.0 4.0 | 2.0 1.0 3.0 4.0 | Pasó |
| U-M1-01 | M1 | Unitaria | busqueda secuencial de 80 en el caso | true 7 | true 7 | Pasó |
| U-M1-02 | M1 | Unitaria | busqueda en ABB de 80 en el caso | true 3 | true 3 | Pasó |
| U-M1-03 | M1 | Unitaria | actualizar nombres de 40 | 40 - Ana Lopez (SW) | 40 - Ana Lopez (SW) | Pasó |
| U-M1-04 | M1 | Unitaria | eliminar 40 y deshacer (vuelve a su posicion), luego eliminar 60: vector, lista, ABB y arbol sincronizados | true true 6 [50, 30, 70, 20, 40, 80] | true true 6 [50, 30, 70, 20, 40, 80] | Pasó |
| U-M2-01 | M2 | Unitaria | atender 3 solicitudes del caso (FIFO) | 1 2 3 | 1 2 3 | Pasó |
| U-M2-02 | M2 | Unitaria | registrar 90, eliminar 40 y deshacer dos veces (LIFO) | Eliminar 40 \| Registrar 90 \| [] \| 7 | Eliminar 40 \| Registrar 90 \| [] \| 7 | Pasó |
| U-M2-03 | M2 | Unitaria | devolver S1 al frente de [S2,S3] usando solo encolar/desencolar | [1, 2, 3] | [1, 2, 3] | Pasó |
| U-M2-04 | M2 | Unitaria | retirar S2 del medio de [S1,S2,S3] conserva el orden | true [1, 3] | true [1, 3] | Pasó |
| U-M2-05 | M2 | Unitaria | registrar solicitud S4 y deshacerla | Registrar solicitud S4 \| 3 | Registrar solicitud S4 \| 3 | Pasó |
| U-M3-01 | M3 | Unitaria | jerarquia base en preorden | [UTA, FISEI, SW, TI] | [UTA, FISEI, SW, TI] | Pasó |
| U-M3-02 | M3 | Unitaria | estudiantes vinculados a SW en el caso | [E50, E30, E70, E20, E40, E60, E80] | [E50, E30, E70, E20, E40, E60, E80] | Pasó |
| U-M3-03 | M3 | Unitaria | agregar facultad FCHE y carrera PSI: preorden y altura | [UTA, FISEI, SW, TI, FCHE, PSI] 3 | [UTA, FISEI, SW, TI, FCHE, PSI] 3 | Pasó |
| U-M4-01 | M4 | Unitaria | desconectar PC1->PC3 y deshacer (vuelve a la misma posicion) | PC1->PC3(4.0) [SW1, PC3] | PC1->PC3(4.0) [SW1, PC3] | Pasó |
| U-M4-02 | M4 | Unitaria | conectar PC3->PC4 y deshacer | true false | true false | Pasó |
| U-M5-01 | M5 | Unitaria | amigos de Ana y de Carla | [Beto, Carla] [Ana, Diego, Elena] | [Beto, Carla] [Ana, Diego, Elena] | Pasó |
| U-M5-02 | M5 | Unitaria | recomendaciones para Diego | [Ana (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])] | [Ana (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])] | Pasó |
| U-M5-03 | M5 | Unitaria | eliminar amistad Carla-Elena y recomendar a Ana | [Diego (2 en comun: [Beto, Carla])] | [Diego (2 en comun: [Beto, Carla])] | Pasó |
| U-M5-04 | M5 | Unitaria | empate en amigos en comun se ordena por nombre | [Ana (1 en comun: [X]), Zoe (1 en comun: [X])] | [Ana (1 en comun: [X]), Zoe (1 en comun: [X])] | Pasó |
| CI-01 | M3 | Integrador | insertar 50,30,70,20,40,60,80 y mostrar inorden | [20, 30, 40, 50, 60, 70, 80] | [20, 30, 40, 50, 60, 70, 80] | Pasó |
| CI-02 | M3 | Integrador | eliminar matricula 30 (dos hijos) | 30 - Estudiante 30 (SW) | 30 - Estudiante 30 (SW) | Pasó |
| CI-03 | M3 | Integrador | inorden despues de eliminar 30 | [20, 40, 50, 60, 70, 80] | [20, 40, 50, 60, 70, 80] | Pasó |
| CI-04 | M3 | Integrador | preorden y validez del ABB despues de eliminar 30 (40 sube) | [50, 40, 20, 70, 60, 80] valido=true | [50, 40, 20, 70, 60, 80] valido=true | Pasó |
| CI-05 | M1/M3 | Integrador | 30 ya no esta en vector, lista, ABB ni arbol general | false -1 false [E50, E70, E20, E40, E60, E80] | false -1 false [E50, E70, E20, E40, E60, E80] | Pasó |
| CI-06 | M2 | Integrador | procesar tres solicitudes FIFO | S1 S2 S3 | S1 S2 S3 | Pasó |
| CI-07 | M2 | Integrador | recuperar dos operaciones LIFO (deshacer x2) | Atender S3 \| Atender S2 | Atender S3 \| Atender S2 | Pasó |
| CI-08 | M2 | Integrador | cola y historial despues de deshacer | frente=S2 pendientes=2 historial=[Atender S1, Eliminar 30] | frente=S2 pendientes=2 historial=[Atender S1, Eliminar 30] | Pasó |
| CI-09 | M4 | Integrador | BFS desde PC1 | [PC1, SW1, PC3, SRV1, PC2] | [PC1, SW1, PC3, SRV1, PC2] | Pasó |
| CI-10 | M4 | Integrador | equipos no alcanzables desde PC1 | [PC4] | [PC4] | Pasó |
| CI-11 | M4 | Integrador | DFS desde PC1 | [PC1, SW1, SRV1, PC2, PC3] | [PC1, SW1, SRV1, PC2, PC3] | Pasó |
| CI-12 | M5 | Integrador | recomendaciones para Ana | [Diego (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])] | [Diego (2 en comun: [Beto, Carla]), Elena (1 en comun: [Carla])] | Pasó |
| CI-13 | M5 | Integrador | Diego va antes que Elena y no aparecen Ana, Beto ni Carla | Diego,Elena | Diego,Elena | Pasó |
| CI-14 | M4 | Integrador | eliminar arista SW1->SRV1 | SW1->SRV1(1.0) | SW1->SRV1(1.0) | Pasó |
| CI-15 | M4 | Integrador | BFS desde PC1 despues de eliminar la arista | [PC1, SW1, PC3] | [PC1, SW1, PC3] | Pasó |
| CI-16 | M4 | Integrador | no alcanzables despues de eliminar la arista | [SRV1, PC2, PC4] | [SRV1, PC2, PC4] | Pasó |
| CI-17 | M6 | Integrador | estado compartido consistente al final del caso | true | true | Pasó |
| N-01 | M1/M3 | Normal | registrar 90 en TI: entra al ABB y al arbol bajo TI | true [E90] [20, 30, 40, 50, 60, 70, 80, 90] | true [E90] [20, 30, 40, 50, 60, 70, 80, 90] | Pasó |
| N-02 | M1/M2/M4 | Normal | solicitud de un estudiante nuevo para PC4 y atencion en su turno | S4 S1 | S4 S1 | Pasó |
| N-03 | M1/M3 | Normal | actualizar carrera de 70 a TI lo mueve en el arbol general | [E70] true | [E70] true | Pasó |
| N-04 | M4 | Normal | conectar PC3->PC4 vuelve alcanzable a PC4 | [PC1, SW1, PC3, SRV1, PC4, PC2] [] | [PC1, SW1, PC3, SRV1, PC4, PC2] [] | Pasó |
| N-05 | M5 | Normal | Ana se hace amiga de Diego y Diego de Elena (Fabian sin amigos): Diego sale y queda solo Elena | [Elena (2 en comun: [Carla, Diego])] | [Elena (2 en comun: [Carla, Diego])] | Pasó |
| N-06 | M1 | Normal | comparar busqueda de 80: ABB hace menos comparaciones que la secuencial | 7 > 3 | 7 > 3 | Pasó |
| N-07 | M4 | Normal | hay camino PC1->PC2 y no hay PC2->PC1 (aristas dirigidas) | true false | true false | Pasó |
| N-08 | M4 | Normal | eliminar equipo SW1: se quitan PC1->SW1 y SW1->SRV1 | [PC3] 2 [PC1, PC3] | [PC3] 2 [PC1, PC3] | Pasó |
| E-01 | M3 | Extremo | recorridos de un ABB vacio | [] [] [] 0 | [] [] [] 0 | Pasó |
| E-02 | M3 | Extremo | ABB con un solo elemento: eliminarlo deja el arbol vacio | true true 0 | true true 0 | Pasó |
| E-03 | M1 | Extremo | eliminarEn(0) en lista vacia | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-04 | M1 | Extremo | eliminarEn(0) en vector vacio | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-05 | M2 | Extremo | desapilar pila vacia | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-06 | M2 | Extremo | desencolar cola vacia | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-07 | M2 | Extremo | deshacer con historial vacio | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-08 | M2 | Extremo | deshacer 3 veces cuando solo hay 2 operaciones | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-09 | M2 | Extremo | atender una cuarta solicitud con la cola vacia | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-10 | M4 | Extremo | grafo sin aristas: BFS solo visita el origen | [A] [B, C] | [A] [B, C] | Pasó |
| E-11 | M4 | Extremo | BFS y DFS desde PC4 aislado, y cantidad de no alcanzables | [PC4] [PC4] 5 | [PC4] [PC4] 5 | Pasó |
| E-12 | M5 | Extremo | usuario sin amigos no recibe recomendaciones | [] | [] | Pasó |
| E-13 | M5 | Extremo | usuario con amigos pero sin contactos de segundo grado | [] | [] | Pasó |
| E-14 | M1 | Extremo | buscar el primero (50): secuencial 1 comparacion, ABB 1 | 1 1 | 1 1 | Pasó |
| E-15 | M1 | Extremo | buscar el ultimo registrado (80): secuencial 7, ABB 3 | 7 3 | 7 3 | Pasó |
| E-16 | M1 | Extremo | buscar inexistente (99): no encontrado, secuencial 7, ABB 3 | false 7 false 3 | false 7 false 3 | Pasó |
| E-17 | M3 | Extremo | insertar 1..5 ordenado: altura 5 y buscar 5 cuesta 5 comparaciones | 5 5 | 5 5 | Pasó |
| E-18 | M1 | Extremo | eliminar despues de vaciar el registro de estudiantes | EstructuraVaciaException | EstructuraVaciaException | Pasó |
| E-19 | M3 | Extremo | ABB con un nodo: preorden, inorden y posorden coinciden | [7] [7] [7] | [7] [7] [7] | Pasó |
| E-20 | M4 | Extremo | eliminar el unico vertice de un grafo | true true | true true | Pasó |
| E-21 | M1 | Extremo | lista con un elemento: eliminarlo actualiza cabeza y cola | true [8] 8 8 | true [8] 8 8 | Pasó |
| ER-01 | M1 | Erróneo | registrar matricula duplicada 50 | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-02 | M1 | Erróneo | registrar matricula 0 | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-03 | M1 | Erróneo | registrar matricula negativa -5 | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-04 | M1 | Erróneo | registrar con nombres null | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-05 | M1/M3 | Erróneo | registrar en carrera inexistente XX | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-06 | M1/M3 | Erróneo | registrar en FISEI (es facultad, no carrera) | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-07 | M1 | Erróneo | eliminar matricula inexistente 99 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-08 | M1 | Erróneo | actualizar matricula inexistente 99 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-09 | M1/M2 | Erróneo | eliminar estudiante 50 con solicitud pendiente | IllegalStateException | IllegalStateException | Pasó |
| ER-10 | M2 | Erróneo | solicitud con matricula inexistente 99 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-11 | M2/M4 | Erróneo | solicitud para equipo inexistente PC9 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-12 | M2 | Erróneo | solicitud con motivo vacio | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-13 | M4 | Erróneo | eliminar arista inexistente PC1->PC2 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-14 | M4 | Erróneo | conectar con vertice inexistente PC1->PC9 | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-15 | M4 | Erróneo | lazo PC1->PC1 | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-16 | M4 | Erróneo | peso negativo | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-17 | M4 | Erróneo | peso 0 | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-18 | M4 | Erróneo | peso NaN | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-19 | M4 | Erróneo | peso infinito | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-20 | M4 | Erróneo | conexion duplicada PC1->SW1 | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-21 | M4 | Erróneo | equipo duplicado PC1 | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-22 | M4 | Erróneo | BFS desde vertice inexistente | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-23 | M4 | Erróneo | eliminar arista en grafo vacio | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-24 | M4 | Erróneo | vertice con id vacio | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-25 | M4 | Erróneo | eliminar equipo inexistente | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-26 | M2/M4 | Erróneo | eliminar equipo PC1 con solicitud pendiente | IllegalStateException | IllegalStateException | Pasó |
| ER-27 | M5 | Erróneo | amistad duplicada Ana-Beto (en orden inverso) | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-28 | M5 | Erróneo | amistad consigo mismo | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-29 | M5 | Erróneo | recomendar a usuario inexistente | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-30 | M5 | Erróneo | usuario duplicado | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-31 | M5 | Erróneo | eliminar amistad inexistente Ana-Elena | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-32 | M1 | Erróneo | agregar null al vector | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-33 | M1 | Erróneo | agregar null a la lista | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-34 | M2 | Erróneo | apilar null | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-35 | M3 | Erróneo | insertar clave null en ABB | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-36 | M3 | Erróneo | agregar carrera a facultad inexistente | ElementoNoEncontradoException | ElementoNoEncontradoException | Pasó |
| ER-37 | M3 | Erróneo | agregar facultad duplicada FISEI | ElementoDuplicadoException | ElementoDuplicadoException | Pasó |
| ER-38 | M1 | Erróneo | obtener posicion fuera de rango del vector | IndexOutOfBoundsException | IndexOutOfBoundsException | Pasó |
| ER-39 | M3 | Erróneo | agregar carrera con codigo reservado E90 (choca con el nodo del estudiante 90) | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-40 | M3 | Erróneo | agregar hijo a un nodo que ya fue eliminado del arbol general | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-41 | M2 | Erróneo | solicitud con matricula negativa directo en el servicio | IllegalArgumentException | IllegalArgumentException | Pasó |
| ER-42 | M3 | Erróneo | eliminar del ABB una clave inexistente no lo altera | false 7 true | false 7 true | Pasó |
| ER-43 | M6 | Erróneo | un registro fallido (duplicado) no deja rastro en ningun modulo | 7 0 true | 7 0 true | Pasó |
| ER-44 | M6 | Erróneo | un registro con carrera invalida no deja rastro | 7 true | 7 true | Pasó |
| RG-01 | M6 | Regresión | secuencia: +90, +10(TI), atender S1, -50 (raiz), 70->TI, solicitud 90, -PC1->PC3, +PC3->PC4 | [10, 20, 30, 40, 60, 70, 80, 90] valido=true consistente=true TI=[E10, E70] hist=8 | [10, 20, 30, 40, 60, 70, 80, 90] valido=true consistente=true TI=[E10, E70] hist=8 | Pasó |
| RG-02 | M6 | Regresión | deshacer las 8 operaciones en orden inverso | Conectar PC3->PC4(5.0), Desconectar PC1->PC3(4.0), Registrar solicitud S4, Actualizar 70, Eliminar 50, Atender S1, Registrar 10, Registrar 90 | Conectar PC3->PC4(5.0), Desconectar PC1->PC3(4.0), Registrar solicitud S4, Actualizar 70, Eliminar 50, Atender S1, Registrar 10, Registrar 90 | Pasó |
| RG-03 | M6 | Regresión | estado restaurado: inorden, BFS, cola, vecinos de PC1 e historial como al inicio | [20, 30, 40, 50, 60, 70, 80] [PC1, SW1, PC3, SRV1, PC2] [1, 2, 3] [SW1, PC3] hist=0 true | [20, 30, 40, 50, 60, 70, 80] [PC1, SW1, PC3, SRV1, PC2] [1, 2, 3] [SW1, PC3] hist=0 true | Pasó |
| RG-04 | M1 | Regresión | el 50 restaurado vuelve a su posicion original en el vector | 50 | 50 | Pasó |
| RG-05 | M2/M4 | Regresión | conectar PC4->PC2, +90, eliminar PC4: se descarta la conexion y deshacer sigue funcionando | descartadas=1 Registrar 90 hist=0 PC4=false | descartadas=1 Registrar 90 hist=0 PC4=false | Pasó |
| RG-06 | M2/M4 | Regresión | atender S1..S3, registrar y atender S4 en PC4, eliminar PC4 y deshacer 3 veces: S4 no revive | descartadas=2 [1, 2, 3] | descartadas=2 [1, 2, 3] | Pasó |
| RG-07 | M6 | Regresión | 100 registros (100..199) y eliminar los pares | 57 true true | 57 true true | Pasó |
| RG-08 | M6 | Regresión | repetir el flujo de RG-07 en una instancia nueva y deshacer las 150 operaciones: vuelve al estado inicial | 7 0 true [20, 30, 40, 50, 60, 70, 80] [50, 30, 70, 20, 40, 60, 80] [E50, E30, E70, E20, E40, E60, E80] | 7 0 true [20, 30, 40, 50, 60, 70, 80] [50, 30, 70, 20, 40, 60, 80] [E50, E30, E70, E20, E40, E60, E80] | Pasó |
| RG-09 | M6 | Regresión | menu: M3 recorridos + M1 eliminar 30 + M3 inorden (guion de entrada) | true true true | true true true | Pasó |
| RG-10 | M6 | Regresión | menu: entrada no numerica y error controlado sin cerrar el programa | true true true | true true true | Pasó |
| RG-11 | M6 | Regresión | menu: opcion 6 ejecuta el caso integrador completo | true true true | true true true | Pasó |
| RG-12 | M6 | Regresión | menu: fin de entrada inesperado no lanza excepcion | true | true | Pasó |
| RG-13 | M6 | Regresión | si el arbol general rechaza el nodo, el registro se revierte en vector, lista y ABB | false 7 0 | false 7 0 | Pasó |
| RG-14 | M6 | Regresión | si deshacer una eliminacion falla en el arbol, M1 no queda restaurado a medias | error false hist=1 | error false hist=1 | Pasó |
| RG-15 | M3 | Regresión | eliminar 6 estudiantes y deshacer: el orden de hijos de SW vuelve a ser el original | [E50, E30, E70, E20, E40, E60, E80] | [E50, E30, E70, E20, E40, E60, E80] | Pasó |
| RG-16 | M2 | Regresión | actualizar sin cambios no apila una operacion | 0 | 0 | Pasó |
| RG-17 | M4/M2 | Regresión | conectar ' PC4 '->PC1 con espacios, eliminar PC4 y deshacer: la operacion se descarta | 1 [] [SW1, PC3] | 1 [] [SW1, PC3] | Pasó |
