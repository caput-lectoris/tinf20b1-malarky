package de.dhbw.caput.tinf20b1.malarky;

import java.util.Stack;

import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.AdditionContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.AssignContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.AssignmentContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.DivideContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.EmptyFactorContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.EmptySummandContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ExponentiationContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ImpotenceContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.LiteralContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.MultiStatementsContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.MultiplicationContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.MultiplyContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.NumberContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ParenthesisContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ProgramContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.SingleStatementContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.SubtractionContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.SumContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.UnaryContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.VarDeclContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.VariableContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.VariableDeclarationContext;
import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.Assignment;
import de.dhbw.caput.tinf20b1.malarky.ast.AstNode;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation.Type;
import de.dhbw.caput.tinf20b1.malarky.ast.BlockStatement;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.Statement;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.Variable;
import de.dhbw.caput.tinf20b1.malarky.ast.VariableDeclaration;

class AstBuilder extends MalarkyBaseVisitor<AstNode> {
	
	private Stack<ArithmeticExpression> parameter;
	private BlockStatement block;
	
	AstBuilder( ){
		super( );
		parameter = new Stack<>();
		block = null;
	}

	/*
	 * program : statements EOF ;
	 */
	@Override
	public AstNode visitProgram( ProgramContext ctx ){
		block = new BlockStatement();
		visit( ctx.statements() );
		return block;
	}
	
	/*
	 * statements : statement  #singleStatement
	 */
	@Override
	public AstNode visitSingleStatement( SingleStatementContext ctx ){
		Statement statement = (Statement) visit( ctx.statement() );
		block.STATEMENTS.add( statement );
		return statement;
	}
	
	/*
	 * statements : statement statements  #multiStatements ;
	 */
	@Override
	public AstNode visitMultiStatements( MultiStatementsContext ctx ){
		Statement statement = (Statement) visit( ctx.statement() );
		block.STATEMENTS.add( statement );
		visit( ctx.statements() );
		return block;
	}
	
	/*
	 * statement : variableDeclaration  #varDecl ;
	 */
	@Override
	public AstNode visitVarDecl( VarDeclContext ctx ){
		AstNode declaration = visit( ctx.variableDeclaration() );
		return declaration;
	}
	
	/*
	 * statement : assignment  #assign ;
	 */
	@Override
	public AstNode visitAssign( AssignContext ctx ){
		AstNode assignment = visit( ctx.assignment() );
		return assignment;
	}
	
	/*
	 * variableDeclaration : 'let' name=IDENTIFIER COLON type=IDENTIFIER SEMICOLON ;
	 */
	@Override
	public AstNode visitVariableDeclaration( VariableDeclarationContext ctx ){
		String name = ctx.name.getText();
		String type = ctx.type.getText();
		Datatype datatype = Datatype.evaluateTypeSuffix( type );
		VariableDeclaration var = new VariableDeclaration( name, datatype );
		return var;
	}
	
	/*
	 * assignment : name=IDENTIFIER ASSIGN sum SEMICOLON ;
	 */
	@Override
	public AstNode visitAssignment( AssignmentContext ctx ){
		ArithmeticExpression expr = (ArithmeticExpression) visit( ctx.sum() );
		String name = ctx.name.getText();
		Assignment assignment = new Assignment( name, expr );
		return assignment;
	}
	
	/*
	 * sum : multiplication summand ;
	 */
	@Override
	public ArithmeticExpression visitSum( SumContext ctx ){
		ArithmeticExpression multiplication = (ArithmeticExpression) visit( ctx.multiplication() );
		parameter.push( multiplication );
		ArithmeticExpression summand = (ArithmeticExpression) visit( ctx.summand() );
		return summand;
	}

	/*
	 * summand : PLUS multiplication summand  #addition ;
	 */
	@Override
	public ArithmeticExpression visitAddition( AdditionContext ctx ){
		ArithmeticExpression multiplication = (ArithmeticExpression) visit( ctx.multiplication() );
		ArithmeticExpression sum = new BinaryOperation( parameter.pop(), Type.ADDITION, multiplication );
		parameter.push( sum );
		ArithmeticExpression result = (ArithmeticExpression) visit( ctx.summand() );
		return result;
	}
	
