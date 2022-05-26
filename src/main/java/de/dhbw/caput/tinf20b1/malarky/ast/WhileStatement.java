package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class WhileStatement extends Statement {
	
	public final ArithmeticExpression CONDITION;
	public final BlockStatement STATEMENTS;
	
	public WhileStatement( ArithmeticExpression condition, BlockStatement statements ){
		super( );
		CONDITION = condition;
		STATEMENTS = statements;
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString() {
		return String.format( "while(%s){%s}", CONDITION, STATEMENTS );
	}

}
