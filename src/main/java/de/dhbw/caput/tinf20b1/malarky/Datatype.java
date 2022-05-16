package de.dhbw.caput.tinf20b1.malarky;

public enum Datatype {
	
	I8("i8"), I16("i16"), I32("i32"), I64("i64"), F32("f32"), F64("f64");
	
	private final String SUFFIX;
	
	private Datatype( String suffix ){
		SUFFIX = suffix;
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
