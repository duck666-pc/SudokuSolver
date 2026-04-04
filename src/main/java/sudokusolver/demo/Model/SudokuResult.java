package sudokusolver.demo.Model;

import java.util.List;

public class SudokuResult {
    private final boolean valid;
    private final String errorMessage;
    private final List<int[][]> solutions;

    public SudokuResult(String errorMessage) {
        this.valid = false;
        this.errorMessage = errorMessage;
        this.solutions = List.of();
    }

    public SudokuResult(List<int[][]> solutions) {
        this.valid = true;
        this.errorMessage = null;
        this.solutions = solutions;
    }

    public boolean isValid()            { return valid; }
    public String getErrorMessage()     { return errorMessage; }
    public List<int[][]> getSolutions() { return solutions; }
}
