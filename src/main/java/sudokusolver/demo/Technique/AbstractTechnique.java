package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTechnique implements SolvingTechnique {

    @Override
    public boolean apply(SudokuBoard board) {
        boolean progress = false;
        for (List<int[]> group : getAllGroups(board)) {
            if (checkGroup(board, group)) progress = true;
        }
        return progress;
    }

    protected abstract boolean checkGroup(SudokuBoard board, List<int[]> group);

    protected List<List<int[]>> getAllGroups(SudokuBoard board) {
        List<List<int[]>> groups = new ArrayList<>();

        for (int r = 0; r < 9; r++) {
            List<int[]> group = new ArrayList<>();
            for (int c = 0; c < 9; c++)
                if (board.getCell(r, c) == 0)
                    group.add(new int[]{r, c});
            groups.add(group);
        }

        for (int c = 0; c < 9; c++) {
            List<int[]> group = new ArrayList<>();
            for (int r = 0; r < 9; r++)
                if (board.getCell(r, c) == 0)
                    group.add(new int[]{r, c});
            groups.add(group);
        }

        for (int boxR = 0; boxR < 3; boxR++) {
            for (int boxC = 0; boxC < 3; boxC++) {
                List<int[]> group = new ArrayList<>();
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int row = boxR * 3 + r;
                        int col = boxC * 3 + c;
                        if (board.getCell(row, col) == 0)
                            group.add(new int[]{row, col});
                    }
                }
                groups.add(group);
            }
        }

        return groups;
    }
}
