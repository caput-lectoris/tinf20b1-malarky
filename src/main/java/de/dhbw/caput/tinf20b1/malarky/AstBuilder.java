package de.dhbw.caput.tinf20b1.malarky;

import java.util.Stack;

import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.AdditionContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.DivideContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.EmptyFactorContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.EmptySummandContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ExponentiationContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ImpotenceContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.LiteralContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.MultiplicationContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.MultiplyContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.NumberContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ParenthesisContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.ProgramContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.SubtractionContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.SumContext;
import de.dhbw.caput.tinf20b1.malarky.MalarkyParser.UnaryContext;
import de.dhbw.caput.tinf20b1.malarky.ast.ArithmeticExpression;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation;
import de.dhbw.caput.tinf20b1.malarky.ast.BinaryOperation.Type;
import de.dhbw.caput.tinf20b1.malarky.ast.NumericLiteral;
import de.dhbw.caput.tinf20b1.malarky.ast.UnaryOperation;

class AstBuilder extends MalarkyBaseVisitor<ArithmeticExpression> {
	
	private Stack<ArithmeticExpression> parameter;
	
	AstBuilder( ){
		super( );
		parameter = new Stack<>();
	}

	/*
	 * program : sum EOF ;
	 */
	@Override
	public ArithmeticExpression visitProgram( ProgramContext ctx ){
		ArithmeticExpression program = visit( ctx.sum() );
		return program;
	}
	
	/*
	 * sum : multiplication summand ;
	 */
	@Override
	public ArithmeticExpression visitSum( SumContext ctx ){
		ArithmeticExpression multiplication = visit( ctx.multiplication() );
		parameter.push( multiplication );
		ArithmeticExpression summand = visit( ctx.summand() );
		return summand;
	}

	/*
	 * summand : PLUS multiplication summand  #addition ;
	 */
	@Override
	public ArithmeticExpression visitAddition( AdditionContext ctx ){
		ArithmeticExpression multiplication = visit( ctx.multiplication() );
		ArithmeticExpression sum = new BinaryOperation( parameter.pop(), Type.ADDITION, multiplication );
		parameter.push( sum );
		ArithmeticExpression result = visit( ctx.summand() );
		return result;
	}
	
	/*
	 * summand : MINUS multiplication summand  #subtraction ;
	 */
	@Override
	public ArithmeticExpression visitSubtraction( SubtractionContext ctx ){
		ArithmeticExpression multiplication = visit( ctx.multiplication() );
		ArithmeticExpression difference = new BinaryOperation( parameter.pop(), Type.SUBTRACTION, multiplication );
		parameter.push( difference );
		ArithmeticExpression result = visit( ctx.summand() );
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
		ArithmeticExpression unary = visit( ctx.unary() );
		parameter.push( unary );
		ArithmeticExpression factor = visit( ctx.factor() );
		return factor;
	}

	/*
	 * factor : STAR unary factor  #multiply ;
	 */
	@Override
	public ArithmeticExpression visitMultiply( MultiplyContext ctx ){
		ArithmeticExpression unary = visit( ctx.unary() );
		ArithmeticExpression product = new BinaryOperation( parameter.pop(), Type.MULTIPLICATION, unary );
		parameter.push( product );
		ArithmeticExpression result = visit( ctx.factor() );
		return result;
	}
	
	/*
	 * factor : SLASH unary factor  #divide ;
	 */
	@Override
	public ArithmeticExpression visitDivide( DivideContext ctx ){
		ArithmeticExpression unary = visit( ctx.unary() );
		ArithmeticExpression quotient = new BinaryOperation( parameter.pop(), Type.DIVISION, unary );
		parameter.push( quotient );
		ArithmeticExpression result = visit( ctx.factor() );
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
		ArithmeticExpression unary = visit( ctx.power() );
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
		ArithmeticExpression temp = visitChildren( ctx );
		return temp;
	}
	
	/*
	 * power : paren CARET power  #exponentiation ;
	 */
	@Override
	public ArithmeticExpression visitExponentiation( ExponentiationContext ctx ){
		ArithmeticExpression paren = visit( ctx.paren() );
		ArithmeticExpression power = visit( ctx.power() );
		ArithmeticExpression result = new BinaryOperation( paren, Type.POWER, power );
		return result;
	}

	/*
	 * paren : literal  #number ;
	 */
	@Override
	public ArithmeticExpression visitNumber( NumberContext ctx ){
		return visit( ctx.literal() );
	}
	
	/*
	 * paren : LPAREN sum RPAREN  #parenthesis ;
	 */
	@Override
	public ArithmeticExpression visitParenthesis( ParenthesisContext ctx ){
		return visit( ctx.sum() );
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

}
