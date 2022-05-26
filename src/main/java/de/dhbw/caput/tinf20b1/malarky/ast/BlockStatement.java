package de.dhbw.caput.tinf20b1.malarky.ast;

import java.util.LinkedList;
import java.util.List;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class BlockStatement extends AstNode {
	
	public final List<Statement> STATEMENTS;
	
	public BlockStatement( ){
		super( );
		STATEMENTS = new LinkedList<>( );
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString( ){
		StringBuilder builder = new StringBuilder( );
		for( Statement statement : STATEMENTS ){
			builder.append( statement.toString() );
		}
		return builder.toString();
	}

}
