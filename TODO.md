
TODO
----
things that are to be done.

Short-Term (things needed to make it compile)
=============================================
* don't let unitialized vars be rvalues

Features
========

* identifiers
  * symbol table visitor
    * type lookup method
    * value lookup method
    * type/value declaration
  * types (int, char, void, bool)
* control stucture
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
    * check if inexed is array
  * check if assigned type is of array type

FUTURE
======
these are things that are to be implemented some time in the future,
these are not stricly nessesary and may not be implemented at all

* pointers
* compliation
  * to bytecode or
  * to assembly
* structs
* preprocessor
