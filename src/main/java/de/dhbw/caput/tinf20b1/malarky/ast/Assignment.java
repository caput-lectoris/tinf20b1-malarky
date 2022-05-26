package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class Assignment extends Statement {
	
	private VariableDeclaration declaration;
	public final String NAME;
	public final ArithmeticExpression EXPRESSION;

	public Assignment( String name, ArithmeticExpression expression ){
		super( );
		NAME = name;
		EXPRESSION = expression;
	}
	
	public void setDeclaration( VariableDeclaration decl ){
		this.declaration = decl;
	}
	
	public VariableDeclaration getDeclaration( ){
		return declaration;
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString( ){
		return String.format( "%s = %s;", NAME, EXPRESSION );
	}

}
