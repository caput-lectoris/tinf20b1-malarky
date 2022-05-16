package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class BinaryOperation extends ArithmeticExpression {
	
	public enum Type {
		ADDITION( '+' ), SUBTRACTION( '-' ), MULTIPLICATION( '*' ),
		DIVISION( '/' ), POWER( '^' );
		
		private final char OPERATOR;
		
		private Type( char operator ){
			OPERATOR = operator;
		}
	};
	
	public final ArithmeticExpression LEFT, RIGHT;
	public final Type TYPE;

	public BinaryOperation( ArithmeticExpression left, Type type, ArithmeticExpression right ){
		super( );
		LEFT = left;
		TYPE = type;
		RIGHT = right;
	}
	
	@Override
	public String toString( ){
		return String.format( "(%s %c %s)", LEFT, TYPE.OPERATOR, RIGHT );
	}
	
	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
}
