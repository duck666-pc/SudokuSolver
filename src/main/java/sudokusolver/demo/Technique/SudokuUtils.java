package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

public class SudokuUtils {

    public static void eliminate(SudokuBoard board, int row, int col, int num) {
        for (int c = 0; c < 9; c++) {
            if (c != col && board.getPickable(row, c) != null) {
                board.getPickable(row, c).remove(num);
            }
        }

        for (int r = 0; r < 9; r++) {
            if (r != row && board.getPickable(r, col) != null) {
                board.getPickable(r, col).remove(num);
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (startRow + r != row || startCol + c != col) {
                    if (board.getPickable(startRow + r, startCol + c) != null){
                        board.getPickable(startRow + r, startCol + c).remove(num);
                    }
                }
            }
        }
    }
}