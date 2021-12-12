package day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cave {
    int value;
    int x;
    int y;
    boolean burst;
    ArrayList<Cave> adjacentCaves = new ArrayList<>();

    public Cave(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public void incrementValue() {
        value++;
    }

    public void addCave(Cave cave) {
        adjacentCaves.add(cave);
    }

    public int getValue() {
        return value;
    }

    public void setBurst(boolean burst) {
        this.burst = burst;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBurst() {
        return burst;
    }

    public void resetValue() {
        this.value = 0;
    }
    public ArrayList<Cave> getAdjacentCaves() {
        return adjacentCaves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return value == cave.value && x == cave.x && y == cave.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, x, y, adjacentCaves);
    }

    @Override
    public String toString() {
        return "Cave{" +
                "value=" + value +
                ", x=" + x +
                ", y=" + y +
                ", adjacentCaves=" + adjacentCaves.size() +
                '}';
    }
}
