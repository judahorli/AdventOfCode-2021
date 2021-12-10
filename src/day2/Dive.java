package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dive {
    private static final String depthFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day2/pilot.txt";
    private int horizontal = 0;
    private int depth = 0;
    private int aim = 0;

    private int followCourse() {
        try (Scanner scanner = new Scanner(new File(depthFile))) {
            while (scanner.hasNext()) {
               String line = scanner.nextLine();
               String direction = line.split(" ")[0];
               int val = Integer.parseInt(line.split(" ")[1]);
                switch (direction) {
                    case "forward" -> {
                        horizontal += val;
                        depth += val * aim;
                    }
                    case "down" -> aim += val;
                    case "up" -> aim -= val;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return horizontal * depth;
    }
    public static void main(String[] args) {
        Dive dive = new Dive();
        int ans = dive.followCourse();
        System.out.println(ans);
    }
}
