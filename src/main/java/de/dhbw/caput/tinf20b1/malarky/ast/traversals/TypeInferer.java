package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import java.util.List;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.AstNode;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.Variable;
import de.dhbw.caput.tinf20b1.malarky.ast.VariableDeclaration;

public class TypeInferer implements AstTraverser<Datatype> {
	
	public static void runOn( AstNode expression ){
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
	
	@Override
	public Datatype visitPost( VariableDeclaration decl ){
		return decl.DATATYPE;
	}
	
	@Override
	public Datatype visitPost( Assignment assignment, Datatype expr ){
		Datatype variableDatatype = assignment.getDeclaration().DATATYPE;
		Datatype principleType = Datatype.getPrincipleType( variableDatatype, expr );
		return principleType;
	}
	
	@Override
	public Datatype visitPost( BlockStatement block, List<Datatype> statements ){
		return null;
	}

	@Override
	public Datatype visitPost( Variable var ){
		return var.getDeclaration().DATATYPE;
	}

}
