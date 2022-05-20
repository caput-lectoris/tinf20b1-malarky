package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;

public class ImplicitCaster implements AstTraverser<ArithmeticExpression> {
	
	public static ArithmeticExpression runOn( ArithmeticExpression expression ){
		ImplicitCaster inferer = new ImplicitCaster( );
		return expression.accept( inferer );
	}

	@Override
	public ArithmeticExpression visit(NumericLiteral literal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArithmeticExpression visitPost(UnaryOperation op, ArithmeticExpression base) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArithmeticExpression visitPost(BinaryOperation op, ArithmeticExpression left, ArithmeticExpression right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArithmeticExpression visitPost(TypeCast cast, ArithmeticExpression base) {
		// TODO Auto-generated method stub
		return null;
	}

}
