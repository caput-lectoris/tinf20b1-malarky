package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public abstract class AstNode {
	
	public abstract <T> T accept( AstTraverser<T> traverser );

}
