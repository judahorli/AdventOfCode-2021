package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

public class Crabs {
    ArrayList<Integer> crabs = new ArrayList<>();
    private static final String crabFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day7/crabs.txt";

    public Crabs() {
        readFile();
    }

    private void readFile() {
            try (Scanner scanner = new Scanner(new File(crabFile))) {
                while (scanner.hasNext()) {
                    scanner.useDelimiter("\\D");
                    crabs.add(scanner.nextInt());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    private int findMedian() {
        Collections.sort(crabs);
        int n = crabs.size() - 1;
        if (n % 2 != 0)
            return (int) crabs.get(n / 2);
        return (int) ((int) crabs.get((n - 1) / 2) + crabs.get(n / 2) / 2.0);
    }

    private int findMean() {
        double sum = 0;
        for(int x: crabs) {
            sum+= x;
        }
        return (int) Math.floor((sum / (double) crabs.size()));
    }

    public void findShortestStepsPart1(){
        int median = findMedian();
        int fuel = 0;
        for(int x : crabs) {
            fuel += Math.abs(x - median);
        }

        System.out.println(fuel);
    }

    public void findShortestStepsPart2(){
        int mean = findMean();
        int fuel = 0;
        for(int x : crabs) {
            int diff = Math.abs(mean - x);
            int temp = 0;
            for(int i = 0; i <= diff; i++){
                fuel += i;
            }
        }

        System.out.println(fuel);
    }

    public static void main(String[] args) {
        Crabs crab = new Crabs();
        crab.findShortestStepsPart1();
        crab.findShortestStepsPart2();
    }
}
