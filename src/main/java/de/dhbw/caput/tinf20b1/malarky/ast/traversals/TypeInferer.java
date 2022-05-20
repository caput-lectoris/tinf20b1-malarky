package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;

public class TypeInferer implements AstTraverser<Datatype> {
	
	public static void runOn( ArithmeticExpression expression ){
		TypeInferer inferer = new TypeInferer();
		expression.accept( inferer );
	}
	
	@Override
	public Datatype visit( NumericLiteral literal ){
		return literal.datatype( );
	}
	
	@Override
	public Datatype visitPost( UnaryOperation op, Datatype base ){
		op.set( base );
		return op.datatype( );
	}

	@Override
	public Datatype visitPost( BinaryOperation op, Datatype left, Datatype right ){
		op.set( Datatype.getPrincipleType(left, right) );
		return op.datatype( );
	}

	@Override
	public Datatype visitPost( TypeCast cast, Datatype base ){
		return cast.datatype();
	}

}
