package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class Variable extends ArithmeticExpression {
	
	private VariableDeclaration declaration;
	public final String NAME;
	
	public Variable( String name ){
		super( );
		NAME = name;
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
		return NAME;
	}

}
