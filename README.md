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
    
Sample .mh file
  
    { float a,b }
    float x = (a/10.0)+(b/10);


Requirements
============

* Java >= 1.8.0.0

Installation
============

  Clone the Repository 
    
