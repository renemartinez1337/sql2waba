package sql;

public class RegistroSimbolo {
	private String identificador;
	private String tipo;
	private int NumLinea;
	private int DireccionMemoria;
	private int valor;
	
	public RegistroSimbolo(String identificador, String Tipo, int numLinea,
			int direccionMemoria) {
		super();
		this.identificador = identificador;
		tipo = Tipo;
		NumLinea = numLinea;
		DireccionMemoria = direccionMemoria;
	}

	public String getIdentificador() {
		return identificador;
	}
	
	public String getTipo() {
		return tipo;
	}

	public int getNumLinea() {
		return NumLinea;
	}

	public int getDireccionMemoria() {
		return DireccionMemoria;
	}


	public void setDireccionMemoria(int direccionMemoria) {
		DireccionMemoria = direccionMemoria;
	}
	public void setvalor(int valor) {
		this.valor = valor;
	}
}
