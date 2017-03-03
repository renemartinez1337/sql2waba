package ast;

public class NodoValor extends NodoBase {
	private int valor=-1;
	private boolean valorb,isboolean=false;
	
	


	public NodoValor(int valor) {
		super();
		this.valor = valor;
		this.isboolean=false;
	}
	public NodoValor(boolean valorb) {
		super();
		this.valorb = valorb;
		this.isboolean=true;
	}

	public NodoValor() {
		super();
	}
	
	public int getValor() {
		
		return valor;
	}
	public boolean getValorb() {
		
		return valorb;
	}
public boolean getisboolean() {
		
		return isboolean;
	}

	
	
}
