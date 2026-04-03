package sudokusolver.demo.Technique;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sudokusolver.demo.Model.SudokuBoard;

import java.util.List;

@Component
@Order(2)
public class HiddenSingle extends AbstractTechnique {

    @Override
    protected boolean checkGroup(SudokuBoard board, List<int[]> group) {
        boolean progress = false;
        for (int num = 1; num <= 9; num++) {
            List<int[]> cells = TechniqueUtils.findCellsWithPickable(board, group, num);
            if (cells.size() == 1) {
                int r = cells.get(0)[0], c = cells.get(0)[1];
                board.setCells(r, c, num);
                SudokuUtils.eliminate(board, r, c, num);
                progress = true;
            }
        }
        return progress;
    }
}
