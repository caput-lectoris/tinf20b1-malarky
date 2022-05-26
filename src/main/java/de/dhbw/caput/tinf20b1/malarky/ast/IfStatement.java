package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class IfStatement extends Statement {
	
	public final ArithmeticExpression CONDITION;
	public final BlockStatement IF_BLOCK, ELSE_BLOCK;
	
	public IfStatement( ArithmeticExpression condition, BlockStatement ifBlock, BlockStatement elseBlock ){
		super( );
		CONDITION = condition;
		IF_BLOCK = ifBlock;
		ELSE_BLOCK = elseBlock;
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString( ){
		return String.format( "if(%s){%s}else{%s}", CONDITION, IF_BLOCK, ELSE_BLOCK );
	}

}
