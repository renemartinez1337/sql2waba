package ast;

public class NodoSelect extends NodoBase {
	private NodoBase columnas;
	private NodoBase from;
	private NodoBase join;
	private NodoBase join2;
	private NodoBase where;
	
	public NodoSelect(){
		super();
		this.columnas = null;
		this.from = null;
		this.join = null;
		this.join2 = null;
		this.where = null;		
	}
	
	public NodoSelect(NodoBase columnas, NodoBase from, NodoBase where){
		super();
		this.columnas = columnas;
		this.from = from;
		this.join = null;
		this.join2 = null;
		this.where = where;
	}

	public NodoSelect(NodoBase columnas, NodoBase from, NodoBase join, NodoBase where){
		super();
		this.columnas = columnas;
		this.from = from;
		this.join = join;
		this.join2 = null;
		this.where = where;
	}	
	
	public NodoSelect(NodoBase columnas, NodoBase from, NodoBase join, NodoBase join2, NodoBase where){
		super();
		this.columnas = columnas;
		this.from = from;
		this.join = join;
		this.join2 = join2;
		this.where = where;
	}

	public NodoBase getColumnas(){
		return columnas;
	}

	public NodoBase getFrom(){
		return from;
	}

	public NodoBase getJoin(){		
		return join;
	}

	public NodoBase getJoin2(){		
		return join2;
	}	
	
	public NodoBase getWhere(){		
		return where;
	}	
}

