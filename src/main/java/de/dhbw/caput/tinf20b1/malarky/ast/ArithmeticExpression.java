package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.Datatype;

public abstract class ArithmeticExpression extends AstNode {
	
	protected Datatype datatype;
	
	public void set( Datatype datatype ){
		this.datatype = datatype;
	}
	
	public Datatype datatype( ){
		return datatype;
	}

}
