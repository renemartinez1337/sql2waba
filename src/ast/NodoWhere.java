package ast;

public class NodoWhere extends NodoBase {	
	private NodoBase condicion;
	   
	public NodoWhere(NodoBase condicion) {
		super();		
		this.condicion = condicion;
	}

	public NodoWhere() {
		super();		
		this.condicion = null;
	}

	public NodoBase getCondicion() {
		return condicion;
	}
}