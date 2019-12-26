package sample.model;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class CrosswordGenerator {

    Random rnd;

    char[][] words;

    int rows, columns;
    char[][] field;

    public CrosswordGenerator(char[][] words, int rows, int columns) {
        this.rnd = new Random(1234);

        this.words = words;
        this.rows = rows;
        this.columns = columns;
    }

    boolean[][] closed;
    boolean[][] onlyVertical;
    boolean[][] onlyHorizontal;

    private final static char EMPTY = '#';

    char[][] generate() {
        this.closed = new boolean[rows][columns];
        this.onlyHorizontal = new boolean[rows][columns];
        this.onlyVertical = new boolean[rows][columns];
        this.field = new char[rows][columns];
        for (char[] row : field) {
            Arrays.fill(row, EMPTY);
        }

        List<char[]> shuffledWords = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(shuffledWords, rnd);

        boolean first = true;
        for (char[] word : shuffledWords) {
            if (tryInsert(word, first)) {
                first = false;
            }
        }

        return field;
    }

    private final static int VERTICAL = 0, HORIZONTAL = 1;

    static class InsertionPoint extends Point {

        int direction;

        public InsertionPoint(int x, int y, int direction) {
            super(x, y);
            this.direction = direction;
        }
    }

    private boolean can(int startRow, int startColumn,
                        int deltaRow, int deltaColumn,
                        char[] word, boolean first,
                        boolean[][] onlyAccept, boolean[][] onlyDecline) {
        boolean anyOnly = first;
        boolean anyCross = first;

        for (int row = startRow, column = startColumn, index = 0;
                row <= rows && column <= columns && index < word.length;
                row += deltaRow, column += deltaColumn, ++index) {
            if (rows == row) return false;
            if (columns == column) return false;

            if (closed[row][column]) return false;
            if (onlyDecline[row][column]) return false;

            boolean isCross = EMPTY != field[row][column];
            if (isCross && field[row][column] != word[index]) return false;

            anyOnly |= onlyAccept[row][column];
            anyCross |= isCross;
        }

        if (checkCell(startRow - deltaRow, startColumn - deltaColumn)) {
            if (EMPTY != field[startRow - deltaRow][startColumn - deltaColumn]) {
                return false;
            }
        }

        if (checkCell(
                startRow + word.length * deltaRow,
                startColumn + word.length * deltaColumn
        )) {
            if (EMPTY != field[startRow + word.length * deltaRow][startColumn + word.length * deltaColumn]) {
                return false;
            }
        }

        return anyOnly && anyCross;
    }

    boolean canVertical(int startRow, int startColumn, char[] word, boolean first) {
        return can(startRow, startColumn, 1, 0, word, first, onlyVertical, onlyHorizontal);
    }

    boolean canHorizontal(int startRow, int startColumn, char[] word, boolean first) {
        return can(startRow, startColumn, 0, 1, word, first, onlyHorizontal, onlyVertical);
    }

    boolean checkIndex(int index, int size) {
        return 0 <= index && index < size;
    }

    boolean checkCell(int row, int column) {
        return checkIndex(row, rows) && checkIndex(column, columns);
    }

    private void insert(int startRow, int startColumn, int deltaRow, int deltaColumn, char[] word, boolean[][] onlyAccept) {
        int orthogonalDeltaRow = 1 - deltaRow;
        int orthogonalDeltaColumn = 1 - deltaColumn;

        for (int row = startRow, column = startColumn, index = 0;
                index < word.length;
                row += deltaRow, column += deltaColumn, ++index) {
            field[row][column] = word[index];

            if (checkCell(row - orthogonalDeltaRow, column - orthogonalDeltaColumn)) {
                onlyAccept[row - orthogonalDeltaRow][column - orthogonalDeltaColumn] = true;
            }

            if (checkCell(row + orthogonalDeltaRow, column + orthogonalDeltaColumn)) {
                onlyAccept[row + orthogonalDeltaRow][column + orthogonalDeltaColumn] = true;
            }
        }

        if (checkCell(startRow - deltaRow, startColumn - deltaColumn)) {
            closed[startRow - deltaRow][startColumn - deltaColumn] = true;
        }

        if (checkCell(startRow + word.length * deltaRow, startColumn + word.length * deltaColumn)) {
            closed[startRow + word.length * deltaRow][startColumn + word.length * deltaColumn] = true;
        }
    }

    void insertVertical(int startRow, int startColumn, char[] word) {
        insert(startRow, startColumn, 1, 0, word, onlyHorizontal);
    }

    void insertHorizontal(int startRow, int startColumn, char[] word) {
        insert(startRow, startColumn, 0, 1, word, onlyVertical);
    }

    boolean tryInsert(char[] word, boolean first) {
        List<InsertionPoint> insertionPoints = new ArrayList<>();
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (canVertical(row, column, word, first)) {
                    insertionPoints.add(new InsertionPoint(row, column, VERTICAL));
                }

                if (canHorizontal(row, column, word, first)) {
                    insertionPoints.add(new InsertionPoint(row, column, HORIZONTAL));
                }
            }
        }

        if (insertionPoints.isEmpty()) {
            return false;
        }

        int index = rnd.nextInt(insertionPoints.size());
        InsertionPoint insertionPoint = insertionPoints.get(index);

        switch (insertionPoint.direction) {
            case VERTICAL:
                insertVertical(insertionPoint.x, insertionPoint.y, word);
                break;
            case HORIZONTAL:
                insertHorizontal(insertionPoint.x, insertionPoint.y, word);
                break;
        }

        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (char[] row : field ){
            s += new String(row) + "\n";
        }
        return s;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = "russian.txt";//"russian_surnames.txt";

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    new DataInputStream(
                            new FileInputStream(fileName)
                    ),
                    "windows-1251"
                )
        );

//        String[] badEnds = {
//            "им", "ом", "ым", "ый", "его", "ого", "ому", "ему", "е",
//            "ую", "у", "ой", "ей",
//        };
        String[] badEnds = {
                "им", "ом", "ым", "ый", "его", "ого", "ому", "ему",
                "ую", "у", "ой", "ей",
        };

        char[][] words = in.lines()
                .map(String::toLowerCase)
                .distinct()
                .filter(s -> {
                    for (String end : badEnds) {
                        if (s.endsWith(end)) return false;
                    }

                    return true;
                })
                .filter(s -> s.length() >= 4)
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int rows = 30, columns = 30;

        CrosswordGenerator generator = new CrosswordGenerator(words, rows, columns);

        char[][] field = generator.generate();
        for (char[] row : field) {
            System.out.println(new String(row));
        }
    }
}
