package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bingo {
    private static final String bingoFile = "/Users/judahperillo/IdeaProjects/AdventOfCode-2021/resources/day4/bingo.txt";
    CopyOnWriteArrayList<String> lines = new CopyOnWriteArrayList<>();

    int[] called;
    CopyOnWriteArrayList<int[][]> bingoCards = new CopyOnWriteArrayList<>();
    int lastWinningNumber;
    CopyOnWriteArrayList<int[][]> winningCards = new CopyOnWriteArrayList<>();



    public Bingo() {
        readFile();
    }

    public void play() {
        setUpBingoCards();
        playBingo();
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new File(bingoFile))) {
            String line = scanner.nextLine();
            String[] nums = line.split(",");
            called = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                called[i] = Integer.parseInt(nums[i]);
            }

            while (scanner.hasNext()) {
                String l = scanner.nextLine();
                if (!Objects.equals(l, "")) {
                    lines.add(l);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUpBingoCards() {
        int[][] bingoCard = new int[5][5];

        for (int i = 1; i <= lines.size(); i++) {
            String[] rowNums = lines.get(i-1).trim().split("  | ");
            for (int j = 0; j < 5; j++) {
                bingoCard[(i-1) % 5][j] = Integer.parseInt(rowNums[j]);
            }
            if(i % 5 == 0){
                bingoCards.add(bingoCard);
                bingoCard = new int[5][5];
            }
        }
    }

    private void playBingo() {
        int[][] winner;
        for(int num : called) {
            updateCards(num);
            winner = checkCards(num);
            if(winner != null) {
                System.out.println("bingo! on number: " + num);
                System.out.println(totalNotCalledNumbers(winner) * num);
                return;
            }
        }
    }

    private void playBingoToLose() {
        int[][] winner = new int[0][0];
        int[][] temp;

        for(int num : called) {
            updateCards(num);
            temp = checkCards(num);
            if(temp != null) {
                winner = temp;
                System.out.println(totalNotCalledNumbers(winner) * lastWinningNumber);
            }
        }
    }

    private void updateCards(int calledNum){
        for (int[][] bingoCard : bingoCards) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (bingoCard[i][j] == calledNum) {
                        bingoCard[i][j] = -1;
                    }
                }
            }
        }
    }

    private int[][] checkCards(int num) {
        int[][] card = null;
        for (int[][] bingoCard : bingoCards) {
            for (int i = 0; i < 5; i++) {
                int rowTotal = 0;
                for (int j = 0; j < 5; j++) {
                    rowTotal+= bingoCard[i][j];
                }
                if (rowTotal == -5) {
                    lastWinningNumber = num;
                    bingoCards.remove(bingoCard);
                    winningCards.add(bingoCard);
                    card = bingoCard;
                }
            }

            for (int i = 0; i < 5; i++) {
                int colTotal = 0;
                for (int j = 0; j < 5; j++) {
                    colTotal+= bingoCard[j][i];
                }
                if (colTotal == -5) {
                    lastWinningNumber = num;
                    bingoCards.remove(bingoCard);
                    winningCards.add(bingoCard);
                    card =bingoCard;
                }
            }
        }

        return card;
    }

    private int totalNotCalledNumbers(int[][] winner){
        int total = 0;
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(winner[i][j] != -1){
                    total += winner[i][j];
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Bingo bingo = new Bingo();
        bingo.play();
    }
}
