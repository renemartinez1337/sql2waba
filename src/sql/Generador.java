package sql;

import ast.*;
import java.io.*;
import java.util.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 * 
	 * */
	
	
	
	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
	 *   otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion 
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	private static String ultimoAmbito;
	private static int saltomain;      
        private static ArrayList<Integer> localidad_return = new ArrayList<Integer>();
   
         
	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}
        
	public static void generarCodigoObjeto(NodoBase raiz) throws IOException{
                UtGen.IniciarFile();
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
                
		generarPreludioEstandar();
		generar(raiz);
	
		/*Genero el codigo de finalizacion de ejecucion del codigo*/   
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		
                System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
                char x=5;
                System.out.println("\n\n\t "+x+" **> Archivo MiniC_Tiny.tm fue creado <** "+x);
	}
	
	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
	private static void generar(NodoBase nodo) throws IOException{
	
            if(tablaSimbolos!=null){
		if (nodo instanceof  NodoProgram )
	    	generarPrograma(nodo);
	    else  if (nodo instanceof  NodoIf)
		generarIf(nodo);
	    else if (nodo instanceof  NodoFor)
	    	generarFor(nodo);
	    else  if (nodo instanceof  NodoWhile)
		generarWhile(nodo);
	    else if (nodo instanceof  NodoAsignacion)
		generarAsignacion(nodo);
	    else if (nodo instanceof  NodoLeer)  
	    	generarLeer(nodo);
	    else if (nodo instanceof  NodoEscribir)
	    	generarEscribir(nodo);
	    else if (nodo instanceof NodoValor)
	    	generarValor(nodo);
	    else if (nodo instanceof  NodoDeclaracion )
                generarDeclaracion(nodo);
	    else  if (nodo instanceof  NodoIdentificador)
	    	generarIdentificador(nodo);
	    else if (nodo instanceof NodoOperacion)
		generarOperacion(nodo);
	    else  if (nodo instanceof  NodoFuncion)
	    	generarFuncion(nodo);
	    else if (nodo instanceof  NodoLlamadaFuncion)
	    	generarLlamadaFuncion(nodo);
	    else
	    	System.out.println("BUG: Tipo de nodo a generar desconocido");
			    	 
		
		/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
		if(nodo.TieneHermano())
			generar(nodo.getHermanoDerecha());
		
	}else
		System.out.println("ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
}
        
        private static void generarPrograma(NodoBase nodo) throws IOException{
            
                if(((NodoProgram) nodo).getFunctions()!=null){
			generar(((NodoProgram) nodo).getFunctions());
                }else{

                    if(((NodoProgram) nodo).getMain()!=null){
			ultimoAmbito = "main";			
			//iniciar la ejecucion en la linea #line main
			int pos = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(saltomain);
			UtGen.emitirRM("LDA", UtGen.PC, pos,UtGen.GP, "Salto incodicional al main");
			UtGen.restaurarRespaldo();
			generar(((NodoProgram) nodo).getMain());
                        
			pos=UtGen.emitirSalto(0);
			for	(int i=0; i<localidad_return.size();i++){
				UtGen.cargarRespaldo(localidad_return.get(i));
				UtGen.emitirRM("LDA", UtGen.PC, pos, UtGen.GP, "salto del return");
				UtGen.restaurarRespaldo();
			}
			localidad_return.clear();
                    }
                }
	}
	private static void generarIf(NodoBase nodo) throws IOException{
    	NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba());
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");//Genero la parte THEN
		generar(n.getParteThen());
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getParteElse()!=null){
			generar(n.getParteElse());
		}	
			localidadActual = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(localidadSaltoEnd);
			UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
			UtGen.restaurarRespaldo();
    	
		
		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}
	

