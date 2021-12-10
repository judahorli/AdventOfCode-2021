package day6;

public class Lanternfish {
    int numDaysLeftToSpawn;
    boolean firstSpawn = false;

    public Lanternfish(int numDaysLeftToSpawn) {
        this.numDaysLeftToSpawn = numDaysLeftToSpawn;
    }

    public Lanternfish() {
        this.numDaysLeftToSpawn = 8;
        this.firstSpawn = true;
    }

    public void passageOfTime() {
        this.numDaysLeftToSpawn--;
        if(this.numDaysLeftToSpawn < 0) {
            this.numDaysLeftToSpawn = 6;
            this.firstSpawn = false;
        }
    }

    public int getNumDaysLeftToSpawn() {
        return numDaysLeftToSpawn;
    }

    public boolean isFirstSpawn() {
        return firstSpawn;
    }

    @Override
    public String toString() {
        return "numDaysLeftToSpawn=" + numDaysLeftToSpawn;
    }
}
