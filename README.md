ToyCompiler Using Java 
=======================

Created a new compiler which will compile the code and will check whether the input code is valid or not.
As a compiler it will performs Lexical Analysis, synthatic Analysis and Semantic Analysis then end result will be obtain.

This compiler compiles the with .mh extension files.

Initially our compiler supports,

    Keywords => Integer, Float
    Operators => +,-,=,*,/
    Symbols => (){},;

Instructions for writing code
  
    Variable declarations like int/float with curly braces {float a,b}
    Every expression must be ends with semicolon(;)
    
Valid Grammar

	SYM1 -> =
	SYM2 -> (
	SYM3 -> )
	SYM4 -> {
	SYM5 -> }
	SYM6 -> ,
	SYM17 -> ;
	
	KEY1 -> int
	KEY2 -> float
	
	OPR1 -> =
	OPR2 -> +
	OPR3 -> -
	OPR4 -> *
	OPR5 -> /
	
	line -> SYM4 declaration SYM5
	line -> KEY1 IDE OPR1 expression SYM3
	line -> KEY2 IDE OPR1 expression SYM3
	
	declaration -> KEY1 identifiers 
	declaration -> KEY2 identifiers
	
	identifiers -> IDE
	identifiers -> IDE SYM6 identifiers
	
	expression -> terminal expression -> terminal OPR2 expression
	expression -> terminal OPR3 expression expression -> terminal OPR4 expression
	expression -> terminal OPR5 expression expression -> SYM1 expression SYM2
	expression -> SYM1 expression SYM2 OPR2 expression expression -> SYM1
	expression SYM2 OPR3 expression expression -> SYM1 expression SYM2 OPR4
	expression expression -> SYM1 expression SYM2 OPR5 expression
	
    
Sample .mh file
  
    { float a,b }
    float x = (a/10.0)+(b/10);


Requirements
============

* Java >= 1.8.0.0

Installation
============

  Clone the Repository 
    
