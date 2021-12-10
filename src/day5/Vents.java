package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Vents {
    ArrayList<Line> lines = new ArrayList<>();
    private static final String ventsFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day5/vents.txt";

    public Vents() {
        readFile();
        ArrayList<Point> points = processHorizVertLines();
        ArrayList<Point> diagonalPoints = processDiagonalLines();
        points.addAll(diagonalPoints);
        processHorizVertLines().addAll(diagonalPoints);
        counts(points);
    }

    private void readFile(){
        try (Scanner scanner = new Scanner(new File(ventsFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] split = line.split(" -> ");
                String parse1 = split[0];
                String parse2 = split[1];
                int x1 = Integer.parseInt(parse1.split(",")[0]);
                int y1 = Integer.parseInt(parse1.split(",")[1]);
                int x2 = Integer.parseInt(parse2.split(",")[0]);
                int y2 = Integer.parseInt(parse2.split(",")[1]);
                lines.add(new Line(new Point(x1, y1), new Point(x2, y2)));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Point> processHorizVertLines() {
        ArrayList<Point> collectionOfPoints = new ArrayList<>();
        for(Line l : lines) {
            if(l.isHorizOrVertLine()) {
                collectionOfPoints.addAll(l.getHorizVertBetweenPoints());
            }
        }

        return collectionOfPoints;
    }

    private ArrayList<Point> processDiagonalLines() {
        ArrayList<Point> collectionOfPoints = new ArrayList<>();
        for(Line l : lines) {
            if(!l.isHorizOrVertLine()) {
                collectionOfPoints.addAll(l.getDiagonalPoints());
            }
        }

        return collectionOfPoints;
    }

    private void counts(ArrayList<Point> collectionOfPoints) {
        HashMap<Point, Integer> countPointFrequency = new HashMap<>();
        for(Point p : collectionOfPoints) {
            if(countPointFrequency.containsKey(p)) {
                countPointFrequency.put(p, countPointFrequency.get(p) + 1);
            } else {
                countPointFrequency.put(p, 1);
            }
        }
        List<Point> pointsOverlap= countPointFrequency.entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
        System.out.println(pointsOverlap.size());
    }

    public void printLines() {
        for(Line l : lines) {
            System.out.println(l);
        }
    }

    public static void main(String[] args) {
        Vents vents = new Vents();
    }
}
