package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;

public class TypeInferer implements AstTraverser<Datatype> {
	
	public static void runOn( ArithmeticExpression expression ){
		TypeInferer inferer = new TypeInferer();
		expression.accept( inferer );
	}
	
	@Override
	public Datatype visit( NumericLiteral op ){
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Datatype visitPost( UnaryOperation op, Datatype left ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datatype visitPost( BinaryOperation op, Datatype left, Datatype right ){
		// TODO Auto-generated method stub
		return null;
	}

}
