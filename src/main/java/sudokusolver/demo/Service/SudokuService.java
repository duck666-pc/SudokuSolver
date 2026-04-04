package sudokusolver.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Model.SudokuResult;
import sudokusolver.demo.Technique.SolvingTechnique;
import sudokusolver.demo.Technique.SudokuUtils;
import sudokusolver.demo.Technique.SudokuValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SudokuService {

    private static final int MAX_SOLUTIONS = 10;

    @Autowired
    private List<SolvingTechnique> techniques;

    public SudokuResult solve(SudokuBoard board) {
        String error = SudokuValidator.validate(board);
        if (error != null) return new SudokuResult(error);

        initPickable(board);

        boolean progress = true;
        while (progress) {
            progress = false;
            for (SolvingTechnique t : techniques) {
                if (t.apply(board)) { progress = true; break; }
            }
        }

        List<int[][]> solutions = new ArrayList<>();
        findSolutions(board, solutions);

        if (solutions.isEmpty()){
            return new SudokuResult("Đề bài không có nghiệm hợp lệ!");
        }
        return new SudokuResult(solutions);
    }

    private void findSolutions(SudokuBoard board, List<int[][]> solutions) {
        if (solutions.size() >= MAX_SOLUTIONS) return;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCells(r, c) == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, r, c, num)) {
                            board.setCells(r, c, num);
                            findSolutions(board, solutions);
                            board.setCells(r, c, 0);
                        }
                    }
                    return;
                }
            }
        }
        solutions.add(copyBoard(board));
    }

    private boolean isValid(SudokuBoard board, int row, int col, int num) {
        for (int c = 0; c < 9; c++){
            if (board.getCells(row, c) == num){
                return false;
            }
        }

        for (int r = 0; r < 9; r++){
            if (board.getCells(r, col) == num){
                return false;
            }
        }

        int sr = row - row % 3, sc = col - col % 3;
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (board.getCells(sr + r, sc + c) == num){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private int[][] copyBoard(SudokuBoard board) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                copy[r][c] = board.getCells(r, c);
            }
            return copy;
        }
        return copy;
    }

    public void initPickable(SudokuBoard board) {
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                board.setPickable(r, c, board.getCells(r, c) == 0
                        ? new HashSet<>(Set.of(1,2,3,4,5,6,7,8,9))
                        : new HashSet<>(Set.of(board.getCells(r, c))));
            }
        }

        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                if (board.getCells(r, c) != 0)
                    SudokuUtils.eliminate(board, r, c, board.getCells(r, c));
            }
        }
    }
}