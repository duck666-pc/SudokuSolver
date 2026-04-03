package sudokusolver.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Technique.SolvingTechnique;
import sudokusolver.demo.Technique.SudokuUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SudokuService {

    @Autowired
    private List<SolvingTechnique> techniques;

    public SudokuBoard solve(SudokuBoard board) {
        initPickable(board);

        boolean progress = true;
        while (progress) {
            progress = false;
            for (SolvingTechnique technique : techniques) {
                if (technique.apply(board)) {
                    progress = true;
                    break;
                }
            }
        }

        return board;
    }

    public void initPickable(SudokuBoard board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCells(r, c) == 0) {
                    board.setPickable(r, c, new HashSet<>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
                } else {
                    board.setPickable(r, c, new HashSet<>(Set.of(board.getCells(r, c))));
                }
            }
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCells(r, c) != 0) {
                    SudokuUtils.eliminate(board, r, c, board.getCells(r, c));
                }
            }
        }
    }
}