package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DumboOctopus {
    private final static String octopusFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day11/dumbo.txt";
    ArrayList<Cave> caves = new ArrayList<>();
    HashMap<int[], Cave> map = new HashMap<>();
    int flashCount = 0;

    public DumboOctopus() {
        ArrayList<ArrayList<Integer>> x = readFile();
        setUpCaves(x);
        findAdjacentCaves();
        System.out.println("before steps");
        print();
    }

    public void print() {
        int l = 0;
        for (Cave c : caves) {
            System.out.print(c.getValue());
            l++;
            if (l % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private void setUpCaves(ArrayList<ArrayList<Integer>> caves) {
        int xLength = 10, yLength = 10;
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                Cave cave = new Cave(caves.get(x).get(y), x, y);
                this.caves.add(cave);
                map.put(new int[]{x, y}, cave);
            }
        }
    }

    public boolean step() {
        incrementAdjacentCaves(caves);
        boolean allBurst = resetBursts();
        print();
        return allBurst;
    }

    private boolean resetBursts() {
        int burstCount = 0;
        for (Cave c : caves) {
            if(c.isBurst()) {
                c.resetValue();
                burstCount++;
            }
            c.setBurst(false);
        }
        if(burstCount == caves.size()){
            return true;
        }
        return false;
    }

    private void incrementAdjacentCaves(ArrayList<Cave> caves) {
        for (Cave c : caves) {
            c.incrementValue();
            if (c.getValue() > 9 && !c.isBurst()) {
                flashCount++;
                c.resetValue();
                incrementAdjacentCaves(c.getAdjacentCaves());
                c.setBurst(true);
            }

        }
    }

    private void findAdjacentCaves() {
        int xLength = 10, yLength = 10;
        for (Cave c : caves) {
            int x = c.getX();
            int y = c.getY();
            int left = x - 1;
            int right = x + 1;
            int above = y - 1;
            int below = y + 1;
            if (left >= 0) {
                int[] temp = findKey(new int[]{left, y});
                c.addCave(map.get(temp));
                if (above >= 0) {
                    temp = findKey(new int[]{left, above});
                    c.addCave(map.get(temp));
                }
                if (below < yLength) {
                    temp = findKey(new int[]{left, below});
                    c.addCave(map.get(temp));
                }
            }
            if (right < xLength) {
                int[] temp = findKey(new int[]{right, y});
                c.addCave(map.get(temp));
                if (above >= 0) {
                    temp = findKey(new int[]{right, above});
                    c.addCave(map.get(temp));
                }
                if (below < yLength) {
                    temp = findKey(new int[]{right, below});
                    c.addCave(map.get(temp));
                }
            }
            if (above >= 0) {
                int[] temp = findKey(new int[]{x, above});
                c.addCave(map.get(temp));
            }
            if (below < yLength) {
                int[] temp = findKey(new int[]{x, below});
                c.addCave(map.get(temp));
            }
        }
    }

    private int[] findKey(int[] temp) {
        for (Map.Entry<int[], Cave> entry : map.entrySet()) {
            if (entry.getKey()[0] == temp[0] && entry.getKey()[1] == temp[1]) {
                return entry.getKey();
            }
        }
        return temp;
    }

    private ArrayList<ArrayList<Integer>> readFile() {
        ArrayList<ArrayList<Integer>> caveList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(octopusFile))) {
            while (scanner.hasNext()) {
                String line = scanner.next();
                ArrayList<Integer> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    int i = Integer.parseInt(String.valueOf(c));
                    row.add(i);
                }
                caveList.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return caveList;
    }

    public static void main(String[] args) {
        DumboOctopus dumbo = new DumboOctopus();
        int i = 1;
        boolean stop = false;
        while (!stop) {
            System.out.println("step " + i);
            stop = dumbo.step();
            i++;
        }
        System.out.println(dumbo.flashCount);
    }
}
