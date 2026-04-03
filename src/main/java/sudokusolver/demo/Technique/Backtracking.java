package sudokusolver.demo.Technique;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sudokusolver.demo.Model.SudokuBoard;

import java.util.List;

@Component
@Order(999)
public class Backtracking extends AbstractTechnique {

    @Override
    public boolean apply(SudokuBoard board) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board.getCells(r, c) == 0)
                    return backtrack(board);
        return false;
    }

    @Override
    protected boolean checkGroup(SudokuBoard board, List<int[]> group) {
        return false;
    }

    private boolean backtrack(SudokuBoard board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCells(r, c) == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, r, c, num)) {
                            board.setCells(r, c, num);
                            if (backtrack(board)) return true;
                            board.setCells(r, c, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(SudokuBoard board, int row, int col, int num) {
        for (int c = 0; c < 9; c++)
            if (board.getCells(row, c) == num) return false;

        for (int r = 0; r < 9; r++)
            if (board.getCells(r, col) == num) return false;

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board.getCells(startRow + r, startCol + c) == num) return false;

        return true;
    }
}
