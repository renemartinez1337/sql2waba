package ast;

public class NodoFrom extends NodoBase {
	private String tabla;
	   
	public NodoFrom(String tabla) {
		super();
		this.tabla = tabla;		
	}
	
	public NodoFrom() {
		super();
		this.tabla = null;
	}

	public String getTabla() {
		return tabla;
	}
}