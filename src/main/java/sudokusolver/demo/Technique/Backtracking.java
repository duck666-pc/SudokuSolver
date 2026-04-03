package sudokusolver.demo.Technique;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sudokusolver.demo.Model.SudokuBoard;

import java.util.HashSet;
import java.util.List;

@Component
@Order(999)
public class Backtracking extends AbstractTechnique {

    @Override
    public boolean apply(SudokuBoard board) {
        return backtrack(board);
    }

    @Override
    protected boolean checkGroup(SudokuBoard board, List<int[]> group) {
        return false;
    }

    private boolean backtrack(SudokuBoard board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCells(r, c) == 0) {
                    for (int num : new HashSet<>(board.getPickable(r, c))) {
                        board.setCells(r, c, num);
                        if (backtrack(board)) return true;
                        board.setCells(r, c, 0);
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
