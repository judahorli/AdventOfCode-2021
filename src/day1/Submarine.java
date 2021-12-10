package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Submarine {
    private static final String depthFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day1/submarine.txt";
    private ArrayList<Integer> depths = new ArrayList<>();

    public Submarine() {
        readFile();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(depthFile))) {
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                depths.add(x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int findIncreases() {
        int increaseCount = 0;
        int decreaseCount = 0;
        int noChangeCount = 0;
        int last = 0;
        boolean start = true;
        for (int i : depths) {
            if (start) {
                last = i;
                start = false;
            } else {
                if (i > last) {
                    increaseCount++;
                } else if (i < last) {
                    decreaseCount++;
                } else {
                    noChangeCount++;
                }
                last = i;
            }
        }

        System.out.println("increase:" + increaseCount);
        System.out.println("decrease:" + decreaseCount);
        System.out.println("no change:" + noChangeCount);
        return increaseCount;
    }

    public int findSlidingIncreases() {
        int increaseCount = 0;
        int decreaseCount = 0;
        int noChangeCount = 0;
        int lastThree = 0;
        for (int i = 0; i < depths.size() - 2; i++) {
            int currentThree = depths.get(i) + depths.get(i + 1) + depths.get(i + 2);
            if (i != 0) {
                if (currentThree > lastThree) {
                    increaseCount++;
                } else if (currentThree < lastThree) {
                    decreaseCount++;
                } else {
                    noChangeCount++;
                }
                lastThree = currentThree;
            }
        }

        System.out.println("increase:" + increaseCount);
        System.out.println("decrease:" + decreaseCount);
        System.out.println("no change:" + noChangeCount);
        return increaseCount;
    }

    public static void main(String[] args) {
        Submarine sub = new Submarine();
        sub.findIncreases();
        sub.findSlidingIncreases();
    }
}
