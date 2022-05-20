package de.dhbw.caput.tinf20b1.malarky;

public enum Datatype {
	
	I8("i8", 10), I16("i16", 20), I32("i32", 30), I64("i64", 40), F32("f32", 50), F64("f64", 60);
	
	private final String SUFFIX;
	private final int RANK;
	
	private Datatype( String suffix, int rank ){
		SUFFIX = suffix;
		RANK = rank;
	}
	
	public static Datatype getPrincipleType( Datatype a, Datatype b ){
		return a.RANK > b.RANK ? a : b;
	}
	
	public static Datatype evaluateTypeSuffix( String suffix ){
		switch( suffix ){
			case "i8": return Datatype.I8;
			case "i16": return Datatype.I16;
			case "i32": return Datatype.I32;
			case "i64": return Datatype.I64;
			case "f32": return Datatype.F32;
			case "f64": return Datatype.F64;
			default: throw new RuntimeException( "invalid type suffix" );
		}
	}
	
	@Override
	public String toString( ){
		return SUFFIX;
	}

}
