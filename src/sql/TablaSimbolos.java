package sql;


import java.util.*;


import ast.*;

public class TablaSimbolos {
	
	public ArrayList<String> errores = new ArrayList<String>();
	private HashMap<String, RegistroSimbolo> tabla;

	private boolean error=false;
	public boolean verifd=false;  //verif Verificacion de declaracion 
	private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
	
	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		
		direccion=0;
	}

	public void cargarTabla(NodoBase raiz){
            /*
		while (raiz != null) {		 					
                    if((raiz instanceof NodoIdentificador) ){
                       getDireccion(((NodoIdentificador) raiz).getNombre());				 
                    }				    
			
		    if (raiz instanceof NodoSelect){
		    	cargarTabla(((NodoSelect)raiz).getVariable());
		    }
		    
		    if (raiz instanceof NodoDeclaracion){
		    	
		    	 
		    	if(!verifd)
		    		BuscarIdentificador(((NodoDeclaracion) raiz).getVariable(),((NodoDeclaracion) raiz).getnumlinea(),((NodoDeclaracion) raiz).getTipo());
	 
		    	if(((NodoDeclaracion)raiz).getexpresionasignacion()!=null)
		    	cargarTabla(((NodoDeclaracion)raiz).getexpresionasignacion());
		    			    	
		    }
		    
		    
		    if (raiz instanceof NodoLlamadaFuncion){
		    	cargarTabla(((NodoLlamadaFuncion)raiz).getNombre());
		    	cargarTabla(((NodoLlamadaFuncion)raiz).getArgs());
		    }
		    
		    
		    if (raiz instanceof  NodoFuncion){
		    	cargarTabla(((NodoFuncion)raiz).getNombre());
		    	
		    	if(!verifd)
		    		BuscarIdentificador(((NodoFuncion)raiz).getNombre(),1,((NodoFuncion)raiz).getTipo());
		    		
		    	
		    	cargarTabla(((NodoFuncion)raiz).getArgs());
		    	cargarTabla(((NodoFuncion)raiz).getCuerpo());
		    	cargarTabla(((NodoFuncion)raiz).getretorno());	
		    	
		    }
		    
		    if (raiz instanceof  NodoIf){
		    	cargarTabla(((NodoIf)raiz).getPrueba());
		    	cargarTabla(((NodoIf)raiz).getParteThen());
		    	if(((NodoIf)raiz).getParteElse()!=null){
		    		cargarTabla(((NodoIf)raiz).getParteElse());
		    	}
		    }
		    else if (raiz instanceof  NodoRepeat){
		    	cargarTabla(((NodoRepeat)raiz).getCuerpo());
		    	cargarTabla(((NodoRepeat)raiz).getPrueba());System.out.println("\n");
		    }
		    else if (raiz instanceof  NodoFor){
		    	cargarTabla(((NodoFor)raiz).getAsignacion());
		    	cargarTabla(((NodoFor)raiz).getCondicion());
		    	cargarTabla(((NodoFor)raiz).getProporcion());
		    	cargarTabla(((NodoFor)raiz).getCuerpo());
		    	
		    }
		    else if (raiz instanceof  NodoWhile){
		    	cargarTabla(((NodoWhile)raiz).getPrueba());
		    	cargarTabla(((NodoWhile)raiz).getCuerpo());
		    	
		    }
		    else if (raiz instanceof  NodoAsignacion)
		    {
		    	if(!verifd)
		    	getDireccion(((NodoAsignacion)raiz).getIdentificador());
		    	
		    	cargarTabla(((NodoAsignacion)raiz).getExpresion());
		    	
		    }
		    else if (raiz instanceof  NodoEscribir)
		    	cargarTabla(((NodoEscribir)raiz).getExpresion());
		    
		    else if (raiz instanceof NodoOperacion){
		    	cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
		    	cargarTabla(((NodoOperacion)raiz).getOpDerecho());
		    }
		    
		    raiz = raiz.getHermanoDerecha();
		    
			 
		  }
		

		*/
	}
	
	//true es nuevo no existe se insertara, false ya existe NO se vuelve a insertar 
	public boolean InsertarSimbolo(String identificador, int numLinea, String Tipo){
		RegistroSimbolo simbolo;
		if(tabla.containsKey(identificador)){
			return false;
		}else{
			simbolo= new RegistroSimbolo(identificador,Tipo,numLinea,direccion++);
			tabla.put(identificador,simbolo);
			return true;			
		}
	}
	
	public RegistroSimbolo BuscarSimbolo(String identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(identificador);
		return simbolo;
	}
	
	public NodoBase BuscarIdentificador(NodoBase raiz,int numlinea,String tipo){

			
		if(InsertarSimbolo(((NodoIdentificador)raiz).getNombre(),numlinea,tipo)!=false)
		{	if(((NodoIdentificador)raiz).getHermanoDerecha()!=null)
			{	raiz=((NodoIdentificador)raiz).getHermanoDerecha();
			
				BuscarIdentificador(raiz,numlinea,tipo);
			
			}
		}
		else
		{
			errores.add("[ERROR semantico] Declaracion identificador '"+((NodoIdentificador)raiz).getNombre() +"' duplicada.");
			error=true;
		}	
		
			return raiz;
	}
	
	public void ImprimirClaves(){
		System.out.println("\n\n\n---------------------- Tabla de Simbolos ----------------------");
		for( Iterator <String>it = tabla.keySet().iterator(); it.hasNext();) { 
            String s = (String)it.next();
            System.out.println("Variable: "+s+" Tipo:"+BuscarSimbolo(s).getTipo() +" Direccion: " + BuscarSimbolo(s).getDireccionMemoria()+" Linea:" + BuscarSimbolo(s).getNumLinea());
	    	  
            
		}
	}
	


	
	public int getDireccion(String Clave){
		
		if(BuscarSimbolo(Clave)!=null)
		{	
			int dire=BuscarSimbolo(Clave).getDireccionMemoria();
			return dire;
		}
		else
		{
			errores.add("[ERROR semantico] Identificador '"+Clave+"' no declarado");
			error=true;
			return 1;
		}
	}
	
	public boolean geterror(){
		return this.error;
	}
	/*
	 * TODO:
	 * 1. Crear lista con las lineas de codigo donde la variable es usada.
	 * */
}
