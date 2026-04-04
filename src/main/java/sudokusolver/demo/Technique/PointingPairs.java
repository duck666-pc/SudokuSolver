package sudokusolver.demo.Technique;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sudokusolver.demo.Model.SudokuBoard;

import java.util.List;

@Component
@Order(3)
public class PointingPairs extends AbstractTechnique {

    @Override
    protected boolean checkGroup(SudokuBoard board, List<int[]> group) {
        boolean progress = false;
        if (group.isEmpty() || !TechniqueUtils.isSameBox(group)){
            return false;
        }

        for (int num = 1; num <= 9; num++) {
            List<int[]> cells = TechniqueUtils.findCellsWithPickable(board, group, num);
            if (cells.size() < 2){
                continue;
            }

            if (TechniqueUtils.isSameRow(cells)) {
                int row = cells.get(0)[0];
                for (int c = 0; c < 9; c++) {
                    if (isInGroup(cells, row, c)){
                        continue;
                    }
                    if (board.getCells(row, c) == 0){
                        if (board.getPickable(row, c).remove(num)){
                            progress = true;
                        }
                    }

                }
            }

            if (TechniqueUtils.isSameCol(cells)) {
                int col = cells.get(0)[1];
                for (int r = 0; r < 9; r++) {
                    if (isInGroup(cells, r, col)){
                        continue;
                    }
                    if (board.getCells(r, col) == 0){
                        if (board.getPickable(r, col).remove(num)){
                            progress = true;
                        }
                    }
                }
            }
        }
        return progress;
    }

    private boolean isInGroup(List<int[]> cells, int row, int col) {
        return cells.stream().anyMatch(c -> c[0] == row && c[1] == col);
    }
}
