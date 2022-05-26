package de.dhbw.caput.tinf20b1.malarky;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import de.dhbw.caput.tinf20b1.jasmine.JasminDefault;
import de.dhbw.caput.tinf20b1.jasmine.Jasmine;
import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.AstNode;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.DeclarationFinder;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.ImplicitCaster;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.JavaBytecodeGenerator;
import de.dhbw.caput.tinf20b1.malarky.ast.traversals.TypeInferer;

class CompilerTest {
	
	@Test
	void whileStatement( ){
		AstNode ast = parse( "let x : i32; while( x ){ x := 5; }" );
		assertThat( ast.toString() ).isEqualTo( "i32 x;while(x){x = 5i32;}" );
	}
	
	
	@Test
	void syntacticAnalysis( ){
		AstNode ast = parse( "let x : i32; x := 42 / 2i8 ^ 3 ^ 5 / 7i16 ;" );
		assertThat( ast.toString() ).isEqualTo( "i32 x;x = ((42i32 / (2i8 ^ (3i32 ^ 5i32))) / 7i16);" );
	}
	
	
	@Test
	void declarationFinder1( ){
		AstNode ast = parse( "let x : i32; x := 42 / 2i8 ^ 3 ^ 5 / 7i16 ;" );
		BlockStatement block = (BlockStatement) ast;
		Assignment root = (Assignment) block.STATEMENTS.get(1);
		assertThat( root.getDeclaration() ).isNull();
		DeclarationFinder.runOn( ast );
		assertThat( root.getDeclaration() ).isNotNull();
	}
	
	@Test
	void declarationFinder2( ){
		AstNode ast = parse( "let y : i32; x := 42 / 2i8 ^ 3 ^ 5 / 7i16 ;" );
		assertThrows( RuntimeException.class, () -> {
			DeclarationFinder.runOn( ast );
		}, "variable must be declared before use" );
	}
	
	
	@Test
	void typeInference( ){
		AstNode ast = parse( "let x : f32; x := 42 + 2 * -3f32 * 5/7 ;" );
		DeclarationFinder.runOn( ast );
		BlockStatement block = (BlockStatement) ast;
		Assignment root = (Assignment) block.STATEMENTS.get(1);
		assertThat( root.EXPRESSION.datatype() ).isNull( );
		TypeInferer.runOn( ast );
		assertThat( root.EXPRESSION.datatype() ).isEqualTo( Datatype.F32 );
	}
	
	
	@Test
	void implicitCasting( ){
		AstNode ast = parse( "let x : f32; x := 42 / 2i8 ^ 3 ^ 5f32 / 7i16 ;" );
		DeclarationFinder.runOn( ast );
		
		TypeInferer.runOn( ast );
		AstNode ast2 = ImplicitCaster.runOn( ast );
		assertThat( ast2.toString() ).isEqualTo( "f32 x;x = ((f32(42i32) / (f32(2i8) ^ (f32(3i32) ^ 5f32))) / f32(7i16));" );
	}
	
	
	@Test
	void codeGeneration( ) throws Exception {
		InputStream solution = load( "GeneratedBytecode.j" );
		AstNode ast = parse( "let x : i32; x := 2; x := 42 + x * -3 * 5/7 ;" );
		DeclarationFinder.runOn( ast );
		
		JasminDefault jasmine = new JasminDefault();
		JavaBytecodeGenerator generator = new JavaBytecodeGenerator( jasmine.MAIN_METHOD.BODY );
		jasmine.MAIN_METHOD.BODY.appendLine( "getstatic java/lang/System/out Ljava/io/PrintStream;" );
		ast.accept( generator );
		jasmine.MAIN_METHOD.BODY.appendLine( "iload 0" );
		jasmine.MAIN_METHOD.BODY.appendLine( "getstatic java/lang/System/out Ljava/io/PrintStream;" );
		jasmine.MAIN_METHOD.BODY.appendLine( "ldc \"The result is: \"" );
		jasmine.MAIN_METHOD.BODY.appendLine( "invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V" );
		jasmine.MAIN_METHOD.BODY.appendLine( "invokevirtual java/io/PrintStream/println(I)V" );
		jasmine.MAIN_METHOD.BODY.appendLine( "return" );
		jasmine.MAIN_METHOD.setStackLimit(4);
		jasmine.MAIN_METHOD.setLocalsLimit(2);
		
		String expectedOutput = new String( solution.readAllBytes(), StandardCharsets.UTF_8 );
		assertThat( jasmine.toString() ).isEqualToNormalizingNewlines( expectedOutput );
		
		String result;
		try( InputStream in = new ByteArrayInputStream(jasmine.toString().getBytes()) ){
			result = Jasmine.execute( in );
		}
		assertThat( result ).isEqualTo( "The result is: 38" + System.lineSeparator() );
	}
	
	
	private AstNode parse( String expression ){
		MalarkyLexer lexer = new MalarkyLexer( CharStreams.fromString(expression) );
		CommonTokenStream tokens = new CommonTokenStream( lexer );
		MalarkyParser parser = new MalarkyParser( tokens );
		ParseTree tree = parser.program();
		AstBuilder astBuilder = new AstBuilder( );
		AstNode ast = astBuilder.visit( tree );
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
