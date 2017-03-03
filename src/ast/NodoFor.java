package ast;

public class NodoFor extends NodoBase {

    private NodoBase asignacion;
    private NodoBase condicion;
    private NodoBase proporcion;
    private NodoBase cuerpo;
    private NodoBase prueba;

    public NodoFor(NodoBase asignacion, NodoBase condicion, NodoBase proporcion, NodoBase cuerpo) {
        super();
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.proporcion = proporcion;
        this.cuerpo = cuerpo;
    }

    public NodoFor() {
        super();
        this.asignacion = null;
        this.condicion = null;
        this.proporcion = null;
        this.cuerpo = null;
    }
    public NodoBase getAsignacion() {
        return asignacion;
    }
    public NodoBase getCondicion() {
        return condicion;
    }
    public NodoBase getProporcion() {
        return proporcion;
    }
    public NodoBase getCuerpo() {
        return cuerpo;
    }
    public void setAsignacion(NodoBase asignacion) {
        this.asignacion = asignacion;
    }
    public void setCondicion(NodoBase condicion) {
        this.condicion = condicion;
    }
    public void setProporcion(NodoBase proporcion) {
        this.proporcion = proporcion;
    }
    public void setCuerpo(NodoBase cuerpo) {
        this.cuerpo = cuerpo;
    }
    
    public NodoBase getPrueba() {
		return prueba;
	}

	public void setPrueba(NodoBase prueba) {
		this.prueba = prueba;
	}
}