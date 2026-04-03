package sudokusolver.demo.Model;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {
    private int[][] cells = new int[9][9];
    private Set<Integer>[][] pickable = new HashSet[9][9];

    public int[][] getCells() {
        return cells;
    }

    public int getCells(int row, int col) {
        return cells[row][col];
    }

    public void setCells(int row, int col, int value) {
        cells[row][col] = value;
    }

    public Set<Integer> getPickable(int row, int col) {
        return pickable[row][col];
    }

    public void setPickable(int row, int col, Set<Integer> values) {
        pickable[row][col] = values;
    }

}
