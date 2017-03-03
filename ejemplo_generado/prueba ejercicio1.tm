*      Compilacion TINY para el codigo objeto TM
*      Archivo: Nodo_<Por definir>
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      Inicio de una funcion
*      -> identificador
2:       LD       0,0(5)      cargar valor de identificador: main
*      -> identificador
*      -> leer
3:       IN       0,0,0      leer: lee un valor entero 
4:       ST       0,1(5)      leer: almaceno el valor entero leido en el id x
*      <- leer
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
5:       LD       0,1(5)      cargar valor de identificador: x
*      -> identificador
6:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
7:       LDC       0,1(0)      cargar constante: 1
*      <- constante
8:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
9:       ADD       0,1,0      op: +
*      <- Operacion: mas
10:       ST       0,1(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
11:       LD       0,1(5)      cargar valor de identificador: ast.NodoIdentificador@14ae5a5
*      -> identificador
12:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
13:       HALT       0,0,0      Fin del programa