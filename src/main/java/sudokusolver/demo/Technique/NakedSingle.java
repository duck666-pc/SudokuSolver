package sudokusolver.demo.Technique;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sudokusolver.demo.Model.SudokuBoard;

import java.util.List;

@Component
@Order(1)
public class NakedSingle extends AbstractTechnique {

    @Override
    protected boolean checkGroup(SudokuBoard board, List<int[]> group) {
        boolean progress = false;
        for (int[] cell : group) {
            int r = cell[0], c = cell[1];
            if (board.getPickable(r, c).size() == 1) {
                int num = board.getPickable(r, c).iterator().next();
                board.setCells(r, c, num);
                SudokuUtils.eliminate(board, r, c, num);
                progress = true;
            }
        }
        return progress;
    }
}
