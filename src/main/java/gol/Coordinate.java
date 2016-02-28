package gol;

import java.math.BigInteger;

public class Coordinate {
    public final BigInteger x;
    public final BigInteger y;

    public Coordinate(int x, int y) {
        this(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }

    public Coordinate(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(int deltaX, int deltaY) {
        return new Coordinate(x.add(BigInteger.valueOf(deltaX)), y.add(BigInteger.valueOf(deltaY)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x.equals(that.x) && y.equals(that.y);

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
