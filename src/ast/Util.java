package ast;

import java.util.ArrayList;

public class Util {
	
	static int sangria = 0;
	
	//Imprimo en modo texto con sangrias el AST
	public static void imprimirAST(NodoBase raiz, ArrayList<NodoIdentificador> columnas){
		  sangria+=2;
		  while (raiz != null) {
		    printSpaces();
		    if (raiz instanceof  NodoSelect )
		    	System.out.println("Select");                    
		    else if (raiz instanceof  NodoFrom)
		    	System.out.println("\n From");                    
		    else if (raiz instanceof  NodoJoin)
		    	System.out.println("\n Join");
		    else  if (raiz instanceof  NodoWhere)
		    	System.out.println("\n Where");		    
		    else   if (raiz instanceof  NodoIdentificador)
		    	 imprimirNodo(raiz);		   		    
		    else if (raiz instanceof  NodoValor)
		    	 imprimirNodo(raiz);
		    else if (raiz instanceof NodoCondicion )
                        imprimirNodo(raiz);
		    else System.out.println("Tipo de nodo desconocido");
		    
                    //RECORRIDO DEL ARBOL
                    
                    if (raiz instanceof NodoSelect){
                        printSpaces();
                        System.out.println("\n* Select Columnas *");
                        printSpaces();
                        imprimirNodo(((NodoSelect)raiz).getColumnas());
                        printSpaces();
                        System.out.println("\n* Select From  *");
                        printSpaces();
                        imprimirNodo(((NodoSelect)raiz).getFrom());
                        printSpaces();
                        
                        if(((NodoSelect)raiz).getJoin() != null){
                            System.out.println("\n* Select Join1  *");
                            printSpaces();
                            imprimirNodo(((NodoSelect)raiz).getJoin());
                            printSpaces();
                        }
                        
                        if(((NodoSelect)raiz).getJoin2() != null){
                            System.out.println("\n* Select Join2  *");
                            printSpaces();
                            imprimirNodo(((NodoSelect)raiz).getJoin2());
                            printSpaces();
                        }
                        
                        System.out.println("\n* Select Where  *");
                        printSpaces();
                        imprimirNodo(((NodoSelect)raiz).getWhere());
                        printSpaces();
                        
                    }                    
                                                         
		    raiz = raiz.getHermanoDerecha();
		  }
		  sangria-=2;
		}

    /* Imprime espacios con sangria */
    static void printSpaces(){         
        for(int i = 0; i < sangria; i++)
            System.out.print(" ");
    }

    /* Imprime informacion de los nodos */
    static void imprimirNodo( NodoBase raiz )
    {                
        if(raiz instanceof NodoSelect){
            System.out.println("Columnas = "+ ((NodoSelect)raiz));             
        }
        
        if(raiz instanceof NodoFrom){
            System.out.println("\tTabla: " + ((NodoFrom)raiz).getTabla());
        }     
        
        if(raiz instanceof NodoJoin){
            System.out.println("\tTabla Destino: " + ((NodoJoin)raiz).gettablaDestino());
            imprimirNodo(((NodoJoin)raiz).getCondicion());
        }
        
        if(raiz instanceof NodoWhere){
            System.out.println("\tTabla = "+ ((NodoWhere)raiz).getCondicion());
        }
        

        if(	raiz instanceof NodoCondicion ){
                tipoCond sel=((NodoCondicion) raiz).getCondicion();
                
                System.out.println("\n\tCondicion lado izquierdo: "); 
                imprimirNodo(((NodoCondicion) raiz).getOpIzquierdo());
                
                if(sel==tipoCond.and)
                        System.out.println("\n\tOperador de condición: AND ");
                if(sel==tipoCond.or)
                        System.out.println("\n\tOperacion de condición: OR ");		
                if(sel==tipoCond.equal)
                        System.out.println("\n\tOperacion de condición: = ");
                if(sel==tipoCond.not_equal)
                        System.out.println("\n\tOperacion de condición: != ");		
                
                System.out.println("\n\tCondicion lado derecho: "); 
                imprimirNodo(((NodoCondicion) raiz).getOpDerecho());
        }

        if( raiz instanceof NodoValor ){		
            if(((NodoValor)raiz).getisboolean())
                System.out.println("\tBOOLEAN, val = "+ ((NodoValor)raiz).getValorb());
            else
                System.out.println("\tINT, val = "+ ((NodoValor)raiz).getValor());		
        }

        if(	raiz instanceof NodoIdentificador ){
                System.out.println("\tID, nombre = "+ ((NodoIdentificador)raiz).getNombre());
                if(((NodoIdentificador)raiz).getColumna() != null){
                    System.out.println("\t\tColumna, nombre = "+ ((NodoIdentificador)raiz).getColumna());
                }              
        }
    }
}
