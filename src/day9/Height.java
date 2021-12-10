package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Height {
    private final static String heightmapFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day9/heightmap.txt";
    ArrayList<ArrayList<Integer>> heightMap = new ArrayList<>();
    HashMap<ArrayList<Integer>, Integer> lows = new HashMap<>();

    public Height() {
        readFile();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(heightmapFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                ArrayList<Integer> heightRow = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    heightRow.add(Integer.parseInt(String.valueOf(c)));
                }
                heightMap.add(heightRow);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void findRiskLevel() {
        for (int x = 0; x < heightMap.size(); x++) {
            for (int y = 0; y < heightMap.get(x).size(); y++) {
                List<Integer> temp = findLowPointOfIndex(x, y);
                if (temp != null) {
                    lows.put(new ArrayList<>(temp.subList(0, 2)), temp.get(2));
                }
            }
        }
        System.out.println(lows);

        int riskLevel = 0;
        for (ArrayList<Integer> indices : lows.keySet()) {
            riskLevel += (lows.get(indices) + 1);
        }
        System.out.println(riskLevel);
    }

    public void findBasins() {
        ArrayList<Integer> basinSizes = new ArrayList<>();
        for (Map.Entry<ArrayList<Integer>, Integer> low : lows.entrySet()) {
            int x = findEdges(low.getKey().get(0), low.getKey().get(1), new HashMap<>());
            System.out.println(low.getValue() + " basin size: " + x) ;
            basinSizes.add(x);
        }

        Collections.sort(basinSizes);
        System.out.println(basinSizes);
        List<Integer> topThree = basinSizes.subList(basinSizes.size() -3, basinSizes.size());
        System.out.println(topThree);
        int multiplied = 1;
        for(int x : topThree){
            multiplied *= x;
        }

        System.out.println(multiplied);
    }

    private int findEdges(int row, int col, HashMap<ArrayList<Integer>, Integer> basins) {
        int rowNum = heightMap.size();
        int colNum = heightMap.get(0).size();

        int right = -1, left = -1, above = -1, below = -1;
        int value = heightMap.get(row).get(col);
        HashMap<ArrayList<Integer>, Integer> valid = new HashMap<>();
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(row);
        indices.add(col);
        valid.put(indices, value);

        if (col + 1 < colNum) {
            right = heightMap.get(row).get(col + 1);
            indices = new ArrayList<>();
            indices.add(row);
            indices.add(col + 1);
            if(right != 9) {
                basins.put(indices, right);
            }
        }
        if (col - 1 >= 0) {
            left = heightMap.get(row).get(col - 1);
            indices = new ArrayList<>();
            indices.add(row);
            indices.add(col - 1);
            if(left != 9){
                basins.put(indices, left);
            }
        }
        if (row - 1 >= 0) {
            above = heightMap.get(row - 1).get(col);
            indices = new ArrayList<>();
            indices.add(row - 1);
            indices.add(col);
            if(above != 9){
                basins.put(indices, above);
            }
        }
        if (row + 1 < rowNum) {
            below = heightMap.get(row + 1).get(col);
            indices = new ArrayList<>();
            indices.add(row + 1);
            indices.add(col);
            if(below != 9) {
                basins.put(indices, below);
            }
        }

        if(right > value && right != 9) {
            findEdges(row, col + 1, basins);
        }

        if(left > value && left != 9) {
            findEdges(row, col - 1, basins);
        }
        if(above > value && above != 9) {
            findEdges(row -1, col, basins);
        }
        if(below > value && below != 9){
            findEdges(row + 1, col, basins);
        }

        return basins.size();
    }

    private ArrayList<Integer> findLowPointOfIndex(int row, int col) {
        int right, left, above, below;
        int value = heightMap.get(row).get(col);
        int rowNum = heightMap.size();
        int colNum = heightMap.get(0).size();
        HashMap<ArrayList<Integer>, Integer> valid = new HashMap<>();
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(row);
        indices.add(col);
        valid.put(indices, value);

        if (col + 1 < colNum) {
            right = heightMap.get(row).get(col + 1);
            indices = new ArrayList<>();
            indices.add(row);
            indices.add(col + 1);
            valid.put(indices, right);
        }
        if (col - 1 >= 0) {
            left = heightMap.get(row).get(col - 1);
            indices = new ArrayList<>();
            indices.add(row);
            indices.add(col - 1);
            valid.put(indices, left);
        }
        if (row - 1 >= 0) {
            above = heightMap.get(row - 1).get(col);
            indices = new ArrayList<>();
            indices.add(row - 1);
            indices.add(col);
            valid.put(indices, above);
        }
        if (row + 1 < rowNum) {
            below = heightMap.get(row + 1).get(col);
            indices = new ArrayList<>();
            indices.add(row + 1);
            indices.add(col);
            valid.put(indices, below);
        }

        ArrayList<Integer> minIndex = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (Map.Entry<ArrayList<Integer>, Integer> in : valid.entrySet()) {
            if (in.getValue() < min) {
                min = in.getValue();
                minIndex = in.getKey();
            }
        }

        if (Arrays.stream(valid.values().toArray()).distinct().count() == 1) {
            return null;
        }

        if (value != min) {
            return findLowPointOfIndex(minIndex.get(0), minIndex.get(1));
        } else {
            ArrayList<Integer> minMap = minIndex;
            minMap.add(min);
            return minMap;
        }
    }


    public void print() {
        for (ArrayList<Integer> row : heightMap) {
            for (int i : row) {
                System.out.print(i);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Height heightMap = new Height();
        heightMap.print();
        System.out.println();
        heightMap.findRiskLevel();
        heightMap.findBasins();
    }
}
