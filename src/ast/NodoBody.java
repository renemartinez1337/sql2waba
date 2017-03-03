package ast;

public class NodoBody extends NodoBase {
	private NodoBase cuerpo;

	public NodoBody(NodoBase cuerpo) {
		super();
		this.cuerpo= cuerpo;
	}

	public NodoBody() {
		super();
		this.cuerpo=cuerpo;
	}

	public NodoBase  getcuerpo() {
		return cuerpo;
	}

	public void setcuerpo(NodoBase  cuerpo) {
		this.cuerpo = cuerpo;
	}

}