	/*
	 * summand : MINUS multiplication summand  #subtraction ;
	 */
	@Override
	public ArithmeticExpression visitSubtraction( SubtractionContext ctx ){
		ArithmeticExpression multiplication = (ArithmeticExpression) visit( ctx.multiplication() );
		ArithmeticExpression difference = new BinaryOperation( parameter.pop(), Type.SUBTRACTION, multiplication );
		parameter.push( difference );
		ArithmeticExpression result = (ArithmeticExpression) visit( ctx.summand() );
		return result;
	}
	
	/*
	 * summand :  #emptySummand ;
	 */
	@Override
	public ArithmeticExpression visitEmptySummand( EmptySummandContext ctx ){
		ArithmeticExpression temp = parameter.pop();
		return temp;
	}

	/*
	 * multiplication : unary factor ;
	 */
	@Override
	public ArithmeticExpression visitMultiplication( MultiplicationContext ctx ){
		ArithmeticExpression unary = (ArithmeticExpression) visit( ctx.unary() );
		parameter.push( unary );
		ArithmeticExpression factor = (ArithmeticExpression) visit( ctx.factor() );
		return factor;
	}

	/*
	 * factor : STAR unary factor  #multiply ;
	 */
	@Override
	public ArithmeticExpression visitMultiply( MultiplyContext ctx ){
		ArithmeticExpression unary = (ArithmeticExpression) visit( ctx.unary() );
		ArithmeticExpression product = new BinaryOperation( parameter.pop(), Type.MULTIPLICATION, unary );
		parameter.push( product );
		ArithmeticExpression result = (ArithmeticExpression) visit( ctx.factor() );
		return result;
	}
	
	/*
	 * factor : SLASH unary factor  #divide ;
	 */
	@Override
	public ArithmeticExpression visitDivide( DivideContext ctx ){
		ArithmeticExpression unary = (ArithmeticExpression) visit( ctx.unary() );
		ArithmeticExpression quotient = new BinaryOperation( parameter.pop(), Type.DIVISION, unary );
		parameter.push( quotient );
		ArithmeticExpression result = (ArithmeticExpression) visit( ctx.factor() );
		return result;
	}
	
	/*
	 * factor :  #emptyFactor ;
	 */
	@Override
	public ArithmeticExpression visitEmptyFactor( EmptyFactorContext ctx ){
		ArithmeticExpression temp = parameter.pop();
		return temp;
	}

	/*
	 * unary
	 *     : power
	 *     | MINUS power
	 *     ;
	 */
	@Override
	public ArithmeticExpression visitUnary( UnaryContext ctx ){
		ArithmeticExpression unary = (ArithmeticExpression) visit( ctx.power() );
		if( MalarkyLexer.MINUS == ctx.start.getType() ){
			ArithmeticExpression result = new UnaryOperation( unary, UnaryOperation.Type.NEGATION );
			return result;
		}
		return unary;
	}

	/*
	 * power : paren  #impotence ;
	 */
	@Override
	public ArithmeticExpression visitImpotence( ImpotenceContext ctx ){
		ArithmeticExpression temp = (ArithmeticExpression) visitChildren( ctx );
		return temp;
	}
	
	/*
	 * power : paren CARET power  #exponentiation ;
	 */
	@Override
	public ArithmeticExpression visitExponentiation( ExponentiationContext ctx ){
		ArithmeticExpression paren = (ArithmeticExpression) visit( ctx.paren() );
		ArithmeticExpression power = (ArithmeticExpression) visit( ctx.power() );
		ArithmeticExpression result = new BinaryOperation( paren, Type.POWER, power );
		return result;
	}

	/*
	 * paren : literal  #number ;
	 */
	@Override
	public ArithmeticExpression visitNumber( NumberContext ctx ){
		return (ArithmeticExpression) visit( ctx.literal() );
	}
	
	/*
	 * paren : LPAREN sum RPAREN  #parenthesis ;
	 */
	@Override
	public ArithmeticExpression visitParenthesis( ParenthesisContext ctx ){
		return (ArithmeticExpression) visit( ctx.sum() );
	}

	/*
	 * literal
	 *     : INTEGER_LITERAL
	 *     | FLOAT_LITERAL
	 *     ;
	 */
	@Override
	public ArithmeticExpression visitLiteral( LiteralContext ctx ){
		ArithmeticExpression literal = new NumericLiteral( ctx.getText() );
		return literal;
	}
	
	/*
	 * statement : IDENTIFIER  #variable ;
	 */
	@Override
	public ArithmeticExpression visitVariable( VariableContext ctx ){
		String name = ctx.getText();
		return new Variable( name );
	}

}
