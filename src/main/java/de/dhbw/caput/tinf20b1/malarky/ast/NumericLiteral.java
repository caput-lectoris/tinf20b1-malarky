package de.dhbw.caput.tinf20b1.malarky.ast;

import de.dhbw.caput.tinf20b1.malarky.Datatype;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.AstTraverser;

public class NumericLiteral extends ArithmeticExpression {
	
	private final String LEXEME;
	public final String VALUE;
	
	public NumericLiteral( String lexeme ){
		super( );
		LEXEME = lexeme;
		int split = positionOfSuffix( LEXEME );
		VALUE = LEXEME.substring(0, split);
		String suffix = LEXEME.substring( split );
		if( suffix.isEmpty() ){
			datatype = Datatype.I32;
		}else{
			datatype = Datatype.evaluateTypeSuffix( suffix );
		}
	}
	
	private static int positionOfSuffix( String literal ){
		int pos = 0;
		for( pos = 0; pos < literal.length(); ++pos ){
			switch( literal.charAt(pos) ){
				case 'f':
				case 'i':
				case 'u':
					return pos;
			}
		}
		return pos;
	}
	
	@Override
	public String toString( ){
		return String.format( "%s%s", VALUE, datatype );
	}
	
	@Override
	public <T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}

}
