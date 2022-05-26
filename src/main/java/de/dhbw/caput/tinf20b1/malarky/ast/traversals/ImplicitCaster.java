package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import java.util.List;

import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.AstNode;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.IfStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.Statement;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.Variable;
import de.dhbw.caput.tinf20b1.malarky.ast.VariableDeclaration;
import de.dhbw.caput.tinf20b1.malarky.ast.WhileStatement;

public class ImplicitCaster implements AstTraverser<AstNode> {
	
	public static AstNode runOn( AstNode tree ){
		ImplicitCaster caster = new ImplicitCaster( );
		return tree.accept( caster );
	}

	@Override
	public ArithmeticExpression visit( NumericLiteral literal ){
		return literal;
	}

	@Override
	public ArithmeticExpression visitPost( UnaryOperation op, AstNode base ){
		return new UnaryOperation( (ArithmeticExpression) base, op.TYPE );
	}

	@Override
	public ArithmeticExpression visitPost( BinaryOperation op, AstNode left, AstNode right ){
		ArithmeticExpression _left = (ArithmeticExpression) left;
		ArithmeticExpression _right = (ArithmeticExpression) right;
		if( op.datatype() != _left.datatype() ){
			BinaryOperation newOp = new BinaryOperation(
					new TypeCast(op.datatype(), _left), op.TYPE, _right );
			newOp.set( op.datatype() );
			return newOp;
		}
		if( op.datatype() != _right.datatype() ){
			BinaryOperation newOp =  new BinaryOperation(
					_left, op.TYPE, new TypeCast(op.datatype(), _right) );
			newOp.set( op.datatype() );
			return newOp;
		}
		return op;
	}

	@Override
	public ArithmeticExpression visitPost( TypeCast cast, AstNode base ){
		return new TypeCast( cast.datatype(), (ArithmeticExpression)base );
	}
	
	@Override
	public AstNode visitPost( VariableDeclaration decl ){
		return decl;
	}
	
	@Override
	public AstNode visitPost( Assignment assignment, AstNode expr ){
		return new Assignment( assignment.NAME, (ArithmeticExpression)expr );
	}
	
	@Override
	public AstNode visitPost( BlockStatement block, List<AstNode> statements ){
		BlockStatement newBlock = new BlockStatement();
		for( AstNode astNode : statements ){
			newBlock.STATEMENTS.add( (Statement)astNode );
		}
		return newBlock;
	}

	@Override
	public AstNode visitPost( Variable var ){
		return var;
	}

	@Override
	public AstNode visitPost( WhileStatement var ){
		return null;
	}
	
	@Override
	public AstNode visitPost( IfStatement stmt, AstNode condition, AstNode ifBlock, AstNode elseBlock ){
		return null;
	}

}
