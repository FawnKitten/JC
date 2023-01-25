
{
    // This is a comment!
    int a, b;
    float c, d;
    a = 5; // assigns a to 5
    b = a + 2; // assigns b to a + 2
    a = a * b; /* single line block comment */
    // a = a + d // ERROR: Symbol 'd' was not initialized
    /*
    this is a block comment
    it takes up multiple lines
    */
    a = (b + a) * ((b - 3) + a/b);
    c = 5 + 5 * (a + b);
}
