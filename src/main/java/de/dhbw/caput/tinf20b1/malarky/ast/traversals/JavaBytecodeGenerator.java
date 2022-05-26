package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import java.util.List;

import de.dhbw.caput.tinf20b1.jasmine.JasminMethodBody;
import de.dhbw.caput.tinf20b1.malarky.InstructionSet;
import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.IfStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.Variable;
import de.dhbw.caput.tinf20b1.malarky.ast.VariableDeclaration;
import de.dhbw.caput.tinf20b1.malarky.ast.WhileStatement;

public class JavaBytecodeGenerator implements AstTraverser<String> {
	
	private final JasminMethodBody BODY;
	
	public JavaBytecodeGenerator( JasminMethodBody body ){
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

	@Override
	public String visitPost( TypeCast cast, String base ){
		String instruction = InstructionSet.findBestMatchFor( cast );
		return String.format( "%s\n%s", base, instruction );
	}
	
	@Override
	public String visitPost( VariableDeclaration decl ){
		BODY.appendLine( "iconst_0" );
		BODY.appendLine( "istore " + decl.getVariableTableSlot() );
		return null;
	}
	
	@Override
	public String visitPost( Assignment assignment, String expr ){
		BODY.appendLine( "istore " + assignment.getDeclaration().getVariableTableSlot() );
		return null;
	}
	
	@Override
	public String visitPost( BlockStatement block, List<String> statements ){
		return null;
	}

	@Override
	public String visitPost( Variable var ){
		BODY.appendLine( "iload " + var.getDeclaration().getVariableTableSlot() );
		return null;
	}

	@Override
	public String visitPost( WhileStatement var ){
		return null;
	}
	
	@Override
	public String visitPost( IfStatement stmt, String condition, String ifBlock, String elseBlock ){
		return null;
	}

}
