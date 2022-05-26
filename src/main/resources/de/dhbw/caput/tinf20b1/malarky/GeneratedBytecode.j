.class public GeneratedBytecode
.super java/lang/Object

.method public <init>()V
  aload_0
  invokenonvirtual java/lang/Object/<init>()V
  return
.end method

.method public static main([Ljava/lang/String;)V
  .limit stack 4
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  iconst_0
  istore 0
  bipush 2
  istore 0
  bipush 42
  iload 0
  bipush 3
  ineg
  imul
  bipush 5
  imul
  bipush 7
  idiv
  iadd
  istore 0
  iload 0
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "The result is: "
  invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
  invokevirtual java/io/PrintStream/println(I)V
  return
.end method
