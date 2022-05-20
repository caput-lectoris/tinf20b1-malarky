package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;

public interface AstTraverser<T> {
	
	T visit( NumericLiteral literal );
	
	default T visit( UnaryOperation op ){
		T base = op.BASE.accept( this );
		return visitPost( op, base);
	}
	T visitPost( UnaryOperation op, T base );
	
	default T visit( BinaryOperation op ){
		T left = op.LEFT.accept( this );
		T right = op.RIGHT.accept( this );
		return visitPost( op, left, right );
	}
	T visitPost( BinaryOperation op, T left, T right );
	
	default T visit( TypeCast cast ){
		T base = cast.BASE.accept( this );
		return visitPost( cast, base);
	}
	T visitPost( TypeCast cast, T base );

}
