package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private String columna;
	   
	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
		this.columna = null;
	}

	public NodoIdentificador(String nombre, String columna) {
		super();
		this.nombre = nombre;
		this.columna = columna;
	}

	public NodoIdentificador() {
		super();
		this.nombre = null;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getColumna() {
		return columna;
	}

}