package de.dhbw.caput.tinf20b1;

import static org.assertj.core.api.Assertions.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.dhbw.caput.tinf20b1.malarky.MalarkyLexer;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser;

class CompilerTest {
	
	@Test
	void syntacticAnalysis( ){
		String expression = "42 / 2i8 ^ 3 ^ 5 / 7i16";
		MalarkyLexer lexer = new MalarkyLexer( CharStreams.fromString(expression) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		MalarkyParser parser = new MalarkyParser( tokens );
		ParseTree tree = parser.program();
		AstBuilder astBuilder = new AstBuilder( );
		ArithmeticExpression ast = astBuilder.visit( tree );
		assertThat( ast.toString() ).isEqualTo( "((42i32 / (2i8 ^ (3i32 ^ 5i32))) / 7i16)" );
	}

}
