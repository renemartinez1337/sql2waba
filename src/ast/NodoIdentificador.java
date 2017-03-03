package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private String tipo;
	private NodoBase Argumentos;
	private boolean funcion=false;
	
	
       
	public NodoIdentificador(String nombre, NodoBase Argumentos) {
		super();
		this.nombre = nombre;
		this.Argumentos = Argumentos;
	}
	
	public NodoIdentificador(String nombre, NodoBase Argumentos, String tipo) {
		super();
		this.nombre = nombre;
		this.Argumentos = Argumentos;
	}

	public NodoIdentificador(String nombre, String tipo) {
		super();
		this.nombre = nombre;
	}
   	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
	}
	public NodoIdentificador(String nombre,boolean funcion) {
		super();
		this.nombre = nombre;
		this.funcion=funcion;
	}

	public NodoIdentificador() {
		super();
	}

	public NodoBase getArgumentos() {
		return Argumentos;
	}


	public String getNombre() {
		return nombre;
	}
	public boolean getfuncion() {
		return funcion;
	}
        public String getTipo() {
		return tipo;
	}

}