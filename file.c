
{
    // This is a comment!
    int a, b, zero;
    float c, d;
    a = 5; // assigns a to 5
    b = a + 2; /* single line block comment */
    c = a + b;
    c = c / 3; // file.c TODO: make int divisions work for floats
    if (0 == 1) {
        int x, z;
        x = 5;
        z = x * 3;
    } else {
        float hello;
        zero = 0;
        hello = zero + a;
        if (1 == 1) {
            zero = 1;
        }
    }
}
