package ast;

public class Util {
	
	static int sangria = 0;
	
	//Imprimo en modo texto con sangrias el AST
	public static void imprimirAST(NodoBase raiz){
		  sangria+=2;
		  while (raiz != null) {
		    printSpaces();
		    if (raiz instanceof  NodoProgram )
		    	System.out.println("Programa");
		    else  if (raiz instanceof  NodoFuncion)
		    	System.out.println("\n  ------- Funcion ------");
		    else  if (raiz instanceof  NodoIf)
		    	System.out.println("If");
		    else if (raiz instanceof  NodoFor)
		    	System.out.println("for");
		    else if (raiz instanceof  NodoRepeat)
		    	System.out.println("Repeat");
		    else if (raiz instanceof  NodoWhile)
		    	System.out.println("While");
		    else if (raiz instanceof  NodoAsignacion)
		    	System.out.println("Asignacion a: "+((NodoAsignacion)raiz).getIdentificador());
		    else if (raiz instanceof  NodoLeer)  
		    	System.out.println("Lectura: "+((NodoLeer)raiz).getIdentificador());		    
		    else if (raiz instanceof  NodoEscribir)
		    	System.out.println("Escribir");
		    else if (raiz instanceof  NodoDeclaracion )
		    	System.out.println("\n Declaracion");
		    else   if (raiz instanceof  NodoIdentificador)
		    	 imprimirNodo(raiz);		   
		    else if (raiz instanceof  NodoExpresion)
		    	System.out.println("Expresion");
		    else if (raiz instanceof  NodoLlamadaFuncion)
		    	System.out.println("Llamada funcion/procedimiento");
		    
		    else if (raiz instanceof  NodoValor)
		    	 imprimirNodo(raiz);
		    else if (raiz instanceof NodoOperacion )
		    imprimirNodo(raiz);
		    else System.out.println("Tipo de nodo desconocido");;
		    
		    /* Hago el recorrido recursivo */

		    if (raiz instanceof NodoDeclaracion){
		    	printSpaces();
		    	System.out.println("* tipo *"+((NodoDeclaracion)raiz).getTipo());
		    	printSpaces();
		    	System.out.println("* Identificadores  *");
		    	imprimirAST(((NodoDeclaracion)raiz).getVariable());
		    	imprimirAST(((NodoDeclaracion)raiz).getexpresionasignacion());

		    	printSpaces();	
		   
		    }
		    if (raiz instanceof NodoLlamadaFuncion){
		    	printSpaces();
		    	System.out.println("* Nombre Funcion/procedimiento  *");
		    	imprimirAST(((NodoLlamadaFuncion)raiz).getNombre());
		    	printSpaces();
		    	System.out.println("* Argumentos Funcion *");
		    	imprimirAST(((NodoLlamadaFuncion)raiz).getArgs());
		    	printSpaces();
		    	
		    	
		    }

		    if (raiz instanceof  NodoFuncion){
		    	printSpaces();
		    	System.out.println("* Nombre Funcion *");
		    	imprimirAST(((NodoFuncion)raiz).getNombre());
		    	printSpaces();
		    	System.out.println("* Tipo Funcion "+((NodoFuncion)raiz).getTipo()+" *");
		    	printSpaces();
		    	System.out.println("* Argumentos Funcion *");
		    	imprimirAST(((NodoFuncion)raiz).getArgs());
		    	printSpaces();
		    	System.out.println("* Cuerpo Funcion *");
		    	imprimirAST(((NodoFuncion)raiz).getCuerpo());
		    	printSpaces();		 
		    	System.out.println("* retorno funcion  *");
		    	imprimirAST(((NodoFuncion)raiz).getretorno());
		    	printSpaces();		
		    	
		    }

		    if (raiz instanceof  NodoIf){
		    	printSpaces();
		    	System.out.println("**Prueba IF**");
		    	imprimirAST(((NodoIf)raiz).getPrueba());
		    	printSpaces();
		    	System.out.println("**Then IF**");
		    	imprimirAST(((NodoIf)raiz).getParteThen());
		    	if(((NodoIf)raiz).getParteElse()!=null){
		    		printSpaces();
		    		System.out.println("**Else IF**");
		    		imprimirAST(((NodoIf)raiz).getParteElse());
		    	}System.out.println("\n");
		    }
		    else if (raiz instanceof  NodoRepeat){
		    	printSpaces();
		    	System.out.println("**Cuerpo REPEAT**");
		    	imprimirAST(((NodoRepeat)raiz).getCuerpo());
		    	printSpaces();
		    	System.out.println("**Prueba REPEAT**");
		    	imprimirAST(((NodoRepeat)raiz).getPrueba());System.out.println("\n");
		    }
		    else if (raiz instanceof  NodoFor){
		    	printSpaces();
                        System.out.println("**Asignacion FOR**");
		    	imprimirAST(((NodoFor)raiz).getAsignacion());
		    	printSpaces();
		    	System.out.println("**Condicion FOR**");
		    	imprimirAST(((NodoFor)raiz).getCondicion());
		    	printSpaces();
		    	System.out.println("**Proporcion FOR**");
		    	imprimirAST(((NodoFor)raiz).getProporcion());
		    	printSpaces();
		    	System.out.println("**Cuerpo FOR**");
		    	imprimirAST(((NodoFor)raiz).getCuerpo());
		    	printSpaces();System.out.println("\n");
		    	
		    }
		    else if (raiz instanceof  NodoWhile){
		    	printSpaces();
		    	System.out.println("**Condicion While**");
		    	imprimirAST(((NodoWhile)raiz).getPrueba());
		    	printSpaces();
		    	System.out.println("**Cuerpo While**");
		    	imprimirAST(((NodoWhile)raiz).getCuerpo());
		    	printSpaces();System.out.println("\n");
		    	
		    }
		    else if (raiz instanceof  NodoAsignacion)
		    	imprimirAST(((NodoAsignacion)raiz).getExpresion());
		    else if (raiz instanceof  NodoEscribir)
		    	imprimirAST(((NodoEscribir)raiz).getExpresion());
		    
		    else if (raiz instanceof NodoOperacion){
		    	printSpaces();
		    	System.out.println("**Expr Izquierda Operacion**");
		    	imprimirAST(((NodoOperacion)raiz).getOpIzquierdo());
		    	printSpaces();
		    	System.out.println("**Expr Derecha Operacion**");		    	
		    	imprimirAST(((NodoOperacion)raiz).getOpDerecho());
		    }
		    raiz = raiz.getHermanoDerecha();
		  }
		  sangria-=2;
		}

/* Imprime espacios con sangria */
static void printSpaces()
{ int i;
  for (i=0;i<sangria;i++)
	  System.out.print(" ");
}

/* Imprime informacion de los nodos */
static void imprimirNodo( NodoBase raiz )
{
	if(	raiz instanceof NodoRepeat
		||	raiz instanceof NodoLeer
		||	raiz instanceof NodoEscribir  ){
		System.out.println("palabra reservada: "+ raiz.getClass().getName());
	}
	
	if(	raiz instanceof NodoAsignacion )
		System.out.println(":=");
	
	if(	raiz instanceof NodoOperacion ){
		tipoOp sel=((NodoOperacion) raiz).getOperacion();
		
		if(sel==tipoOp.and)
			System.out.println("Operacion AND");
		if(sel==tipoOp.or)
			System.out.println("Operacion OR");
		if(sel==tipoOp.menor)
			System.out.println("Operacion <");
		if(sel==tipoOp.mayor)
			System.out.println("Operacion >");
		if(sel==tipoOp.menorigual)
			System.out.println("Operacion <=");
		if(sel==tipoOp.mayorigual)
			System.out.println("Operacion >=");
		if(sel==tipoOp.igual)
			System.out.println("Operacion ==");
		if(sel==tipoOp.diferente)
			System.out.println("Operacion !>");
		if(sel==tipoOp.mas)
			System.out.println("Operacion +");
         /*       if(sel==tipoOp.plusplus)
			System.out.println("Operacion ++");*/
		if(sel==tipoOp.menos)
			System.out.println("Operacion -");
                /*if(sel==tipoOp.menosmenos)
			System.out.println("Operacion --");*/
		if(sel==tipoOp.por)
			System.out.println("Operacion *");
		if(sel==tipoOp.entre)
			System.out.println("Operacion /");
	}

	if(	raiz instanceof NodoValor ){
		
			if(((NodoValor)raiz).getisboolean())
				System.out.println("BOOLEAN, val= "+ ((NodoValor)raiz).getValorb());
			else
				System.out.println("INT, val= "+ ((NodoValor)raiz).getValor());

		
	}

	if(	raiz instanceof NodoIdentificador ){
		System.out.println("ID, nombre= "+ ((NodoIdentificador)raiz).getNombre());
		
	}

}


}
