
TODO
====
things that are to be done.

Short-Term
----------
* add line numbers to te lexer and parser 
  * better error reporting
* type checking

Features to be added
--------------------
* identifiers
  * symbol table visitor
    * type lookup method
    * type/value declaration
  * types (int, char, void, bool)
* control structure
    * if statement
    * else statement
    * while loop
    * for loop
(here is where things get tricky, mostly because it is uncharted territory)
* functions
  * function declaration
  * main function/entry point
  * return type checking
  * calling functions
  * arguments/parameters
    * argument/parameter type && number checking
  * call stack/recursion
* arrays
  * array type
  * array indexing
    * check if indexed is in array
  * check if assigned type is of array type

FUTURE
------
these are things that are to be implemented some time in the future,
these are not strictly necessary and may not be implemented at all

* pointers
* compilation
  * to bytecode or
  * to assembly
* structs
* preprocessor
