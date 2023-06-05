
{
    // This is a comment!
    int a, b, zero;
    float c, d;
    int TRUE, FALSE;
    TRUE = 1;
    FALSE = 0;
    a = 1;
    b = 2;
    c = 3;
    // a = 5; // assigns a to 5
    // b = a + 2; /* single line block comment */
    // c = a + b;
    // c = c / 3; // file.c TODO: make int divisions work for floats
    if (!TRUE) {
        int x, z;
        x = 5;
        z = x * 3;
    } else {
        float hello;
        zero = 0;
        hello = zero + a;
        if (!(zero == 0) || !(hello < a)) {
            zero = 1;
        }
    }
}
