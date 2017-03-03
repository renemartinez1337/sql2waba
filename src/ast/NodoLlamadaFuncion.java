package ast;

public class NodoLlamadaFuncion extends NodoBase {
	private NodoBase args,nombre;
	
	
	public NodoLlamadaFuncion (){
		super();
		this.args =null;
		this.nombre=null;
	}
	
	public NodoLlamadaFuncion (NodoBase nombre,NodoBase args){
		super();
		this.nombre=nombre;
		this.args= args;		
	}
	public NodoLlamadaFuncion (NodoBase nombre){
		super();
		this.nombre=nombre;
		this.args= null;		
	}
	
	public NodoBase getArgs(){
		return args;
	}
	public NodoBase getNombre(){		
		return nombre;
	}	
}

