package sudokusolver.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Model.SudokuResult;
import sudokusolver.demo.Service.SudokuService;

import java.util.Map;

@Controller
public class SudokuController {

    @Autowired
    private SudokuService sudokuService;

    @GetMapping("/")
    public String index() {
        return "views";
    }

    @PostMapping("/")
    public String solve(@RequestParam Map<String, String> params, Model model) {
        SudokuBoard board = new SudokuBoard();
        int[][] originalBoard = new int[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String val = params.get("cell_" + r + "_" + c);
                int num = (val == null || val.isBlank()) ? 0 : Integer.parseInt(val.trim());
                board.setCells(r, c, num);
                originalBoard[r][c] = num;
            }
        }

        model.addAttribute("originalBoard", originalBoard);

        SudokuResult result = sudokuService.solve(board);

        if (!result.isValid()) {
            model.addAttribute("error", result.getErrorMessage());
            model.addAttribute("board", originalBoard);
            return "views";
        }

        var solutions = result.getSolutions();
        model.addAttribute("board", solutions.get(0));
        model.addAttribute("solutionCount", solutions.size());
        model.addAttribute("solutionsJson", sudokuService.toJson(solutions));
        return "views";
    }
}