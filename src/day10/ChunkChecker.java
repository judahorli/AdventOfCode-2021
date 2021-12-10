package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class ChunkChecker {
    private final static String chunkFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day10/chunks.txt";
    private Stack<Character> chunkStack = new Stack<>();
    private List<Character> illegals = new ArrayList<>();
    private static final List<Character> openers = new ArrayList<>(Arrays.asList('(', '[', '{', '<'));
    private static final List<Character> closers = new ArrayList<>(Arrays.asList(')', ']', '}', '>'));
    private static final Map<Character, Character> pairs = new HashMap<>() {{
        put('(', ')');
        put('[', ']');
        put('{', '}');
        put('<', '>');
    }};
    private List<BigInteger> unfinishedScores = new ArrayList<>();


    public void checkChunks() {
        try (Scanner input = new Scanner(new File(chunkFile))) {
            while (input.hasNext()) {
                chunkStack.clear();
                String line = input.nextLine();
                findIllegals(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file");
        }

        tallyUpIllegals();
        findMiddle();
    }

    private void findIllegals(String line) {
        for (char c : line.toCharArray()) {
            if (closers.contains(c)) {
                if(pairs.get(chunkStack.lastElement()) != c){
                    illegals.add(c);
                    return;
                }
                else {
                    chunkStack.pop();
                }
            }
            else {
                chunkStack.add(c);
            }
        }
        BigInteger unfinishedScore = BigInteger.ZERO;
        for(int i = chunkStack.size() - 1; i >= 0; i--){
            unfinishedScore = unfinishedScore.multiply(BigInteger.valueOf(5));
            switch(pairs.get(chunkStack.get(i))){
                case ')' -> unfinishedScore = unfinishedScore.add(BigInteger.ONE);
                case ']' -> unfinishedScore = unfinishedScore.add(BigInteger.TWO);
                case '}' -> unfinishedScore = unfinishedScore.add(BigInteger.valueOf(3));
                case '>' -> unfinishedScore = unfinishedScore.add(BigInteger.valueOf(4));
            }
        }

        unfinishedScores.add(unfinishedScore);
    }

    private void tallyUpIllegals() {
        int tally = 0;
        for(char i : illegals) {
            switch (i) {
                case ')' -> tally += 3;
                case ']' -> tally += 57;
                case '}' -> tally += 1197;
                case '>' -> tally += 25137;
            }
        }

        System.out.println(tally);
    }

    private void findMiddle() {
        Collections.sort(unfinishedScores);
        System.out.println(unfinishedScores.get((unfinishedScores.size() / 2)));
    }

    public static void main(String[] args) {
        ChunkChecker chunkChecker = new ChunkChecker();
        chunkChecker.checkChunks();
    }
}
