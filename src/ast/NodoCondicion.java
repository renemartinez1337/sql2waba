package ast;

public class NodoCondicion extends NodoBase {
		
	private NodoBase opIzquierdo;
	private NodoBase opDerecho;
	private tipoCond condicion;
	
	public NodoCondicion(NodoBase opIzquierdo, tipoCond tipoCondicion, NodoBase opDerecho) {
		super();
		this.opIzquierdo = opIzquierdo;
		this.opDerecho = opDerecho;
		this.condicion = tipoCondicion;
	}

	public NodoCondicion(tipoCond tipoCondicion) {
		super();
		this.opIzquierdo = null;
		this.opDerecho = null;
		this.condicion = tipoCondicion;
	}

	public NodoBase getOpIzquierdo() {
		return opIzquierdo;
	}

	public void setOpIzquierdo(NodoBase opIzquierdo) {
		this.opIzquierdo = opIzquierdo;
	}

	public NodoBase getOpDerecho() {
		return opDerecho;
	}

	public void setOpDerecho(NodoBase opDerecho) {
		this.opDerecho = opDerecho;
	}

	public tipoCond getCondicion() {
		return condicion;
	}

	
}
