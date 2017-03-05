package ast;

public class NodoJoin extends NodoBase {
	private String tablaDestino;
	private NodoBase condicion;
	   
	public NodoJoin(String tablaDestino, NodoBase condicion) {
		super();
		this.tablaDestino = tablaDestino;
		this.condicion = condicion;
	}

	public NodoJoin() {
		super();
		this.tablaDestino = null;
		this.condicion = null;
	}

	public String gettablaDestino() {
		return tablaDestino;
	}

	public NodoBase getCondicion() {
		return condicion;
	}
}