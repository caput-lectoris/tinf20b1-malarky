package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class TypeCast extends ArithmeticExpression {
	
	public final ArithmeticExpression BASE;
	
	public TypeCast( Datatype type, ArithmeticExpression expression ){
		super( );
		BASE = expression;
		datatype = type;
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString( ){
		return String.format( "%s(%s)", datatype(), BASE );
	}

}
