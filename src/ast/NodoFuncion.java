package ast;

public class NodoFuncion extends NodoBase {
	private NodoBase args;
	private NodoBase cuerpo;
	private NodoBase nombre;
	private String tipo;
	private NodoBase retorno;
	
	public NodoFuncion (){
		super();
		this.args =null;
		this.nombre=null;
		this.tipo=tipo;
		
		
	}
	
	public NodoFuncion (String tipo, NodoBase nombre, NodoBase args, NodoBase cuerpo, NodoBase retorno){
		super();
		this.nombre=nombre;
		this.args= args;
		this.tipo=tipo;
		this.cuerpo=cuerpo;
		this.retorno=retorno;
	}
	public NodoFuncion (NodoBase nombre){
		super();
		this.nombre=nombre;
		this.args= null;		
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public NodoBase getArgs(){
		return args;
	}
	public NodoBase getNombre(){		
		return nombre;
	}	
	public NodoBase getCuerpo(){		
		return cuerpo;
	}	
	public NodoBase getretorno(){		
		return retorno;
	}
}

