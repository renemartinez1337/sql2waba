package ast;

public class NodoProcedure extends NodoBase {
    private String tipo;
    private NodoIdentificador identificador;
    private NodoBase argumento;
    private NodoBase cuerpo;

    public NodoProcedure(String tipo, NodoIdentificador identificador) {
        this.tipo = tipo;
        this.identificador = identificador;   
    }
    public NodoProcedure(NodoIdentificador identificador, NodoBase variable, NodoBase cuerpo) {
        super();
        this.identificador = identificador;
        this.argumento = variable;
        this.cuerpo = cuerpo;
        
    }
    public NodoIdentificador getIdentificador() {
        return identificador;
    }
    public void setIdentificador(NodoIdentificador identificador) {
        this.identificador = identificador;
    }
    public NodoBase getcuerpo() {
    	return cuerpo;
    }
    public NodoBase getArgumento() {
        return argumento;
    }
    public void setArgumento(NodoBase argumento) {
        this.argumento = argumento;
    }
}