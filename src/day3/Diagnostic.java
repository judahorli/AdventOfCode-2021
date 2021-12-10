package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Diagnostic {
    private static final String diagnosticFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day3/diagnostic.txt";
    ArrayList<String> binaries = new ArrayList<>();

    public Diagnostic() {
        readFile();
        loopForPowerConsumption();
        loopForLifeSupport();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(diagnosticFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                binaries.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loopForPowerConsumption() {
        int binLength = binaries.get(0).length();
        int[] gamma = new int[binLength];
        int[] epsilon = new int[binLength];
        for (int i = 0; i < binLength; i++) {
            int zeros = 0;
            int ones = 0;
            for (String binary : binaries) {
                if (binary.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
           if (zeros > ones) {
               gamma[i] = 0;
               epsilon[i] = 1;
           } else {
               gamma[i] = 1;
               epsilon[i] = 0;
           }
        }

        String gammaString = "";
        for(int i : gamma) {
            gammaString += i;
        }
        String epsilonString = "";
        for(int i : epsilon) {
            epsilonString += i;
        }

        int gammaRate = Integer.parseInt(gammaString, 2);
        int epsilonRate = Integer.parseInt(epsilonString, 2);

        System.out.println("gamma rate: " + gammaRate);
        System.out.println("epsilon rate: " + epsilonRate);
        System.out.println("power consumption: " + gammaRate * epsilonRate);
    }

    private void loopForLifeSupport() {
        int binLength = binaries.get(0).length();
        int i = 0;
        ArrayList<String> oxygen = new ArrayList<>(binaries);
        ArrayList<String> co2 = new ArrayList<>(binaries);

        while (i < binLength) {
            int zeros = 0;
            int ones = 0;
            for(String binary : oxygen){
                if (binary.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            Iterator<String> oxygenIter = oxygen.iterator();
            if(zeros > ones) {
                while(oxygenIter.hasNext()& oxygen.size() > 1) {
                    String str = oxygenIter.next();
                    if(str.charAt(i) != '0'){
                        oxygenIter.remove();
                    }
                }
            } else {
                while(oxygenIter.hasNext() && oxygen.size() > 1) {
                    String str = oxygenIter.next();
                    if(str.charAt(i) != '1'){
                        oxygenIter.remove();
                    }
                }
            }

            zeros = 0;
            ones = 0;
            for(String binary : co2){
                if (binary.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            Iterator<String> co2Iter = co2.iterator();
            if(zeros <= ones) {
                while(co2Iter.hasNext() && co2.size() > 1){
                    String str = co2Iter.next();
                    if(str.charAt(i) != '0'){
                        co2Iter.remove();
                    }
                }
            } else {
                while(co2Iter.hasNext() && co2.size() > 1){
                    String str = co2Iter.next();
                    if(str.charAt(i) != '1'){
                        co2Iter.remove();
                    }
                }
            }
            i++;
        }

        int oxygenRate = Integer.parseInt(oxygen.get(0), 2);
        int co2Rate = Integer.parseInt(co2.get(0), 2);

        System.out.println("oxygen rate: " + oxygenRate);
        System.out.println("co2 rate: " + co2Rate);
        System.out.println("life support rate: " + oxygenRate * co2Rate);
    }

    public static void main(String[] args) {
        Diagnostic diagnostic = new Diagnostic();
    }
}