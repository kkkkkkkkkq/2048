package org.example;

import java.util.Random;

import static org.example.Direction.*;
import static org.example.Constants.*;

public class Model {

    private int[][] field;
    private final int countCellsX;
    private final int countCellsY;
    private final int winCell;

    public int counter;

    public Model(int countX, int countY, int winCell) {
        this.countCellsX = countX;
        this.countCellsY = countY;
        this.winCell = winCell;
    }

    public void newGame() {
        clear();
        createCells();
    }

    private void clear() {
        field = new int[countCellsX][countCellsY];
        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY; y++) {
                field[x][y] = 0;
            }
        }
        counter = 0;
    }

    private void createCells() {
        for (int i = 0; i < COUNT_INITIAL_SELLS; i++) {
            createNewCell();
        }
    }

    private void createNewCell() {
        int cell = (new Random().nextInt(100) <= CHANCE_OF_LUCKY_SPAWN)
                ? LUCKY_INITIAL_CELL_STATE
                : INITIAL_CELL_STATE;
        while (true) {
            int x = new Random().nextInt(countCellsX);
            int y = new Random().nextInt(countCellsY);
            if (field[x][y] == 0) {
                field[x][y] = cell;
                break;
            }
        }
    }

    public void left() {
        move(LEFT);
    }

    public void right() {
        move(RIGHT);
    }

    public void down() {
        move(DOWN);
    }

    public void up() {
        move(UP);
    }

    private void move(Direction direction) {
        int[][] beforeMove = setArray(field);
        switch (direction) {
            case UP: {
                for (int x = 0; x < countCellsX; x++) {
                    int[] newArray = new int[countCellsY];
                    int index = 0;
                    for (int y = 0; y < countCellsY; y++) {
                        if (field[x][y] != 0) {
                            newArray[index] = field[x][y];
                            index++;
                        }
                    }
                    setColumn(x, ifNeedSum(newArray, direction));
                }
                break;
            }
            case LEFT: {
                for (int y = 0; y < countCellsY; y++) {
                    int[] newArray = new int[countCellsX];
                    int index = 0;
                    for (int x = 0; x < countCellsX; x++) {
                        if (field[x][y] != 0) {
                            newArray[index] = field[x][y];
                            index++;
                        }
                    }
                    setLine(y, ifNeedSum(newArray, direction));
                }
                break;
            }
            case RIGHT: {
                for (int y = 0; y < countCellsY; y++) {
                    int[] newArray = new int[countCellsX];
                    int index = newArray.length - 1;
                    for (int x = newArray.length - 1; x >= 0; x--) {
                        if (field[x][y] != 0) {
                            newArray[index] = field[x][y];
                            index--;
                        }
                    }
                    setLine(y, ifNeedSum(newArray, direction));
                }
                break;
            }
            case DOWN: {
                for (int x = 0; x < countCellsX; x++) {
                    int[] newArray = new int[countCellsY];
                    int index = newArray.length - 1;
                    for (int y = newArray.length - 1; y >= 0; y--) {
                        if (field[x][y] != 0) {
                            newArray[index] = field[x][y];
                            index--;
                        }
                    }
                    setColumn(x, ifNeedSum(newArray, direction));
                }
                break;
            }
        }
        if (isMoved(beforeMove, field)) createNewCell();
    }

    private int[] ifNeedSum(int[] array, Direction direction) {
        int length = array.length;

        switch (direction) {
            case UP:
            case LEFT: {
                for (int i = 0; i < length - 1; i++) {
                    if (array[i] == array[i + 1]) {
                        array[i] *= 2;
                        counter += array[i];
                        array[i + 1] = 0;
                        i++;
                    }
                }
                break;
            }

            case DOWN:
            case RIGHT: {
                for (int i = length - 1; i > 0; i--) {
                    if (array[i] == array[i - 1]) {
                        array[i] = array[i] * 2;
                        counter += array[i];
                        array[i - 1] = 0;
                        i--;
                    }
                }
                break;
            }
        }
        return array;
    }

    private void setLine(int y, int[] line) {
        for (int x = 0; x < countCellsX; x++) {
            field[x][y] = line[x];
        }
    }

    private void setColumn(int x, int[] column) {
        for (int y = 0; y < countCellsY; y++) {
            field[x][y] = column[y];
        }
    }

    private int[][] setArray(int[][] array) {
        int[][] newArray = new int[countCellsX][countCellsY];
        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY; y++) {
                newArray[x][y] = array[x][y];
            }
        }
        return newArray;
    }

    private boolean isMoved(int[][] before, int[][] after) {
        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY; y++) {
                if (before[x][y] != after[x][y]) return true;
            }
        }
        return false;
    }

    public boolean isWinEndOfGame() {
        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY; y++) {
                if (field[x][y] == winCell) return true;
            }
        }
        return false;
    }

    private boolean isThereEmptyCells() {
        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY; y++) {
                if (field[x][y] == 0) return true;
            }
        }
        return false;
    }

    public boolean isThereMoves() {
        if (isThereEmptyCells()) return true;

        for (int x = 0; x < countCellsX; x++) {
            for (int y = 0; y < countCellsY - 1; y++) {
                if (getCell(x, y) == getCell(x, y + 1))
                    return true;
            }
        }

        for (int y = 0; y < countCellsY; y++) {
            for (int x = 0; x < countCellsX - 1; x++) {
                if (getCell(x, y) == getCell(x + 1, y))
                    return true;
            }
        }

        return false;
    }

    public int getCounter() {
        return counter;
    }

    public int getCell(int x, int y) {
        return field[x][y];
    }

    public int getCountCellsX() {
        return countCellsX;
    }

    public int getCountCellsY() {
        return countCellsY;
    }
}