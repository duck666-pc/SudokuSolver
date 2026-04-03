package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

public interface SolvingTechnique {
    boolean apply(SudokuBoard model);
}
