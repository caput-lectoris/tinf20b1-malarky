package de.dhbw.caput.tinf20b1;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.dhbw.caput.tinf20b1.jasmine.JasminDefault;
import de.dhbw.caput.tinf20b1.jasmine.Jasmine;
import de.dhbw.caput.tinf20b1.malarky.MalarkyLexer;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser;

class CompilerTest {
	
	@Test
	void syntacticAnalysis( ){
		ArithmeticExpression ast = parse( "42 / 2i8 ^ 3 ^ 5 / 7i16" );
		assertThat( ast.toString() ).isEqualTo( "((42i32 / (2i8 ^ (3i32 ^ 5i32))) / 7i16)" );
	}
	
	
	@Test
	void typeInference( ){
		ArithmeticExpression ast = parse( "42 + 2 * -3f32 * 5/7" );
		assertThat( ast.datatype() ).isNull( );
		TypeInferer.runOn( ast );
		assertThat( ast.datatype() ).isEqualTo( Datatype.F32 );
	}
	
	
	@Test
	void codeGeneration( ) throws Exception {
		InputStream solution = load( "GeneratedBytecode.j" );
		ArithmeticExpression ast = parse( "42 + 2 * -3 * 5/7" );
		
		JasminDefault jasmine = new JasminDefault();
		JavaBytecodeGenerator generator = new JavaBytecodeGenerator( jasmine.MAIN_METHOD.BODY );
		jasmine.MAIN_METHOD.BODY.appendLine( "getstatic java/lang/System/out Ljava/io/PrintStream;" );
		ast.accept( generator );
		jasmine.MAIN_METHOD.BODY.appendLine( "getstatic java/lang/System/out Ljava/io/PrintStream;" );
		jasmine.MAIN_METHOD.BODY.appendLine( "ldc \"The result is: \"" );
		jasmine.MAIN_METHOD.BODY.appendLine( "invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V" );
		jasmine.MAIN_METHOD.BODY.appendLine( "invokevirtual java/io/PrintStream/println(I)V" );
		jasmine.MAIN_METHOD.BODY.appendLine( "return" );
		jasmine.MAIN_METHOD.setStackLimit(4);
		jasmine.MAIN_METHOD.setLocalsLimit(2);
		
		String expectedOutput = new String( solution.readAllBytes(), StandardCharsets.UTF_8 );
		assertThat( jasmine.toString() ).isEqualTo( expectedOutput );
		
		String result;
		try( InputStream in = new ByteArrayInputStream(jasmine.toString().getBytes()) ){
			result = Jasmine.execute( in );
		}
		assertThat( result ).isEqualTo( "The result is: 38" + System.lineSeparator() );
	}
	
	
	private ArithmeticExpression parse( String expression ){
		MalarkyLexer lexer = new MalarkyLexer( CharStreams.fromString(expression) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		MalarkyParser parser = new MalarkyParser( tokens );
		ParseTree tree = parser.program();
		AstBuilder astBuilder = new AstBuilder( );
		ArithmeticExpression ast = astBuilder.visit( tree );
		return ast;
	}
	
	
	private InputStream load( String filename ) throws Exception {
		InputStream in = CompilerTest.class.getResourceAsStream(filename);
		if( null == in ){
			throw new IllegalArgumentException( String.format("No such file \"%s\".", filename) );
		}
		return in;
	}

}
