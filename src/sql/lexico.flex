package sql;

import java_cup.runtime.*;
//import otros.*;

%%
/* Habilitar la compatibilidad con el interfaz CUP para el generador sintactico*/
%cup
/* Llamar Scanner a la clase que contiene el analizador Lexico */
%class Scanner

/*-- DECLARACIONES --*/
%{
	public Scanner(java.io.InputStream r, DefaultSymbolFactory sf){
		this(r);
		this.sf=sf;
		lineanum=0;
		debug=true;
	}
	private DefaultSymbolFactory sf;
	private int lineanum;
	private boolean debug,error;

boolean error(){
		return this.error;
	}

/******************************************************************
BORRAR SI NO SE NECESITA
	//TODO: Cambiar la SF por esto o ver que se hace
	//Crear un nuevo objeto java_cup.runtime.Symbol con informaci�n sobre el token actual sin valor
 	  private Symbol symbol(int type){
    		return new Symbol(type,yyline,yycolumn);
	  }
	//Crear un nuevo objeto java_cup.runtime.Symbol con informaci�n sobre el token actual con valor
	  private Symbol symbol(int type,Object value){
    		return new Symbol(type,yyline,yycolumn,value);
	  }
******************************************************************/
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

/* Acceso a la columna y fila actual de analisis CUP */
%line
%column



digito		= [0-9]
numero		= {digito}+
cadena			= [a-zA-Z_0-9]
letras = [a-zA-Z]
letrasCadena = {letras}+{cadena}*
identificador	= {letrasCadena}
nuevalinea		= \n | \n\r | \r\n
espacio		= [ \t]+
%%


"select"            {	if(debug) System.out.println("token SELECT");
			return sf.newSymbol("SELECT",sym.SELECT,yyline);
			}
"from"            {	if(debug) System.out.println("token FROM");
			return sf.newSymbol("FROM",sym.FROM,yyline);
			}
"where"            {	if(debug) System.out.println("token WHERE");
			return sf.newSymbol("WHERE",sym.WHERE,yyline);
			}
"count"            {	if(debug) System.out.println("token COUNT");
			return sf.newSymbol("COUNT",sym.COUNT,yyline);
			}
"and"               {	if(debug) System.out.println("token AND");
			return sf.newSymbol("AND",sym.AND,yyline);
			}
"or"                {	if(debug) System.out.println("token OR");
			return sf.newSymbol("OR",sym.OR,yyline);
			}
"<"                 {	if(debug) System.out.println("token LT");
			return sf.newSymbol("LESST",sym.LT,yyline);
			}
">"                 {	if(debug) System.out.println("token GT");
			return sf.newSymbol("GT",sym.GT,yyline);
			}
","             {	if(debug) System.out.println("token COLOM");
			return sf.newSymbol("COLOM",sym.COLOM,yyline);
			}
"="             {	if(debug) System.out.println("token ASSIGN");
			return sf.newSymbol("ASSIGN",sym.ASSIGN,yyline);
			}
";"             {	if(debug) System.out.println("token SEMI");
			return sf.newSymbol("SEMI",sym.SEMI,yyline);
			}
"."             {	if(debug) System.out.println("token DOT");
			return sf.newSymbol("DOT",sym.DOT,yyline);
			}
"*"             {	if(debug) System.out.println("token ALL");
			return sf.newSymbol("ALL",sym.ALL,yyline);
			}
{numero}        {	if(debug) System.out.println("token NUM");
			return sf.newSymbol("NUM",sym.NUM,new String(yytext()));
			}
{identificador}	{	if(debug) System.out.println("token ID");
				return sf.newSymbol("ID",sym.ID,new String(yytext()));
			}
{nuevalinea}       {lineanum++;}
{espacio}    { /* saltos espacios en blanco*/}



.               {System.err.println("[Error Lexico] Caracter Ilegal encontrado en analisis lexico: " + yytext() + "\n"); error=true;}