package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

import java.util.List;
import java.util.stream.Collectors;

public class TechniqueUtils {

    public static List<int[]> findCellsWithCandidate(
            SudokuBoard board, List<int[]> group, int num) {
        return group.stream()
                .filter(cell -> board.getPickable(cell[0], cell[1]).contains(num))
                .collect(Collectors.toList());
    }

    public static List<int[]> findCellsWithPickable(
            SudokuBoard board, List<int[]> group, int num) {
        return findCellsWithCandidate(board, group, num);
    }

    public static boolean isSameRow(List<int[]> cells) {
        return cells.stream().allMatch(c -> c[0] == cells.get(0)[0]);
    }

    public static boolean isSameCol(List<int[]> cells) {
        return cells.stream().allMatch(c -> c[1] == cells.get(0)[1]);
    }

    public static boolean isSameBox(List<int[]> cells) {
        return cells.stream().allMatch(c ->
                c[0] / 3 == cells.get(0)[0] / 3 &&
                        c[1] / 3 == cells.get(0)[1] / 3);
    }
}