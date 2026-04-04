package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

import java.util.ArrayList;
import java.util.List;

public class TechniqueUtils {

    public static List<int[]> findCellsWithPickable(SudokuBoard board, List<int[]> group, int num) {
        List<int[]> result = new ArrayList<>();
        for (int[] cell : group) {
            if (board.getPickable(cell[0], cell[1]).contains(num)) {
                result.add(cell);
            }
        }
        return result;
    }

    public static boolean isSameRow(List<int[]> cells) {
        int firstRow = cells.get(0)[0];
        for (int[] c : cells) {
            if (c[0] != firstRow) return false;
        }
        return true;
    }

    public static boolean isSameCol(List<int[]> cells) {
        int firstCol = cells.get(0)[1];
        for (int[] c : cells) {
            if (c[1] != firstCol) return false;
        }
        return true;
    }

    public static boolean isSameBox(List<int[]> cells) {
        int firstBoxRow = cells.get(0)[0] / 3;
        int firstBoxCol = cells.get(0)[1] / 3;
        for (int[] c : cells) {
            if (c[0] / 3 != firstBoxRow || c[1] / 3 != firstBoxCol){
                return false;
            }
        }
        return true;
    }
}