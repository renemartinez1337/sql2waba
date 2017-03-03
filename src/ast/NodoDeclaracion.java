package ast;

public class NodoDeclaracion extends NodoBase {
    private NodoBase  inicio,fin;
    String tipo;
    NodoBase variable,expresionasignacion;
    private int numlinea;
    public NodoDeclaracion(NodoBase variable){
        this.variable = variable;
    }
    public NodoDeclaracion(String tipo, NodoBase variable){
        this.tipo = tipo;
        this.variable = variable;
        
    }
    public NodoDeclaracion(String tipo, NodoBase variable,int numlinea){
        this.tipo = tipo;
        this.variable = variable;
        this.numlinea = numlinea;
        
    }
    public NodoDeclaracion(String tipo, NodoBase variable,NodoBase expresionasignacion){
        this.tipo = tipo;
        this.variable = variable;
        this.expresionasignacion = expresionasignacion;
        
    }
    public NodoDeclaracion(String tipo, NodoBase variable, NodoBase  inicio,NodoBase  fin,int numlinea){
        this.tipo = tipo;
        this.variable = variable;
        this.inicio=inicio;
        this.fin=fin;
        this.numlinea = numlinea;
    }
    public NodoBase getVariable(){
        return variable;
    }
    public void setVariable(NodoBase variable){
        this.variable = variable;
    }
    public NodoBase getexpresionasignacion(){
        return expresionasignacion;
    }
    public void setexpresionasignacion(NodoBase expresionasignacion){
        this.expresionasignacion = expresionasignacion;
    }
    public String getTipo(){
        return tipo;
    }
    public int getnumlinea(){
        return numlinea;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
}