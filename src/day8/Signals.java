package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Signals {
    HashMap<String[], String[]> lines = new HashMap<>();

    private static final String signalsFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day8/signals.txt";

    public Signals() {
        readFile();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(signalsFile))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String[] digits = orderedEachStringInArray(Arrays.copyOf(parts[0].trim().split(" "), 10));
                String[] code = orderedEachStringInArray(Arrays.copyOf(parts[1].trim().split(" "), 4));
                lines.put(digits, code);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void findCountOfOnesFoursSevensEights() {
        int count = 0;
        for (Map.Entry<String[], String[]> l : lines.entrySet()) {
            for (String code : l.getValue()) {
                if (code.length() == 2 || code.length() == 3 || code.length() == 4 || code.length() == 7) {
                    count++;
                }
            }
        }
    }

    public void decodeLineByLine() {
        int sum = 0;
        for (Map.Entry<String[], String[]> line : lines.entrySet()) {
            StringBuilder digit = new StringBuilder();
            HashMap<String, Integer> decoded = decodeLine(line.getKey());
            for(String code : line.getValue()){
                digit.append(decoded.get(code));
            }
            sum += Integer.parseInt(String.valueOf(digit));
        }
        System.out.println(sum);
    }

    private HashMap<String, Integer> decodeLine(String[] codedDigits) {
        HashSet<Character> all = new HashSet<>();
        for (char x = 'a'; x <= 'g'; x++) {
            all.add(x);
        }

        HashMap<Integer, HashSet<Character>> decodedDigits = new HashMap<>();
        char top;
        char bottom;
        char middle;
        char topRight;
        char bottomRight;
        char topLeft;
        char bottomLeft;
        ArrayList<HashSet<Character>> length5 = new ArrayList<>();

        for (String s : codedDigits) {
            HashSet<Character> charHash = new HashSet<>(primitiveToList(s.toCharArray()));
            switch (charHash.size()) {
                case 2 -> decodedDigits.put(1, charHash);
                case 3 -> decodedDigits.put(7, charHash);
                case 4 -> decodedDigits.put(4, charHash);
                case 7 -> decodedDigits.put(8, charHash);
                case 5 -> length5.add(charHash);
            }
        }

        // find top line with 1 and 7, different of set 7 and 1
        HashSet<Character> sevenDifOne = new HashSet<>(decodedDigits.get(7)); // use the copy constructor
        sevenDifOne.removeAll(decodedDigits.get(1));

        Iterator<Character> iter = sevenDifOne.iterator();
        top = iter.next();

        HashSet<Character> bottomAndBottomLeft = new HashSet<>();
        HashSet<Character> unionSevenFour = new HashSet<>(decodedDigits.get(7));
        unionSevenFour.addAll(decodedDigits.get(4));
        HashSet<Character> differenceAllUnionSevenFour = new HashSet<>(all);
        differenceAllUnionSevenFour.removeAll(unionSevenFour);
        iter = differenceAllUnionSevenFour.iterator();
        bottomAndBottomLeft.add(iter.next());
        bottomAndBottomLeft.add(iter.next());

        // looking for digits with 5 distinct chars
        HashMap<HashSet<Character>, HashSet<Character>> remains5 = new HashMap<>();
        for (HashSet<Character> characters : length5) {
            HashSet<Character> temp = new HashSet<>(characters);
            // top, top right, bottom right, bottom, bottom left
            HashSet<Character> diff = new HashSet<>(decodedDigits.get(7));
            diff.addAll(new HashSet<>(bottomAndBottomLeft));
            diff.removeAll(temp);
            if (diff.size() == 2) {
                decodedDigits.put(5, temp);
            } else {
                remains5.put(temp, diff);
            }
        }

        for (Map.Entry<HashSet<Character>, HashSet<Character>> rem : remains5.entrySet()) {
            HashSet<Character> temp = new HashSet<>(decodedDigits.get(5));
            temp.retainAll(rem.getValue());
            if (temp.size() == 1) {
                decodedDigits.put(2, rem.getKey());
            } else {
                decodedDigits.put(3, rem.getKey());
            }
        }

        for (HashSet<Character> characters : length5) {
            HashSet<Character> temp = new HashSet<>(characters);
            // top, top right, bottom right, bottom, bottom left
            HashSet<Character> diff = new HashSet<>(decodedDigits.get(7));
            diff.addAll(new HashSet<>(bottomAndBottomLeft));
            diff.removeAll(temp);
            if (diff.size() == 2) {
                decodedDigits.put(5, temp);
            } else {
                remains5.put(temp, diff);
            }
        }

        HashSet<Character> temp = new HashSet<>(decodedDigits.get(2));
        temp.removeAll(decodedDigits.get(3));
        bottomLeft = temp.iterator().next();
        temp = new HashSet<>(decodedDigits.get(5));
        temp.removeAll(decodedDigits.get(3));
        topLeft = temp.iterator().next();
        temp = new HashSet<>(bottomAndBottomLeft);
        HashSet<Character> bottomLeftSet = new HashSet<>();
        bottomLeftSet.add(bottomLeft);
        temp.removeAll(bottomLeftSet);
        bottom = temp.iterator().next();
        temp = new HashSet<>(decodedDigits.get(5));
        temp.addAll(bottomLeftSet);
        HashSet<Character> allTemp = new HashSet<>(all);
        allTemp.removeAll(temp);
        topRight = allTemp.iterator().next();
        temp = new HashSet<>(decodedDigits.get(1));
        temp.remove(topRight);
        bottomRight = temp.iterator().next();

        allTemp = new HashSet<>(all);
        List.of(top, topLeft, topRight, bottom, bottomLeft, bottomRight).forEach(allTemp::remove);
        middle = allTemp.iterator().next();;

        HashSet<Character> zero = new HashSet<>(List.of(top, topLeft, topRight, bottom, bottomLeft, bottomRight));
        decodedDigits.put(0, zero);
        HashSet<Character> six = new HashSet<>(List.of(top, topLeft, middle, bottom, bottomLeft, bottomRight));
        decodedDigits.put(6, six);
        HashSet<Character> nine = new HashSet<>(List.of(top, topLeft, topRight, bottom, middle, bottomRight));
        decodedDigits.put(9, nine);

        HashMap<String, Integer> decodedDigs = new HashMap<>();
        for(Map.Entry<Integer, HashSet<Character>> x : decodedDigits.entrySet()) {
            decodedDigs.put(x.getValue().stream().map(Object::toString).collect(Collectors.joining()), x.getKey());
        }
        return decodedDigs;
    }


    private String[] orderedEachStringInArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = orderedString(array[i]);
        }
        return array;
    }

    private String orderedString(String s) {
        char[] tempArray = s.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private List<Character> primitiveToList(char[] array) {
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            charList.add(array[i]);
        }

        return charList;
    }

    public static void main(String[] args) {
        Signals signals = new Signals();
        signals.findCountOfOnesFoursSevensEights();
        signals.decodeLineByLine();
    }
}
