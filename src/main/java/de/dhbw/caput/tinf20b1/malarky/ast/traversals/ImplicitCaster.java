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
	public ArithmeticExpression visit( NumericLiteral literal ){
		return literal;
	}

	@Override
	public ArithmeticExpression visitPost( UnaryOperation op, ArithmeticExpression base ){
		return new UnaryOperation( (ArithmeticExpression) base, op.TYPE );
	}

	@Override
	public ArithmeticExpression visitPost( BinaryOperation op, ArithmeticExpression left, ArithmeticExpression right ){
		if( op.datatype() != left.datatype() ){
			BinaryOperation newOp = new BinaryOperation(
					new TypeCast(op.datatype(), left), op.TYPE, right );
			newOp.set( op.datatype() );
			return newOp;
		}
		if( op.datatype() != right.datatype() ){
			BinaryOperation newOp =  new BinaryOperation(
					left, op.TYPE, new TypeCast(op.datatype(), right) );
			newOp.set( op.datatype() );
			return newOp;
		}
		return op;
	}

	@Override
	public ArithmeticExpression visitPost( TypeCast cast, ArithmeticExpression base ){
		return new TypeCast( cast.datatype(), (ArithmeticExpression)base );
	}

}
