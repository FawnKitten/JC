
{
    // parse simple function
    int x;
    float y;
    x = 1 + 3;
    y = 1.3 * (x / 2);

    if  (x == 4) {
        y = 1.0;
    }

    while (x > 0) {
        y = y + 1;
        x = x - 1;
    }
    /// complicatedFunction(a, b + c, TRUE || (3 + 4 > 5), "Some function parameter");
}
