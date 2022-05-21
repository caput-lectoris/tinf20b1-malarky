package de.dhbw.caput.tinf20b1.malarky;

import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;

public class InstructionSet {

	public static String findBestMatchFor( BinaryOperation.Type op, Datatype type ){
		String instruction = instructionPrefix( type );
		switch( op ){
			case ADDITION:       return instruction + "add";
			case SUBTRACTION:    return instruction + "sub";
			case MULTIPLICATION: return instruction + "mul";
			case DIVISION:       return instruction + "div";
			case POWER:          return instruction + "pow";
		}
		throw new RuntimeException();
	}
	
	public static String findBestMatchFor( NumericLiteral literal ){
		switch( literal.datatype() ){
			case I8:  return "bipush";
			case I16: return "sipush";
			case I32: return "ldc " + literal.VALUE;
			case I64: return "ldc " + literal.VALUE;
			case F32: return "ldc " + literal.VALUE;
			case F64: return "ldc " + literal.VALUE;
		}
		throw new RuntimeException();
	}
	
	public static String findBestMatchFor( TypeCast cast ){
		String targetType = instructionPrefix( cast.datatype() );
		String originalType = instructionPrefix( cast.BASE.datatype() );
		return String.format( "%c2%c", originalType, targetType );
	}
	
	
	private static String instructionPrefix( Datatype datatype ){
		switch( datatype ){
			case I8: case I16: case I32:
				return "i";
			case I64:
				return "l";
			case F32:
				return "f";
			case F64:
				return "d";
		}
		throw new RuntimeException();
	}
	
}
