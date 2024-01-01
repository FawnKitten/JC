
{
    // parse simple function
    int x, y, z;
    x = 4;
    y = 1;
    z = 556;
    while (x > 0) {
        printf("loop %d", y);
        x = x - 1;
        y = y + 1;
        DEBUG(x, y);
    }
}
