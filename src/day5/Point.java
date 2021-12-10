package day5;

import java.util.Objects;

public class Point implements Comparable<Point>{
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean equalsX(Point p) {
        return this.x == p.getX();
    }

    public boolean equalsY(Point p) {
        return this.y == p.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Point o) {
        if(this.y < o.getY()) {
            return -1;
        } else if (this.y > o.getY()){
            return 1;
        }
        return 0;
    }
}
