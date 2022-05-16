package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class UnaryOperation extends ArithmeticExpression {

	public enum Type {
		NEGATION( '-' );

		private final char OPERATOR;

		private Type( char operator ){
			OPERATOR = operator;
		}
	};
	
	public final ArithmeticExpression BASE;
	public final Type TYPE;

	public UnaryOperation( ArithmeticExpression base, Type type ){
		super( );
		BASE = base;
		TYPE = type;
	}
	
	@Override
	public String toString() {
		return String.format( "%c(%s)", TYPE.OPERATOR, BASE );
	}
	
	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
}