private static void generarValor(NodoBase nodo) throws IOException{
    	
	NodoValor n = (NodoValor)nodo;
    	if(UtGen.debug)	UtGen.emitirComentario("-> constante");
    	
    	if(!n.getisboolean())
    		UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "+n.getValor());
    	else
    		UtGen.emitirRM("LDC", UtGen.AC,n.getValorb()?1:0 , 0, "cargar booleano: "+n.getValorb());
    	
        if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}
	
        private static void generarDeclaracion(NodoBase nodo) throws IOException{
           
        	
        	NodoDeclaracion a = (NodoDeclaracion)nodo;
        	NodoIdentificador n =(NodoIdentificador)a.getVariable();
            RegistroSimbolo s = tablaSimbolos.BuscarSimbolo((n.getNombre()));
            if(UtGen.debug)	UtGen.emitirComentario("-> declaracion");
            int pos = UtGen.emitirSalto(0); 
            UtGen.emitirRM("LD", UtGen.AC, pos, UtGen.GP, "cargar valor de identificador: "+n.getNombre().toString());
        	 s.setDireccionMemoria(pos);
            if(UtGen.debug)	UtGen.emitirComentario("<- declaracion");
            
            
            if(((NodoDeclaracion)nodo).getexpresionasignacion()!=null)
            	generarAsignacion(((NodoDeclaracion)nodo).getexpresionasignacion());


        }
	   private static void generarFuncion(NodoBase nodo) throws IOException {
 
		   	int posicion;
		
	        NodoFuncion n = (NodoFuncion) nodo;
	        RegistroSimbolo s = tablaSimbolos.BuscarSimbolo(((NodoIdentificador)((NodoFuncion)nodo).getNombre()).getNombre());

	        UtGen.emitirComentario("Inicio de una funcion");
   
       	 	posicion = UtGen.emitirSalto(0);

             if (n.getCuerpo() != null &&((NodoIdentificador)n.getNombre()).getNombre()=="main")
 	        {
            	 posicion = UtGen.emitirSalto(0);
	            generar(n.getCuerpo());
	        }
             else if(((NodoIdentificador)n.getNombre()).getNombre()!="main")
             {
            	 UtGen.emitirComentario("Cuerpo de la funcion");
            	 
            	 
            	 
            	 posicion = UtGen.emitirSalto(0);
            	 s.setDireccionMemoria(posicion);
            	 
            	 generar(n.getCuerpo());
            	 
            	 UtGen.cargarRespaldo(posicion);

            	 
            	 
            	
            	 
            	 
             }
	   
	    }
	
	private static void generarLlamadaFuncion(NodoBase nodo) throws IOException{
		
		
		NodoLlamadaFuncion n = (NodoLlamadaFuncion)nodo;
		// int direccion;

			if (n.getArgs()!=null){	
				NodoBase aux = n.getArgs();
				int pos = tablaSimbolos.getDireccion(((NodoIdentificador)aux).getNombre());
				do{			
					generar(aux); //deja es AC el valor 
					UtGen.emitirRM("ST", UtGen.AC, pos, UtGen.GP, "llamado: guarda el valor del argumento");	
					pos+=1;
					aux=aux.getHermanoDerecha();
				}while(aux!=null);
			}	
			
			
			int localidadactual=UtGen.emitirSalto(0);
			
			
			//Poner en NL la linea actual + 1
			UtGen.emitirRM("LDA", UtGen.DM, 1, UtGen.PC, "(AC=Pos actual + 1)");

			//saltar a la linea donde empieza la funcion
			
			int pos = tablaSimbolos.getDireccion(((NodoIdentificador)n.getNombre()).getNombre());
			
			UtGen.emitirRM("LDA", UtGen.PC, pos,UtGen.GP, "Salto a la primera linea de la funcion"); 
			
			UtGen.cargarRespaldo(localidadactual);			
			
    	
	}

	private static void generarWhile(NodoBase nodo) throws IOException{ 
    	NodoWhile n = (NodoWhile)nodo;
		int localidadSaltoInicio,localidadSaltoCondicional,localidadActual;
		/* Genero el codigo de la prueba del while */
		if(UtGen.debug)	UtGen.emitirComentario("-> while");
		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("while: aqui deberia ir el marcado del inicio del while");
		generar(n.getPrueba());
		localidadSaltoCondicional = UtGen.emitirSalto(1);
		if(UtGen.debug)	UtGen.emitirComentario("-> cuerpo while");
		/* Genero el cuerpo del while */
		generar(n.getCuerpo());
		//Salto al Inicio del while
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadSaltoInicio, "if: jmp hacia el final");
		
		//Salto si el while es falso (0) salto al fin
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoCondicional);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia fin del while si falso (0)");
		UtGen.restaurarRespaldo();
 
	}
	
	private static void generarFor(NodoBase nodo) throws IOException{ 
        
		
		NodoFor n = (NodoFor) nodo;
		int localidadSaltoInicio,localidadSaltoCondicional,localidadActual;
		generar(n.getAsignacion());
		//Genero el codigo de la prueba del while 
		if(UtGen.debug)	UtGen.emitirComentario("-> while");
		
		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("for: aqui deberia ir el marcado del inicio del for");
		generar(n.getCondicion());
		localidadSaltoCondicional = UtGen.emitirSalto(1);
		
		if(UtGen.debug)	UtGen.emitirComentario("-> cuerpo while");
		// Genero el cuerpo del while 
		generar(n.getCuerpo());
		generar(n.getProporcion());
		
		//Salto al Inicio del while
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadSaltoInicio, "for: jmp hacia el final");
		
		//Salto si el while es falso (0) salto al fin
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoCondicional);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "for: jmp hacia fin del for si falso (0)");
		UtGen.restaurarRespaldo();
		
		
		
		/*
		NodoFor nodof = (NodoFor) nodo;
		int localidadSaltoInicio, localidadActual, localidadBeginCondicion;
		if(UtGen.debug) UtGen.emitirComentario("-> for");
		generar(nodof.getAsignacion());

		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("for condicion: el salto hacia la condicion del for");

		generar(nodof.getCondicion());

		 localidadActual = UtGen.emitirSalto(1);
		 UtGen.cargarRespaldo(localidadSaltoInicio);
		 UtGen.emitirRM("LDA", UtGen.PC, localidadActual, 0, "jmp a inicio del for");
		 UtGen.restaurarRespaldo();

		generar(nodof.getCuerpo());
		generar(nodof.getProporcion());
		UtGen.emitirRM_Abs("JNE", UtGen.AC, localidadSaltoInicio, "for: jmp hacia el inicio del cuerpo");
		*/
		if (UtGen.debug)
			UtGen.emitirComentario("<- for");

	}
	
	
	private static void generarAsignacion(NodoBase nodo) throws IOException{
		NodoAsignacion n = (NodoAsignacion)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");		
		/* Genero el codigo para la expresion a la derecha de la asignacion */
                
		generar(n.getExpresion());
		/* Ahora almaceno el valor resultante */
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
	}
	
	private static void generarLeer(NodoBase nodo) throws IOException{
		NodoLeer n = (NodoLeer)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}
	
	private static void generarEscribir(NodoBase nodo) throws IOException{
		NodoEscribir n = (NodoEscribir)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion());
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion"+n.getExpresion());
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}
	
	
	
	private static void generarIdentificador(NodoBase nodo) throws IOException{
		NodoIdentificador n = (NodoIdentificador)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
		direccion = tablaSimbolos.getDireccion(n.getNombre());
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: "+n.getNombre().toString());
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
	}

	private static void generarOperacion(NodoBase nodo) throws IOException{
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		/* Genero la expresion izquierda de la operacion */
		generar(n.getOpIzquierdo());
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho());
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
		 case or:
             UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "Verifico si los dos operandos son true");
             UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "Si es Verdadera salto");
             UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
             UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
             UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
             break;
		case	mas:	UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");		
						break;
		case	menos:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
						break;
		case and:
               UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "Verifico si los dos operandos son true");
               UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "Si es Verdadera salto");
               UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
               UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
               UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
               break;
          
				
		case	por:	UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
						break;
		case	entre:	UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
						break;		
		case	menor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
						UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;
		case menorigual:UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <=");
						UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<=0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;
		case mayorigual:UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >=");
						UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>=0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;
		case    mayor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
						UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;
		case  diferente:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: !=");
						UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC!=0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;
		case	igual:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
						UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
						UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
						UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
						UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;	
		
			default:
							UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}
	
	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar() throws IOException{
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: "+ "MiniC_Tiny.tm");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
	}

}
