package de.dhbw.caput.tinf20b1;

abstract class ArithmeticExpression {
	
	abstract <T> T accept( AstTraverser<T> traverser );

}
