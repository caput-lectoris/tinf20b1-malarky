package de.dhbw.caput.tinf20b1;

interface AstTraverser<T> {
	
	T visit( NumericLiteral literal );
	
	default T visit( UnaryOperation op ){
		T base = op.BASE.accept( this );
		return visitPost( op, base);
	}
	T visitPost( UnaryOperation binOp, T left );
	
	default T visit( BinaryOperation op ){
		T left = op.LEFT.accept( this );
		T right = op.RIGHT.accept( this );
		return visitPost( op, left, right );
	}
	T visitPost( BinaryOperation binOp, T left, T right );

}
