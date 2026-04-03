package sudokusolver.demo.Service;

import org.springframework.stereotype.Service;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Technique.SolvingTechnique;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SudokuService {
    private List<SolvingTechnique> techniques = List.of();

    public SudokuBoard solve (SudokuBoard board){
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

    public void initPickable(SudokuBoard board){
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                if(board.getCells(r, c) == 0) {
                    Set<Integer> all = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
                    board.setPickable(r, c, all);
                } else {
                    board.setPickable(r, c, new HashSet<>(Set.of(board.getCells(r, c))));
                    eliminate(board, r, c, board.getCells(r, c));
                }
            }
        }
    }

    public void eliminate(SudokuBoard board, int row, int col, int num){
        for (int c = 0; c < 9; c++){
            if (c != col) {
                board.getPickable(c, row).remove(num);
            }
        }

        for (int r = 0; r < 9; r++){
            if (r != row) {
                board.getPickable(r, col).remove(num);
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (startRow + r != row || startCol + c != col) {
                    board.getPickable(startRow + r, startCol + c).remove(num);
                }
            }
        }

    }
}
