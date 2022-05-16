package de.dhbw.caput.tinf20b1;

import de.dhbw.caput.tinf20b1.jasmine.JasminMethodBody;

class JavaBytecodeGenerator implements AstTraverser<String> {
	
	private final JasminMethodBody BODY;
	
	JavaBytecodeGenerator( JasminMethodBody body ){
		BODY = body;
	}

	@Override
	public String visit( NumericLiteral literal ){
		BODY.appendLine( String.format("%s %s", "bipush", literal.VALUE) );
		return null;
	}

	@Override
	public String visitPost( UnaryOperation op, String left ){
		BODY.appendLine( "ineg" );
		return null;
	}

	@Override
	public String visitPost( BinaryOperation op, String left, String right ){
		switch( op.TYPE ){
			case ADDITION: BODY.appendLine( "iadd" ); return null;
			case SUBTRACTION: BODY.appendLine( "isub" ); return null;
			case MULTIPLICATION: BODY.appendLine( "imul" ); return null;
			case DIVISION: BODY.appendLine( "idiv" ); return null;
			case POWER: BODY.appendLine( "ipow" ); return null;
		}
		throw new RuntimeException();
	}

}