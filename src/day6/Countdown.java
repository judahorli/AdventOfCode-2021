package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Countdown {
    private static final String fishFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day6/lanternfish.txt";
    ArrayList<Lanternfish> lanternfish = new ArrayList<>();

    BigInteger[] fishGenerations = new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO,
        BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO};


    public Countdown() {
        readFile();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(fishFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] days = line.split(",");
                for (String day : days) {
                    int spawn = Integer.parseInt(day);
                    switch (spawn) {
                        case 0 -> fishGenerations[0] = fishGenerations[0].add(BigInteger.ONE);
                        case 1 -> fishGenerations[1] = fishGenerations[1].add(BigInteger.ONE);
                        case 2 -> fishGenerations[2] = fishGenerations[2].add(BigInteger.ONE);
                        case 3 -> fishGenerations[3] = fishGenerations[3].add(BigInteger.ONE);
                        case 4 -> fishGenerations[4] = fishGenerations[4].add(BigInteger.ONE);
                        case 5 -> fishGenerations[5] = fishGenerations[5].add(BigInteger.ONE);
                        case 6 -> fishGenerations[6] = fishGenerations[6].add(BigInteger.ONE);
                        case 7 -> fishGenerations[7] = fishGenerations[7].add(BigInteger.ONE);
                        case 8 -> fishGenerations[8] = fishGenerations[8].add(BigInteger.ONE);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BigInteger runBigDays(int daysToRun) {
        BigInteger temp0;
        BigInteger temp1;
        BigInteger temp2;
        BigInteger temp3;
        BigInteger temp4;
        BigInteger temp5;
        BigInteger temp6;
        BigInteger temp7;
        BigInteger temp8;
        for (int i = 0; i < daysToRun; i++) {
            temp7 = fishGenerations[8];
            temp6 = fishGenerations[7].add(fishGenerations[0]);
            temp5 = fishGenerations[6];
            temp4 = fishGenerations[5];
            temp3 = fishGenerations[4];
            temp2 = fishGenerations[3];
            temp1 = fishGenerations[2];
            temp0 = fishGenerations[1];
            temp8 = fishGenerations[0];
            fishGenerations = new BigInteger[]{temp0,temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8};
        }
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < fishGenerations.length; i++) {
            sum = sum.add(fishGenerations[i]);
        }

        return sum;
    }

    public int runDays(int daysToRun) {
        for (int i = 0; i < daysToRun; i++) {
            System.out.println("day " + i);
            ArrayList<Lanternfish> newFishes = new ArrayList<>();
            for (Lanternfish fish : lanternfish) {
                fish.passageOfTime();
                if (fish.getNumDaysLeftToSpawn() == 6 && !fish.isFirstSpawn()) {
                    newFishes.add(new Lanternfish());
                }
            }
            lanternfish.addAll(newFishes);
        }

        return lanternfish.size();
    }

    public void printFish() {
        for (Lanternfish fish : lanternfish) {
            System.out.println(fish);
        }
    }

    public static void main(String[] args) {
        Countdown cd = new Countdown();
        System.out.println("fish after 256 days  = " + cd.runBigDays(256));
    }
}
