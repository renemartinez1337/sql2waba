package ast;

public class NodoBloqueDec extends NodoBase {
    private NodoBase  inicio,fin;
    String tipo;
    NodoBase variable;
    public NodoBloqueDec(NodoBase variable){
        this.variable = variable;
    }
    public NodoBloqueDec(String tipo, NodoBase variable){
        this.tipo = tipo;
        this.variable = variable;
        
    }
 
    public NodoBase getVariable(){
        return variable;
    }
    public void setVariable(NodoBase variable){
        this.variable = variable;
    }
    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}