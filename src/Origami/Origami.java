package Origami;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Origami {
    private final static String paperFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day13/fold.txt";
    List<Coordinate> dotCoordinates = new ArrayList<>();
    List<String> foldInstructions = new ArrayList<>();
    int maxX = 0;
    int maxY = 0;

    void readFile() throws FileNotFoundException {
        try(Scanner scanner = new Scanner(new File(paperFile))){
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.split(",").length > 1) {
                    Coordinate coord = new Coordinate(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]));
                    dotCoordinates.add(coord);
                } else if (!line.isEmpty()){
                    foldInstructions.add(line);
                }
            }
        }
        findMaxValues();
    }

    void print() {
        System.out.println(foldInstructions);
        for(int y = 0; y < maxY + 1; y++){
            for(int x = 0; x < maxX + 1; x++){
                if(dotCoordinates.contains(new Coordinate(x, y))){
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    void folding() {
        for(String instruction : foldInstructions) {
            fold(instruction);
        }
        System.out.println(findDots());
        print();
    }

    int findDots() {
        Set<Coordinate> s = new LinkedHashSet<Coordinate>(dotCoordinates);
        return s.size();
    }

    void findMaxValues(){
        for(Coordinate coord : dotCoordinates){
            if(coord.getX() > maxX){
                maxX = coord.getX();
            }
            if(coord.getY() > maxY){
                maxY = coord.getY();
            }
        }
    }

    void fold(String instruction) {
        String axisValue = instruction.split("fold along ")[1];
        String axis = axisValue.split("=")[0];
        int axisN = Integer.parseInt(axisValue.split("=")[1]);
        if(axis.equals("x")){
            xFoldLeft(axisN);
        } else {
            yFoldUp(axisN);
        }
    }

    void xFoldLeft(int n) {
        dotCoordinates.removeIf(coord -> coord.getX() == n);
        for(int i = n + 1; i < maxX + 1; i++){
            for(Coordinate coord : dotCoordinates){
                int newX = n - (i - n);
                if(coord.getX() == i){
                    coord.setX(newX);
                }
            }
        }
        maxX = n - 1;
    }

    void yFoldUp(int n) {
        dotCoordinates.removeIf(coord -> coord.getY() == n);
        for(int i = n + 1; i < maxY + 1; i++){
            for(Coordinate coord : dotCoordinates) {
                int newY = n - (i - n);
                if(coord.getY() == i) {
                    coord.setY(newY);
                }
            }
        }
        maxY = n - 1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Origami origami = new Origami();
        origami.readFile();
        origami.folding();
    }
}
