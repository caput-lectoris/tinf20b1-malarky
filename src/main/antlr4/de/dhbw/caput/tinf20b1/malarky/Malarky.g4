grammar Malarky;

program
    : sum EOF
    ;

sum
    : multiplication summand
    ;

summand
    : PLUS multiplication summand    #addition
    | MINUS multiplication summand   #subtraction
    |                                #emptySummand
    ;

multiplication
    : unary factor
    ;

factor
    : STAR unary factor   #multiply
    | SLASH unary factor  #divide
    |                     #emptyFactor
    ;

unary
    : power
    | MINUS power
    ;

power
    : paren              #impotence
    | paren CARET power  #exponentiation
    ;

paren
    : literal
    | LPAREN sum RPAREN
    ;

literal
    :  INTEGER_LITERAL
    |  FLOAT_LITERAL
    ;


// integer literals

INTEGER_LITERAL
    :  DEC_LITERAL INTEGER_SUFFIX?
    ;

fragment INTEGER_SUFFIX
    :  'i8'
    |  'i16'
    |  'i32'
    |  'i64'
    |  'i128'
    |  'isize'
    ;


// floating-point literals

FLOAT_LITERAL
    :  DEC_LITERAL ( '.' DEC_LITERAL )? FLOAT_SUFFIX?
    ;

fragment FLOAT_SUFFIX
    :  'f32'
    |  'f64'
    ;


// auxiliary constructs

DEC_LITERAL : DEC_DIGIT+ ;

fragment DEC_DIGIT : [0-9] ;


// separators

LPAREN : '(' ;
RPAREN : ')' ;


// operators

PLUS  : '+' ;
MINUS : '-' ;
STAR  : '*' ;
SLASH : '/' ;
CARET : '^' ;


// whitespace and comments

COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;

WHITESPACE
    : [\p{Zs}] -> channel(HIDDEN)
    ;

NEWLINE
    : ('\r\n' | [\r\n]) -> channel(HIDDEN)
    ;
