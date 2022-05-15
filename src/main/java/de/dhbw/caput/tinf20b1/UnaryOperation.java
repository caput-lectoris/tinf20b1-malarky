package de.dhbw.caput.tinf20b1;

class UnaryOperation extends ArithmeticExpression {

	enum Type {
		NEGATION( '-' );

		private final char OPERATOR;

		private Type( char operator ){
			OPERATOR = operator;
		}
	};
	
	final ArithmeticExpression BASE;
	final Type TYPE;

	UnaryOperation( ArithmeticExpression base, Type type ){
		super( );
		BASE = base;
		TYPE = type;
	}
	
	@Override
	public String toString() {
		return String.format( "%c(%s)", TYPE.OPERATOR, BASE );
	}
	
	@Override
	<T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
}
