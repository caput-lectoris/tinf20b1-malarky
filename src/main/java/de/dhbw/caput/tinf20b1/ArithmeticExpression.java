package de.dhbw.caput.tinf20b1;

abstract class ArithmeticExpression {
	
	protected Datatype datatype;
	
	void set( Datatype datatype ){
		this.datatype = datatype;
	}
	
	Datatype datatype( ){
		return datatype;
	}
	
	abstract <T> T accept( AstTraverser<T> traverser );

}
