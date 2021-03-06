package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import java.util.LinkedList;
import java.util.List;

import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
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
	
	default T visit( VariableDeclaration decl ){
		return visitPost( decl );
	}
	T visitPost( VariableDeclaration decl );
	
	default T visit( Assignment assignment ){
		T expr = assignment.EXPRESSION.accept( this );
		return visitPost( assignment, expr );
	}
	T visitPost( Assignment assignment, T expr );
	
	default T visit( BlockStatement block ){
		List<T> stmts = new LinkedList<>();
		for( Statement statement : block.STATEMENTS ){
			T stmt = statement.accept( this );
			stmts.add( stmt );
		}
		return visitPost( block, stmts );
	}
	T visitPost( BlockStatement block, List<T> stmts );
	
	default T visit( Variable var ){
		return visitPost( var );
	}
	T visitPost( Variable var );
	
	default T visit( WhileStatement stmt ){
		return visitPost( stmt );
	}
	T visitPost( WhileStatement stmt );
	
	default T visit( IfStatement stmt ){
		T condition = stmt.CONDITION.accept( this );
		T ifBlock = stmt.IF_BLOCK.accept( this );
		T elseBlock = stmt.ELSE_BLOCK.accept( this );
		return visitPost( stmt, condition, ifBlock, elseBlock );
	}
	T visitPost( IfStatement stmt, T condition, T ifBlock, T elseBlock );

}
