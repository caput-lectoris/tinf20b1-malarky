package de.dhbw.caput.tinf20b1.malarky.ast.traversals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.AstNode;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.TypeCast;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.Variable;
import de.dhbw.caput.tinf20b1.malarky.ast.VariableDeclaration;
import de.dhbw.caput.tinf20b1.malarky.ast.WhileStatement;

public class DeclarationFinder implements AstTraverser<String> {
	
	private final Map<String, VariableDeclaration> DECLARATIONS;
	
	private DeclarationFinder( ){
		super( );
		DECLARATIONS = new HashMap<>( );
	}
	
	public static String runOn( AstNode tree ){
		DeclarationFinder finder = new DeclarationFinder( );
		return tree.accept( finder );
	}

	@Override
	public String visit( NumericLiteral literal ){
		return null;
	}

	@Override
	public String visitPost( UnaryOperation op, String base ){
		return null;
	}

	@Override
	public String visitPost( BinaryOperation op, String left, String right ){
		return null;
	}

	@Override
	public String visitPost( TypeCast cast, String base ){
		return null;
	}

	@Override
	public String visitPost( VariableDeclaration decl ){
		VariableDeclaration previous = DECLARATIONS.get( decl.NAME );
		if( null != previous ){
			throw new RuntimeException( "a variable with this name already exists" );
		}
		int slot = DECLARATIONS.size();
		decl.setVariableTableSlot( slot );
		DECLARATIONS.put( decl.NAME , decl );
		return null;
	}

	@Override
	public String visitPost( Assignment assignment, String expr ){
		VariableDeclaration decl = DECLARATIONS.get( assignment.NAME );
		if( null == decl ){
			throw new RuntimeException( "variable must be declared before use" );
		}
		assignment.setDeclaration(decl);
		return null;
	}

	@Override
	public String visitPost( BlockStatement block, List<String> stmts ){
		return null;
	}

	@Override
	public String visitPost( Variable var ){
		VariableDeclaration decl = DECLARATIONS.get( var.NAME );
		if( null == decl ){
			throw new RuntimeException( "variable must be declared before use" );
		}
		var.setDeclaration( decl );
		return null;
	}

	@Override
	public String visitPost( WhileStatement var ){
		return null;
	}

}
