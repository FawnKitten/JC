
TODO
====
things that are to be done.

Short-Term
----------
* do not let variables be assigned to other uninitialized variables
* better type checking
* remove need for braces for one line blocks
* Strings
  * String Literals (char arrays?)
  * String Variables
* printing/logging of some sort
* Make README.md prettier + more explanation
* make the AST Nodes/Symbols constant (i.e. no setters)
* replace getName() functionality in variable nodes to getToken()

Features to be added
--------------------
* identifiers
  * types (int, char, void, bool)
* control structure
    * while loop
    * for loop
    * scope blocks
    * introduce scope for functions 

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
