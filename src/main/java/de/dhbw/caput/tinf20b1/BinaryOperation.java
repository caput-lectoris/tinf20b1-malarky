package de.dhbw.caput.tinf20b1;

class BinaryOperation extends ArithmeticExpression {
	
	enum Type {
		ADDITION( '+' ), SUBTRACTION( '-' ), MULTIPLICATION( '*' ),
		DIVISION( '/' ), POWER( '^' );
		
		private final char OPERATOR;
		
		private Type( char operator ){
			OPERATOR = operator;
		}
	};
	
	final ArithmeticExpression LEFT, RIGHT;
	final Type TYPE;

	BinaryOperation( ArithmeticExpression left, Type type, ArithmeticExpression right ){
		super( );
		LEFT = left;
		TYPE = type;
		RIGHT = right;
	}
	
	@Override
	public String toString( ){
		return String.format( "(%s %c %s)", LEFT, TYPE.OPERATOR, RIGHT );
	}
	
}
