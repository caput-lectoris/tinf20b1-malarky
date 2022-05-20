package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public abstract class ArithmeticExpression {
	
	protected Datatype datatype;
	
	public void set( Datatype datatype ){
		this.datatype = datatype;
	}
	
	public Datatype datatype( ){
		return datatype;
	}
	
	public abstract <T> T accept( AstTraverser<T> traverser );

}
