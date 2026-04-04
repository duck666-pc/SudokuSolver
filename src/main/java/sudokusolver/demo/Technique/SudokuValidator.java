package sudokusolver.demo.Technique;

import sudokusolver.demo.Model.SudokuBoard;

public class SudokuValidator {

    public static String validate(SudokuBoard board) {

        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[10];
            for (int c = 0; c < 9; c++) {
                int v = board.getCells(r, c);
                if (v == 0) continue;
                if (seen[v]) return "Hàng " + (r + 1) + " có số " + v + " bị trùng!";
                seen[v] = true;
            }
        }

        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[10];
            for (int r = 0; r < 9; r++) {
                int v = board.getCells(r, c);
                if (v == 0) continue;
                if (seen[v]) return "Cột " + (c + 1) + " có số " + v + " bị trùng!";
                seen[v] = true;
            }
        }

        for (int bR = 0; bR < 3; bR++) {
            for (int bC = 0; bC < 3; bC++) {
                boolean[] seen = new boolean[10];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int v = board.getCells(bR * 3 + r, bC * 3 + c);
                        if (v == 0) continue;
                        if (seen[v]) return "Ô 3×3 (hàng " + (bR + 1) + ", cột " + (bC + 1) + ") có số " + v + " bị trùng!";
                        seen[v] = true;
                    }
                }
            }
        }
        return null;
    }
}