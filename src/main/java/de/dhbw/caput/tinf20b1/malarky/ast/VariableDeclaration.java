package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class VariableDeclaration extends Statement {
	
	public final String NAME;
	public final Datatype DATATYPE;
	private int slot;

	public VariableDeclaration( String name, Datatype datatype ){
		super( );
		NAME = name;
		DATATYPE = datatype;
	}
	
	public void setVariableTableSlot( int slot ){
		this.slot = slot;
	}
	
	public int getVariableTableSlot( ){
		return slot;
	}

	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}
	
	@Override
	public String toString( ){
		return String.format( "%s %s;", DATATYPE, NAME );
	}
	
}
